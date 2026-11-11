package dk.jens.ninjamod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
	private static final ResourceKey<EntityType<?>> SHURIKEN_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
			Identifier.fromNamespaceAndPath(NinjaMod.MOD_ID, "shuriken"));

	// Samme størrelse og opdateringsrate som en pil
	public static final EntityType<ShurikenEntity> SHURIKEN = Registry.register(BuiltInRegistries.ENTITY_TYPE, SHURIKEN_KEY,
			EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC)
					.sized(0.5f, 0.5f)
					.clientTrackingRange(4)
					.updateInterval(20)
					.build(SHURIKEN_KEY));

	public static void initialize() {
		// Kaldes bare for at sikre, at klassen bliver indlæst
	}
}
