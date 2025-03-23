package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusCooldownArmorItem;
import net.minecraft.resources.ResourceLocation;

public class ExcelsiusCooldownArmorModel extends GeoModel<ExcelsiusCooldownArmorItem> {
    @Override
    public ResourceLocation getModelResource(ExcelsiusCooldownArmorItem excelsiusCooldownArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/excelsius_speed.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExcelsiusCooldownArmorItem excelsiusCooldownArmorItem) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/models/armor/excelsius_cooldown.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExcelsiusCooldownArmorItem excelsiusCooldownArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
