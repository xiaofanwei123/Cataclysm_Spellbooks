package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;

public class AbyssalPredatorPotionEffect extends MagicMobEffect {
    public AbyssalPredatorPotionEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory , color);//5984177
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "f5f22724-fb4a-49f9-b303-cdf84357c50b", AbyssalPredatorPotionEffect.ATTACK_SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "3e8ee83b-e6f3-4c70-a39c-de09c8e66858", AbyssalPredatorPotionEffect.ATTACK_DAMAGE_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "e31ad3ab-4985-4a00-9656-bc42bd52e494", AbyssalPredatorPotionEffect.SWIM_SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float SWIM_SPEED_PER_LEVEL = 0.40f;
    // Base buffs, deal more damage when in water
    public static final float ATTACK_SPEED_PER_LEVEL = 0.20f;
    public static final float ATTACK_DAMAGE_PER_LEVEL = 0.20f;
    // Bonus Effects while in water
    public static final float MOVEMENT_SPEED_BONUS_PER_LEVEL = 0.20f;
    public static final float ATTACK_DAMAGE_BONUS_PER_LEVEL = 0.05f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @OnlyIn(Dist.CLIENT)
    public static class EcholocationBlindnessFogFunction implements FogRenderer.MobEffectFogFunction {
        public EcholocationBlindnessFogFunction() {
        }

        @Override
        public MobEffect getMobEffect() {
            return (MobEffect) CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get();
        }

        @Override
        public void setupFog(FogRenderer.FogData fogData, LivingEntity entity, MobEffectInstance mobEffectInstance, float p_234184_, float p_234185_) {
            float f = 160.0F;
            if (fogData.mode == FogRenderer.FogMode.FOG_SKY) {
                fogData.start = 0.0F;
                fogData.end = f * 0.25F;
            } else {
                fogData.start = -f * 0.5F;
                fogData.end = f;
            }

        }
    }
}
