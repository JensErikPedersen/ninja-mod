package dk.jens.ninjamod;

import java.util.Map;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

public class ModItems {
	// Ninja-rustning kan repareres med sort uld i en ambolt (se data/ninjamod/tags/item/)
	public static final TagKey<Item> NINJA_REPAIR_MATERIALS = TagKey.create(Registries.ITEM, id("ninja_repair_materials"));

	// Ninja-rustning: holdbarhed som ringbrynje, beskyttelse mellem ringbrynje og jern
	//                 ringbrynje  ninja  jern
	// hjelm           1           2      2
	// brystplade      5           5      6
	// bukser          4           5      5
	// støvler         1           1      2
	// i alt           11          13     15
	public static final ResourceKey<EquipmentAsset> NINJA_EQUIPMENT_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, id("ninja"));
	public static final int NINJA_ARMOR_DURABILITY = 15; // samme som ringbrynje
	public static final ArmorMaterial NINJA_ARMOR_MATERIAL = new ArmorMaterial(
			NINJA_ARMOR_DURABILITY,
			Map.of(
					ArmorType.HELMET, 2,
					ArmorType.CHESTPLATE, 5,
					ArmorType.LEGGINGS, 5,
					ArmorType.BOOTS, 1
			),
			12,                               // enchantability (som ringbrynje)
			SoundEvents.ARMOR_EQUIP_LEATHER,  // stille lyd, når man tager den på
			0.0f,                             // toughness
			0.0f,                             // modstand mod knockback
			NINJA_REPAIR_MATERIALS,
			NINJA_EQUIPMENT_ASSET             // peger på assets/ninjamod/equipment/ninja.json
	);

	public static final Item NINJA_HELMET = registerArmor("ninja_helmet", ArmorType.HELMET);
	public static final Item NINJA_CHESTPLATE = registerArmor("ninja_chestplate", ArmorType.CHESTPLATE);
	public static final Item NINJA_LEGGINGS = registerArmor("ninja_leggings", ArmorType.LEGGINGS);
	public static final Item NINJA_BOOTS = registerArmor("ninja_boots", ArmorType.BOOTS);

	// Springstav: højreklik for at springe. Kan ikke slå (se NinjaMod).
	public static final Item NINJA_STAFF = register("ninja_staff", JumpStaffItem::new,
			new Item.Properties().stacksTo(1));

	// Kastestjerner: flyver og skader som en pil fra en fuldt spændt bue. 7 sekunders cooldown.
	public static final Item SHURIKEN = register("shuriken", ShurikenItem::new,
			new Item.Properties().stacksTo(16));

	// Mini-katana: samme skade og rækkevidde som et jernsværd, men angrebshastighed 2 (sværd: 1,6).
	// Angrebshastighed = 4 + (-2.0) = 2.0.  Skade = 1 + 2 (jern) + 3 = 6, som jernsværdet.
	public static final Item MINI_KATANA = register("mini_katana", Item::new,
			new Item.Properties().sword(ToolMaterial.IRON, 3.0f, -2.0f));

	private static Identifier id(String name) {
		return Identifier.fromNamespaceAndPath(NinjaMod.MOD_ID, name);
	}

	private static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id(name));
		Item item = itemFactory.apply(properties.setId(itemKey));
		return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
	}

	private static Item registerArmor(String name, ArmorType type) {
		return register(name, Item::new, new Item.Properties()
				.humanoidArmor(NINJA_ARMOR_MATERIAL, type)
				.durability(type.getDurability(NINJA_ARMOR_DURABILITY)));
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(creativeTab -> {
			creativeTab.accept(MINI_KATANA);
			creativeTab.accept(SHURIKEN);
			creativeTab.accept(NINJA_STAFF);
			creativeTab.accept(NINJA_HELMET);
			creativeTab.accept(NINJA_CHESTPLATE);
			creativeTab.accept(NINJA_LEGGINGS);
			creativeTab.accept(NINJA_BOOTS);
		});
	}
}
