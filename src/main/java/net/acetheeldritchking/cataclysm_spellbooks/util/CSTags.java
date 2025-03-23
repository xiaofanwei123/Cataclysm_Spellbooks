package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CSTags {
    public static final TagKey<Item> ABYSSAL_FOCUS = ItemTags.create(new ResourceLocation(Cataclysm_Spellbooks.MODID, "abyssal_focus"));

    public static final TagKey<Item> TECHNOMANCY_FOCUS = ItemTags.create(new ResourceLocation(Cataclysm_Spellbooks.MODID, "technomancy_focus"));
}
