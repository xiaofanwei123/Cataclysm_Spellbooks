package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe.BlazingAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.animatable.GeoEntity;
//import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
//import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class HellishBladeProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache;

    public HellishBladeProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.setNoGravity(true);
    }

    public HellishBladeProjectile(Level level, LivingEntity shooter) {
        this((EntityType)CSEntityRegistry.HELLISH_BLADE_PROJECTILE.get(), level);
        this.setOwner(shooter);
    }

    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(this.getDeltaMovement());
        this.level().addParticle((ParticleOptions)ModParticle.TRAP_FLAME.get(), vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(this.level(), (ParticleOptions)ModParticle.TRAP_FLAME.get(), x, y, z, 5, 0.0, 0.0, 0.0, 1.0, true);
    }

    public float getSpeed() {
        return 0.8F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(ModSounds.IGNIS_IMPACT.get());
    }

    @Override
    protected void doImpactSound(SoundEvent sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 1.5f, 1.0f);
    }


    protected void onHitEntity(EntityHitResult pResult) {
        Entity target = pResult.getEntity();
        //TODO:检查
        DamageSources.applyDamage(target, this.damage, ((AbstractSpell)SpellRegistries.HELLISH_BLADE.get()).getDamageSource(this, this.getOwner()));
        if (target instanceof LivingEntity livingTarget) {
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0));
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 60, 0));
            if (livingTarget instanceof Player playerTarget) {
                playerTarget.disableShield(true);
            }

            ScreenShake_Entity.ScreenShake(this.level(), livingTarget.position(), 20.0F, 0.1F, 20, 40);
        }

        this.discard();
    }

    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);
        this.createAoEField(hitresult.getLocation());
        EarthquakeAoe aoe = new EarthquakeAoe(this.level());
        aoe.moveTo(this.position());
        aoe.setOwner(this);
        aoe.setCircular();
        aoe.setRadius(10.0F);
        aoe.setDuration(20);
        aoe.setDamage(0.0F);
        aoe.setSlownessAmplifier(0);
        this.level().addFreshEntity(aoe);
        this.discard();
    }

    public void createAoEField(Vec3 location) {
        if (!this.level().isClientSide) {
            BlazingAoE aoE = new BlazingAoE(this.level());
            aoE.setOwner(this.getOwner());
            aoE.setDuration(100);
            aoE.setDamage(0.5F);
            aoE.setRadius(3.0F);
            aoE.setCircular();
            aoE.moveTo(location);
            this.level().addFreshEntity(aoE);
        }

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
