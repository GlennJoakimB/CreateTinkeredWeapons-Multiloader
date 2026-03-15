package com.ridopipolop.createtinkeredweapons.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import com.ridopipolop.createtinkeredweapons.CreateTinkeredWeapons;
import net.fabricmc.api.ModInitializer;

public class CreateTinkeredWeaponsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CreateTinkeredWeapons.init();
        CreateTinkeredWeapons.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), CreateTinkeredWeapons.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        CreateTinkeredWeapons.REGISTRATE.register();
    }
}
