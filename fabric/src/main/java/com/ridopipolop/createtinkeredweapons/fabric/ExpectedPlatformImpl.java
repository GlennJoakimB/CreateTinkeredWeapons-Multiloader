package com.ridopipolop.createtinkeredweapons.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class ExpectedPlatformImpl {
	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}
}
