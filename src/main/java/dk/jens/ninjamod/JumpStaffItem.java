package dk.jens.ninjamod;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/**
 * Springstaven. Højreklik for at springe ca. 7 blokke op og 14 blokke frem i den retning, man kigger.
 *
 * Tallene er regnet ud fra Minecrafts fysik: hvert tick (1/20 sekund) trækker tyngdekraften 0,08
 * fra farten opad og ganger den med 0,98, og farten fremad bliver ganget med 0,91 (luftmodstand).
 *   - 1,10 opad giver toppunkt ca. 7 blokke over jorden og ca. 27 ticks i luften
 *   - 1,37 fremad giver ca. 14 blokke på de 27 ticks
 */
public class JumpStaffItem extends Item {
	public static final double JUMP_UP = 1.10;      // fart opad (blokke pr. tick)
	public static final double JUMP_FORWARD = 1.50; // fart fremad (blokke pr. tick)
	private static final int COOLDOWN_TICKS = 10;   // et halvt sekund mellem spring

	public JumpStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		// Man skal stå på noget (eller svømme) for at kunne sætte af, ellers kunne man flyve
		if (!player.onGround() && !player.isInWater()) {
			return InteractionResult.FAIL;
		}

		// Retningen man kigger, men kun vandret
		float yaw = player.getYRot() * ((float) Math.PI / 180f);
		double dx = -Math.sin(yaw);
		double dz = Math.cos(yaw);
		player.setDeltaMovement(dx * JUMP_FORWARD, JUMP_UP, dz * JUMP_FORWARD);

		player.getCooldowns().addCooldown(player.getItemInHand(hand), COOLDOWN_TICKS);

		if (player instanceof ServerPlayer serverPlayer) {
			// Ingen faldskade når man lander efter springet
			NinjaMod.startNinjaJump(serverPlayer);
			level.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.8f, 1.4f);
		}
		return InteractionResult.SUCCESS;
	}
}
