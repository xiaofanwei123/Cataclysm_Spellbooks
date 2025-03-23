package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.CursiumMageElytraArmorItem;
import net.minecraft.resources.ResourceLocation;

public class CursiumMageElytraArmorModel extends GeoModel<CursiumMageElytraArmorItem> {
    @Override
    public ResourceLocation getModelResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/cursium_mage_elytra.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/cursium_mage_armor_elytra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/entity/cursium_mage.animation.json");
    }
}
