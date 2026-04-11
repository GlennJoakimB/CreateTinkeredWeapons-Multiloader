package com.ridopipolop.createtinkeredweapons.forge.content.weapons;

import java.util.function.Consumer;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItemRenderer;
import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateTinkeredWeapons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PropellerMaceItemForge extends PropellerMaceItem {

  public PropellerMaceItemForge(Properties props) {
    super(props);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    // TODO: Need to add custom renderer and model
    consumer.accept(SimpleCustomRenderer.create(this, new BroadGlaiveItemRenderer()));
  }
}
