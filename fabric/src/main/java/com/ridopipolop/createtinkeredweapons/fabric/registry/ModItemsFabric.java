package com.ridopipolop.createtinkeredweapons.fabric.registry;

import static com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons.REGISTRATE;

import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.impact_axe.ImpactAxeItemRenderer;
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
  }
}
