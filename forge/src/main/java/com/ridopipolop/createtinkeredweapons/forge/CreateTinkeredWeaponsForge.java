package com.ridopipolop.createtinkeredweapons.forge;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import com.ridopipolop.createtinkeredweapons.forge.registry.ModEntityTypesForge;
import com.ridopipolop.createtinkeredweapons.forge.registry.ModItemsForge;
import com.ridopipolop.createtinkeredweapons.forge.registry.ModPackets;
import com.ridopipolop.createtinkeredweapons.registry.ModEntityTypes;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateTinkeredWeapons.MOD_ID)
public class CreateTinkeredWeaponsForge {
  public CreateTinkeredWeaponsForge(FMLJavaModLoadingContext context) {
    // registrate must be given the mod event bus on forge before registration
    IEventBus eventBus = context.getModEventBus();
    CreateTinkeredWeapons.REGISTRATE.registerEventListeners(eventBus);
    ModEntityTypesForge.ENTITY_TYPES.register(eventBus);

    // Done to fix throwing the Broad Glaive
    eventBus.addListener((FMLCommonSetupEvent e) -> {
      ModEntityTypes.THROWN_BROAD_GLAIVE = ModEntityTypesForge.THROWN_BROAD_GLAIVE.get();
    });

    CreateTinkeredWeapons.init();
    ModPackets.registerPackets();
    ModItemsForge.register();
  }
}
