package net.acetheeldritchking.cataclysm_spellbooks.mixins;

import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ignis_Fireball_Entity.class)
public abstract class Ignis_Fireball_Mixin_Entity extends AbstractHurtingProjectile {

    protected Ignis_Fireball_Mixin_Entity(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    public Ignis_Fireball_Mixin_Entity(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(p_36817_, p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public Ignis_Fireball_Mixin_Entity(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }

    @Inject(method ="onHitEntity",at=@At("HEAD"), cancellable = true)
    public void onHitEntity(EntityHitResult result, CallbackInfo ci) {
        super.onHitEntity(result);
        Entity shooter = this.getOwner();
        if (!this.level().isClientSide && !(result.getEntity() instanceof Ignis_Fireball_Entity) && !(result.getEntity() instanceof Ignis_Abyss_Fireball_Entity) && !(result.getEntity() instanceof Cm_Falling_Block_Entity) && (!(result.getEntity() instanceof Player) || !(shooter instanceof Player))) {
            if (shooter instanceof Player) {
                Entity entity = result.getEntity();
                LivingEntity owner = (LivingEntity)shooter;
                if (entity instanceof LivingEntity) {
                    float damage = (this.isSoul() ? 8F : 6F)*(float)owner.getAttributeValue(AttributeRegistry.SPELL_POWER.get())*(float)owner.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER.get());
                    entity.hurt(shooter.damageSources().mobProjectile(this, owner), damage);
                    MobEffectInstance effectinstance = new MobEffectInstance((MobEffect) ModEffect.EFFECTBLAZING_BRAND.get(), 200, 1, false, false, true);
                    ((LivingEntity)entity).addEffect(effectinstance);
                    entity.setSecondsOnFire((this.isSoul() ? 5 : 2));
                    owner.heal(0.1F * damage);
                }
                this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.75F);
                this.discard();
                ci.cancel();
            }
        }
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"), cancellable = true)
    protected void onHitBlock(BlockHitResult result, CallbackInfo ci) {
        super.onHitBlock(result);
        if (!this.level().isClientSide&&this.getOwner() instanceof Player) {
            this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 0.75F);
            this.discard();
            ci.cancel();
        }
    }

    @Shadow
    public abstract boolean isSoul();
}
