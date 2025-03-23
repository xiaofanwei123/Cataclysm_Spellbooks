package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusLegArmorItem;
import net.minecraft.resources.ResourceLocation;

public class ExcelsiusLegArmorModel extends GeoModel<ExcelsiusLegArmorItem> {
    @Override
    public ResourceLocation getModelResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/excelsius_legs.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/excelsius_legs.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
