package com.ridopipolop.createtinkeredweapons.forge.client;

import com.ridopipolop.createtinkeredweapons.content.weapons.propeller_mace.PropellerMaceArmPoseHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PropellerMaceArmPoseHandler.applyArmPose(
            event.getEntity(),
            event.getRenderer().getModel()
        );
    }
}

