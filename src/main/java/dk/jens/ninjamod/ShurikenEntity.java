package dk.jens.ninjamod;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Den flyvende kastestjerne. Den bygger på Minecrafts pil, så den flyver i samme bue,
 * lige så hurtigt og langt, og gør samme skade som en pil. Den sætter sig fast, hvor den rammer,
 * og kan samles op igen.
 */
public class ShurikenEntity extends AbstractArrow implements ItemSupplier {
	public ShurikenEntity(EntityType<? extends ShurikenEntity> type, Level level) {
		super(type, level);
	}

	public ShurikenEntity(Level level, LivingEntity owner, ItemStack pickupItem) {
		super(ModEntities.SHURIKEN, owner, level, pickupItem, null);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(ModItems.SHURIKEN);
	}

	// Bruges af tegneren til at vise kastestjernens billede
	@Override
	public ItemStack getItem() {
		return getPickupItemStackOrigin();
	}
}
