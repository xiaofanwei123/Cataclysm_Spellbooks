package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InfernalBladeModel extends GeoModel<InfernalBladeProjectile> {
    public InfernalBladeModel() {
    }

    @Override
    public ResourceLocation getModelResource(InfernalBladeProjectile object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/infernal_blade_small.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfernalBladeProjectile object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/entity/infernal_blade_small/infernal_blade_small.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InfernalBladeProjectile animatable) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/entity/infernal_blade_small.animation.json");
    }
}
