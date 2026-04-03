package com.ridopipolop.createtinkeredweapons.registry;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItem;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItems {
  // Different implementation between forge and fabric for custom item-renderer
  public static ItemEntry<ImpactAxeItem> IMPACT_AXE;

  public static void register() {
    CreateTinkeredWeapons.LOGGER.info("Registiring items for {}.", CreateTinkeredWeapons.NAME);
  }
}