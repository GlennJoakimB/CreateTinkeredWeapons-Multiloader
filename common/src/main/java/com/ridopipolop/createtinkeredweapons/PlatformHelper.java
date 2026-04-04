package com.ridopipolop.createtinkeredweapons;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import com.google.common.collect.Multimap;

public class PlatformHelper {
  @ExpectPlatform
  public static Multimap<Attribute, AttributeModifier> getRangeModifier(AttributeModifier modifier) {
    throw new AssertionError();
  }
}