package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedDraugur;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedEliteDraugur;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedRoyalDraugur;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ConjureThrallsSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "conjure_thralls");
    private final DefaultConfig defaultConfig;

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.thrall_count", spellLevel),
                Component.translatable("ui.cataclysm_spellbooks.draugar_hp", this.getDraugrHealth(spellLevel,caster)*0.8F),
                Component.translatable("ui.cataclysm_spellbooks.draugar_damage", this.getDraugrDamage(spellLevel, caster)*0.8F,2),

                Component.translatable("ui.cataclysm_spellbooks.royaldraugar_hp", this.getDraugrHealth(spellLevel, caster)),
                Component.translatable("ui.cataclysm_spellbooks.royaldraugar_damage", this.getDraugrDamage(spellLevel, caster)),

                Component.translatable("ui.cataclysm_spellbooks.elitedraugar_hp", this.getDraugrHealth(spellLevel, caster)*0.9F),
                Component.translatable("ui.cataclysm_spellbooks.elitedraugar_damage", this.getDraugrDamage(spellLevel, caster)*0.9F,2)
        );
    }

    public ConjureThrallsSpell() {
        this.defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.RARE).setSchoolResource(SchoolRegistry.ICE_RESOURCE).setMaxLevel(8).setCooldownSeconds(120.0).build();
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 80;
        this.baseManaCost = 50;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.DRAUGR_IDLE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.DRAUGR_HURT.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnThrallsNearby(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer, spellLevel);
        }

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.DRAUGUR_TIMER.get(),summonTimer, 0, false, true, true);
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnThrallsNearby(double x, double y, double z, LivingEntity caster, Level level, int summonTimer,int spellLevel)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.DRAUGUR_TIMER.get(),
                summonTimer, 0, false, false, false);

        boolean isRoyal = Utils.random.nextDouble() < 0.4;
        boolean isElite = Utils.random.nextDouble() < 0.5;

        boolean isBase = Utils.random.nextDouble() < 0.6;
        boolean isFullArmy = Utils.random.nextDouble() < 0.75;

        SummonedDraugur draugur = new SummonedDraugur(level, caster);
        SummonedRoyalDraugur royalDraugur = new SummonedRoyalDraugur(level, caster);
        SummonedEliteDraugur eliteDraugur = new SummonedEliteDraugur(level, caster);

        draugur.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue((double)this.getDraugrDamage(spellLevel, caster)*0.8F);
        draugur.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue((double)this.getDraugrHealth(spellLevel, caster)*0.8F);

        eliteDraugur.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue((double)(this.getDraugrDamage(spellLevel, caster)));
        eliteDraugur.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue((double)(this.getDraugrHealth(spellLevel, caster)));

        royalDraugur.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue((double) this.getDraugrDamage(spellLevel, caster)*0.9);
        royalDraugur.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue((double)(this.getDraugrHealth(spellLevel, caster))*0.9);




        Monster isBaseDraugur = isRoyal ? draugur : royalDraugur;
        Monster isEliteDraugur = isElite ? eliteDraugur : royalDraugur;

        Monster baseArmy = isBase ? isBaseDraugur : isEliteDraugur;
        Monster draugurArmry = isFullArmy ? baseArmy : isEliteDraugur;

        draugurArmry.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(draugurArmry.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        draugurArmry.moveTo(x, y, z);

        draugurArmry.addEffect(effect);

        level.addFreshEntity(draugurArmry);
    }

    private float getDraugrHealth(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster) * 2.0F;
    }

    private float getDraugrDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster) * 0.4F;
    }

}
