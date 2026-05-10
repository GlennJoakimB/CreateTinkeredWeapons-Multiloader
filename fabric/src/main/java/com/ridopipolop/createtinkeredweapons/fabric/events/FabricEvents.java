package com.ridopipolop.createtinkeredweapons.fabric.events;

import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceEventHandler;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityDataEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents.LivingTickEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FabricEvents {
  public static void register() {

    LivingTickEvent.TICK.register(event -> {
      LivingEntity entity = event.getEntity();
      if (!(entity instanceof Player player))
        return;

      BroadGlaiveItem.holdingGlaiveIncreasesRange(player, player.getCustomData());
      PropellerMaceEventHandler.onPlayerTick(player);
    });

    EntityDataEvents.LOAD.register((entity, persistentData) -> {
      if (!(entity instanceof Player player))
        return;
      BroadGlaiveItem.addRangeToJoiningPlayersHoldingGlaive(player, persistentData);
    });
  }
}