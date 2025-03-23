package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HellishBladeModel extends GeoModel<HellishBladeProjectile> {
    public HellishBladeModel() {
    }

    @Override
    public ResourceLocation getModelResource(HellishBladeProjectile object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/hellish_blade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HellishBladeProjectile object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/entity/hellish_blade/hellish_blade.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HellishBladeProjectile animatable) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/entity/hellish_blade.animation.json");
    }
}
