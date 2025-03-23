package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.world.entity.Entity;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;


import java.util.Optional;


public class InfernalBladeProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache;
    private final AnimationController<InfernalBladeProjectile> animationController;

    public InfernalBladeProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.animationController = new AnimationController(this, "controller", 0, this::predicate);
        this.setNoGravity(true);
    }

    public InfernalBladeProjectile(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.INFERNAL_BLADE_PROJECTILE.get(), level);
        this.setOwner(shooter);
    }

    @Override
    public void travel() {
        this.setPos(this.position().add(this.getDeltaMovement()));
        if (!this.isNoGravity()) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, vec3.y - 0.05000000074505806, vec3.z);
        }

    }

    public void setRotation(float x, float y) {
        this.setXRot(x);
        this.xRotO = x;
        this.setYRot(y);
        this.yRotO = y;
    }

    @Override
    public void tick() {
        Vec3 deltaMovement = this.getDeltaMovement();
        double distance = deltaMovement.horizontalDistance();
        double x = deltaMovement.x;
        double y = deltaMovement.y;
        double z = deltaMovement.z;
        this.setYRot((float)(Mth.atan2(x, z) * 57.29577951308232));
        this.setXRot((float)(Mth.atan2(y, distance) * 57.29577951308232));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
        super.tick();
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(this.getDeltaMovement());
        this.level().addParticle(ParticleHelper.EMBERS, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(this.level(), ModParticle.TRAP_FLAME.get(), x, y, z, 5, 0.0, 0.0, 0.0, 1.0, true);
    }

    public float getSpeed() {
        return 0.7F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(ModSounds.IGNIS_POKE.get());
    }

    @Override
    protected void doImpactSound(SoundEvent sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 1.5f, 0.5f);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity target = pResult.getEntity();
        //TODO:INFERNAL_STRIKE逻辑
        DamageSources.applyDamage(target, this.damage, SpellRegistries.INFERNAL_STRIKE.get().getDamageSource(this, this.getOwner()));
        if (target instanceof LivingEntity livingTarget) {
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0));
        }

        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.discard();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(this.animationController);
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private PlayState predicate(AnimationState<InfernalBladeProjectile> event) {
        event.getController().setAnimation(RawAnimation.begin().then("animation.infernal_blade_small.idle", LoopType.LOOP));
        return PlayState.CONTINUE;
    }
}
