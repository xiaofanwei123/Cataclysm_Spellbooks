package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.Koboleton_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class SummonedKoboleton extends Koboleton_Entity implements MagicSummon {
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;

    public SummonedKoboleton(EntityType entity, Level world) {
        super(entity, world);
        xpReward = 0;
    }

    public SummonedKoboleton(Level level, LivingEntity owner) {
        this(CSEntityRegistry.SUMMONED_KOBOLETON.get(), level);
        setSummoner(owner);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.getAvailableGoals().removeIf((goal) -> goal.getGoal() instanceof HurtByTargetGoal || goal.getGoal() instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == this.getSummoner())).setAlertOthers());
        this.goalSelector.addGoal(7, new GenericFollowOwnerGoal(this, this::getSummoner, 1.0f, 10, 0, false, 50));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 9.0F));
        super.registerGoals();
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        Entity entity = source.getDirectEntity();
        if (entity instanceof SummonedKoboldiator)
        {
            return false;
        }

        return super.hurt(source, damage);
    }

    @Override
    public LivingEntity getSummoner() {
        return OwnerHelper.getAndCacheOwner(level(), cachedSummoner, summonerUUID);
    }

    public void setSummoner(@Nullable LivingEntity owner)
    {
        if (owner != null)
        {
            this.summonerUUID = owner.getUUID();
            this.cachedSummoner = owner;
        }
    }

    // Attacks and Death
    @Override
    public void die(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.die(pDamageSource);
    }

    @Override
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this, CSPotionEffectRegistry.KOBOLDETON_TIMER.get());
        super.onRemovedFromWorld();
    }

    @Override
    public void onUnSummon() {
        if (!level().isClientSide)
        {
            MagicManager.spawnParticles(level(), ModParticle.SANDSTORM.get(),
                    getX(), getY(), getZ(),
                    25,
                    level().random.nextGaussian() * 0.007D,
                    level().random.nextGaussian() * 0.009D,
                    level().random.nextGaussian() * 0.007D,
                    0.1, false);
            discard();
        }
    }

    @Override
    public boolean isAlliedTo(Entity entityIn) {
        if (entityIn == this)
        {
            return true;
        }
        else if (super.isAlliedTo(entityIn))
        {
            return true;
        }
        else if (entityIn == getSummoner())
        {
            return true;
        }
        // He's a little stupid, he can't attack his own people </3
        else if (!(entityIn instanceof SummonedKoboleton))
        {
            return false;
        }
        else
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        return Utils.doMeleeAttack(this, pEntity, ((AbstractSpell) SpellRegistries.CONJURE_KOBOLETON.get()).getDamageSource(this, this.getSummoner()));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    // NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.summonerUUID = OwnerHelper.deserializeOwner(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        OwnerHelper.serializeOwner(pCompound, summonerUUID);
    }


}
