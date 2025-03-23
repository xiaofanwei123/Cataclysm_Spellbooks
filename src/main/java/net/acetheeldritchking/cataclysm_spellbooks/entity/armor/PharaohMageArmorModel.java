package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.PharaohMageArmorItem;
import net.minecraft.resources.ResourceLocation;

public class PharaohMageArmorModel extends GeoModel<PharaohMageArmorItem> {
    @Override
    public ResourceLocation getModelResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/pharaoh_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/pharaoh_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
