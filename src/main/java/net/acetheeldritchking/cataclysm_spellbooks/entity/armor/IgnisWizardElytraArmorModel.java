package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardElytraArmorItem;
import net.minecraft.resources.ResourceLocation;

public class IgnisWizardElytraArmorModel extends GeoModel<IgnisWizardElytraArmorItem> {
    @Override
    public ResourceLocation getModelResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/ignis_armor_winged.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/ignis_armor_winged.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/entity/ignis_armor_winged.animation.json");
    }
}
