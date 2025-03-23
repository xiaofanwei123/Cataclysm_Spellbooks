package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeModel;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HellishBladeRenderer extends GeoEntityRenderer<HellishBladeProjectile> {
    public HellishBladeRenderer(EntityRendererProvider.Context context) {
        super(context, new HellishBladeModel());
        this.shadowRadius = 0.5F;
    }

    public ResourceLocation getTextureLocation(HellishBladeProjectile animatable) {
        return new ResourceLocation("cataclysm_spellbooks", "textures/entity/hellish_blade/hellish_blade.png");
    }

    public boolean shouldRender(HellishBladeProjectile livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return super.shouldRender(livingEntity, camera, camX, camY, camZ);
    }
}
