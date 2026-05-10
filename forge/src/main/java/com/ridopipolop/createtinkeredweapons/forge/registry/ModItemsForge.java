package com.ridopipolop.createtinkeredweapons.forge.registry;

import static com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons.REGISTRATE;

import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceItem;
import com.ridopipolop.createtinkeredweapons.forge.content.weapons.BroadGlaiveItemForge;
import com.ridopipolop.createtinkeredweapons.forge.content.weapons.ImpactAxeItemForge;
import com.ridopipolop.createtinkeredweapons.forge.content.weapons.PropellerMaceItemForge;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.ItemEntry;

public class ModItemsForge {

  @SuppressWarnings("unchecked")
  public static void register() {
    ModItems.IMPACT_AXE = (ItemEntry<ImpactAxeItem>) (ItemEntry<?>) REGISTRATE
        .item("impact_axe", ImpactAxeItemForge::new)
        .properties(p -> p.stacksTo(1).durability(350))
        .model(AssetLookup.itemModelWithPartials())
        .register();

    ModItems.BROAD_GLAIVE = (ItemEntry<BroadGlaiveItem>) (ItemEntry<?>) REGISTRATE
        .item("broad_glaive", BroadGlaiveItemForge::new)
        .properties(p -> p.stacksTo(1).durability(500))
        .model(AssetLookup.itemModelWithPartials())
        .register();

    ModItems.PROPELLER_MACE = (ItemEntry<PropellerMaceItem>) (ItemEntry<?>) REGISTRATE
        .item("propeller_mace", PropellerMaceItemForge::new)
        .properties(p -> p.stacksTo(1).durability(500))
        .model(AssetLookup.itemModelWithPartials())
        .register();
  }

}
