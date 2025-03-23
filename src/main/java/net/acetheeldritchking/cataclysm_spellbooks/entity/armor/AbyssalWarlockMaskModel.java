package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockMaskItem;
import net.minecraft.resources.ResourceLocation;

public class AbyssalWarlockMaskModel extends GeoModel<AbyssalWarlockMaskItem> {
    @Override
    public ResourceLocation getModelResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/abyssal_warlock_mask.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/abyssal_warlock_mask.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}

