package com.ridopipolop.createtinkeredweapons.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

public class PlatformHelperImpl {
  public static Multimap<Attribute, AttributeModifier> getRangeModifier(AttributeModifier modifier) {
    return ImmutableMultimap.of(ReachEntityAttributes.ATTACK_RANGE, modifier);
  }
}