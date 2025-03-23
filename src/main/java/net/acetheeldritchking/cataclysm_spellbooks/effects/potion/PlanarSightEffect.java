package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;


//渲染颜色
public class PlanarSightEffect extends MagicMobEffect {
    public PlanarSightEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @OnlyIn(Dist.CLIENT)
    public static class EcholocationBlindnessFogFunction implements FogRenderer.MobEffectFogFunction {
        public EcholocationBlindnessFogFunction() {
        }

        @Override
        public MobEffect getMobEffect() {
            return (MobEffect) CSPotionEffectRegistry.PLANAR_SIGHT.get();
        }

        @Override
        public void setupFog(FogRenderer.FogData fogData, LivingEntity entity, MobEffectInstance mobEffectInstance, float p_234184_, float p_234185_) {
            float f = 160.0F;
            if (fogData.mode == FogMode.FOG_SKY) {
                fogData.start = 0.0F;
                fogData.end = f * 0.25F;
            } else {
                fogData.start = -f * 0.5F;
                fogData.end = f;
            }

        }
    }
}

