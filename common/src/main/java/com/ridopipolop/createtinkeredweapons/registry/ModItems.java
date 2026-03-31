package com.ridopipolop.createtinkeredweapons.registry;

import static com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons.REGISTRATE;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItems {
  public static final ItemEntry<ImpactAxeItem> IMPACT_AXE = REGISTRATE
    .item("impact_axe", ImpactAxeItem:: new)
    .properties(p -> p.stacksTo(1).durability(350))
    .model(AssetLookup.itemModelWithPartials())
    .register();


  public static void register() {
    CreateTinkeredWeapons.LOGGER.info("Registiring items for {}.", CreateTinkeredWeapons.NAME);
  }
}