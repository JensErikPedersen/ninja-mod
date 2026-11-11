package dk.jens.ninjamod;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShurikenItem extends Item {
	public static final int COOLDOWN_TICKS = 7 * 20; // 7 sekunder
	// Samme som en fuldt spændt bue: fart 3,0 og spredning 1,0
	private static final float SPEED = 3.0f;
	private static final float INACCURACY = 1.0f;

	public ShurikenItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (level instanceof ServerLevel) {
			ShurikenEntity shuriken = new ShurikenEntity(level, player, stack.copyWithCount(1));
			shuriken.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, SPEED, INACCURACY);
			// En fuldt spændt bue giver altid et "kritisk" skud (lidt ekstra tilfældig skade)
			shuriken.setCritArrow(true);
			if (player.hasInfiniteMaterials()) {
				shuriken.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
			level.addFreshEntity(shuriken);
			level.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.6f);
		}

		player.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);
		stack.consume(1, player);
		return InteractionResult.SUCCESS;
	}
}
