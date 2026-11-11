package dk.jens.ninjamod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class NinjaMod implements ModInitializer {
	public static final String MOD_ID = "ninjamod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Spillere, der er midt i et spring med springstaven (de får ingen faldskade), og hvornår de sprang
	private static final Map<UUID, Integer> JUMPING = new HashMap<>();

	@Override
	public void onInitialize() {
		ModEntities.initialize();
		ModItems.initialize();

		// Springstaven kan ikke slå: angrebet bliver stoppet, så det giver ingen skade og intet skub
		AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) ->
				player.getMainHandItem().is(ModItems.NINJA_STAFF) ? InteractionResult.FAIL : InteractionResult.PASS);

		ServerTickEvents.END_SERVER_TICK.register(NinjaMod::tick);
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> JUMPING.clear());

		LOGGER.info("Ninja Mod er indlæst!");
	}

	public static void startNinjaJump(ServerPlayer player) {
		JUMPING.put(player.getUUID(), player.level().getServer().getTickCount());
	}

	// Kaldes 20 gange i sekundet
	private static void tick(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			updateNinjaSpeed(player);

			// Under et spring nulstilles faldhøjden hele tiden, så man lander uden skade
			Integer jumpedAt = JUMPING.get(player.getUUID());
			if (jumpedAt != null) {
				player.resetFallDistance();
				// De første ticks står man stadig på jorden, fordi man lige er sat af
				boolean airborneLongEnough = server.getTickCount() - jumpedAt > 5;
				if (!player.isAlive() || (airborneLongEnough && (player.onGround() || player.isInWater()))) {
					JUMPING.remove(player.getUUID());
				}
			}
		}
	}

	/**
	 * Ninja-rustningen giver fart efter, hvor mange dele man har på:
	 *   0-1 dele: ingen fart
	 *   2-3 dele: Speed I
	 *   4 dele:   Speed II
	 * Effekten varer 2 sekunder og bliver fornyet hele tiden, så den forsvinder kort efter, man tager dele af.
	 */
	private static void updateNinjaSpeed(Player player) {
		int pieces = countNinjaArmor(player);
		int wanted = pieces >= 4 ? 1 : pieces >= 2 ? 0 : -1; // 0 = Speed I, 1 = Speed II, -1 = ingen

		MobEffectInstance current = player.getEffect(MobEffects.SPEED);
		// Har man taget en del af, skal vores stærkere fart fjernes med det samme.
		// Fart fra fx en drik eller en beacon lader vi være.
		if (current != null && isNinjaSpeed(current) && current.getAmplifier() > wanted) {
			player.removeEffect(MobEffects.SPEED);
			current = null;
		}

		if (wanted < 0) {
			return;
		}
		boolean needsRefresh = current == null
				|| current.getAmplifier() < wanted
				|| (current.getAmplifier() == wanted && isNinjaSpeed(current) && current.getDuration() < 30);
		if (needsRefresh) {
			// ambient = true og ingen partikler: sådan kan vi kende vores egen fart-effekt
			player.addEffect(new MobEffectInstance(MobEffects.SPEED, NINJA_SPEED_TICKS, wanted, true, false, true));
		}
	}

	private static final int NINJA_SPEED_TICKS = 40;

	private static boolean isNinjaSpeed(MobEffectInstance effect) {
		return effect.isAmbient() && !effect.isVisible() && effect.getDuration() <= NINJA_SPEED_TICKS;
	}

	private static int countNinjaArmor(Player player) {
		int count = 0;
		if (wears(player, EquipmentSlot.HEAD, ModItems.NINJA_HELMET)) count++;
		if (wears(player, EquipmentSlot.CHEST, ModItems.NINJA_CHESTPLATE)) count++;
		if (wears(player, EquipmentSlot.LEGS, ModItems.NINJA_LEGGINGS)) count++;
		if (wears(player, EquipmentSlot.FEET, ModItems.NINJA_BOOTS)) count++;
		return count;
	}

	private static boolean wears(Player player, EquipmentSlot slot, Item item) {
		return player.getItemBySlot(slot).is(item);
	}
}
