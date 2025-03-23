package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SummonedAbyssalGnawerModel extends GeoModel<SummonedAbyssalGnawer> {
    @Override
    public ResourceLocation getModelResource(SummonedAbyssalGnawer object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/abyssal_gnawers.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummonedAbyssalGnawer object) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/entity/abyssal_gnawers/abyssal_gnawers.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummonedAbyssalGnawer animatable) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/entity/abyssal_gnawers.animation.json");
    }
}
