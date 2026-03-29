package com.ridopipolop.createtinkeredweapons.forge;

import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateTinkeredWeapons.MOD_ID)
public class CreateTinkeredWeaponsForge {
    public CreateTinkeredWeaponsForge(FMLJavaModLoadingContext context) {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = context.getModEventBus();
        CreateTinkeredWeapons.REGISTRATE.registerEventListeners(eventBus);
        CreateTinkeredWeapons.init();
    }
}
