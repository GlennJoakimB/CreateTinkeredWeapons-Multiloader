package com.ridopipolop.createtinkeredweapons.fabric.registry;

import static com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons.REGISTRATE;

import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItemRenderer;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItemRenderer;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceItemRenderer;
import com.ridopipolop.createtinkeredweapons.registry.ModItems;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;

public class ModItemsFabric {
  public static void register() {
    ModItems.IMPACT_AXE = REGISTRATE
        .item("impact_axe", ImpactAxeItem::new)
        .properties(p -> p.stacksTo(1).durability(350))
        .transform(CreateRegistrate.customRenderedItem(() -> ImpactAxeItemRenderer::new))
        .model(AssetLookup.itemModelWithPartials())
        .register();

    ModItems.BROAD_GLAIVE = REGISTRATE
        .item("broad_glaive", BroadGlaiveItem::new)
        .properties(p -> p.stacksTo(1).durability(500))
        .transform(CreateRegistrate.customRenderedItem(() -> BroadGlaiveItemRenderer::new))
        .model(AssetLookup.itemModelWithPartials())
        .register();

    ModItems.PROPELLER_MACE = REGISTRATE
        .item("propeller_mace", PropellerMaceItem::new)
        .properties(p -> p.stacksTo(1).durability(500))
        .transform(CreateRegistrate.customRenderedItem(() -> PropellerMaceItemRenderer::new))
        .model(AssetLookup.itemModelWithPartials())
        .register();
  }
}
