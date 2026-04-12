package com.ridopipolop.createtinkeredweapons.forge.events;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceEventHandler;
import com.ridopipolop.createtinkeredweapons.forge.registry.ModPackets;
import com.ridopipolop.createtinkeredweapons.forge.packets.BroadGlaiveInteractionPacket;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateTinkeredWeapons.MOD_ID)
public class ForgeEvents {
  @SubscribeEvent
  public static void onLivingTick(LivingTickEvent event) {
    LivingEntity entity = event.getEntity();
    if (!(entity instanceof Player player))
      return;
    BroadGlaiveItem.holdingGlaiveIncreasesRange(player, player.getPersistentData());
  }

  @SubscribeEvent
  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    Player player = event.getEntity();
    BroadGlaiveItem.addRangeToJoiningPlayersHoldingGlaive(player, player.getPersistentData());
  }

  @SubscribeEvent
  public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      PropellerMaceEventHandler.onPlayerTick(event.player);
    }
  }

  @SubscribeEvent
  @OnlyIn(Dist.CLIENT)
  public static void notifyServerOfLongRangeAttacks(AttackEntityEvent event) {
    Entity entity = event.getEntity();
    Entity target = event.getTarget();
    if (!isUncaughtClientInteraction(entity, target))
      return;
    Player player = (Player) entity;
    if (ModItems.BROAD_GLAIVE.isIn(player.getMainHandItem()))
      ModPackets.getChannel().sendToServer(new BroadGlaiveInteractionPacket(target));
  }

  private static boolean isUncaughtClientInteraction(Entity entity, Entity target) {
    if (entity.distanceToSqr(target) < 36)
      return false;
    if (!entity.level().isClientSide)
      return false;
    if (!(entity instanceof Player))
      return false;
    return true;
  }
}