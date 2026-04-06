package com.ridopipolop.createtinkeredweapons.forge;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.ForgeMod;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class PlatformHelperImpl {
  public static Multimap<Attribute, AttributeModifier> getRangeModifier(AttributeModifier modifier) {
    return ImmutableMultimap.of(ForgeMod.ENTITY_REACH.get(), modifier);
  }
}