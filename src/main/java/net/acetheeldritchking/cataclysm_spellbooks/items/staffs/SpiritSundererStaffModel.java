package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.resources.ResourceLocation;

public class SpiritSundererStaffModel extends GeoModel<SpiritSundererStaff> {
    @Override
    public ResourceLocation getModelResource(SpiritSundererStaff spiritSundererStaff) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/spirit_sunderer.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpiritSundererStaff spiritSundererStaff) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/item/spirit_sunderer.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpiritSundererStaff spiritSundererStaff) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
