package com.ridopipolop.createtinkeredweapons.forge.content.weapons;

import java.util.function.Consumer;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItem;
import com.ridopipolop.createtinkeredweapons.content.weapons.broad_glaive.BroadGlaiveItemRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateTinkeredWeapons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BroadGlaiveItemForge extends BroadGlaiveItem {

  public BroadGlaiveItemForge(Properties props) {
    super(props);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    consumer.accept(SimpleCustomRenderer.create(this, new BroadGlaiveItemRenderer()));
  }
}