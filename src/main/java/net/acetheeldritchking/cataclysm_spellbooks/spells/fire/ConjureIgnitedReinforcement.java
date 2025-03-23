package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedIgnitedBerserker;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedIgnitedRevenant;
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


//继承炎魔法术
@AutoSpellConfig
public class ConjureIgnitedReinforcement extends AbstractIgnisSpell {
    //法术命名空间
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "conjure_ignited_reinforcement");
    private final DefaultConfig defaultConfig;


    //法术卷轴工具提示
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.ignited_count", spellLevel),
            Component.translatable("ui.cataclysm_spellbooks.berserker_hp", this.getBerserkerHealth(spellLevel,caster)),
            Component.translatable("ui.cataclysm_spellbooks.berserker_damage", this.getBerserkerDamage(spellLevel, caster)),

            Component.translatable("ui.cataclysm_spellbooks.revenant_hp", this.getRevenantHealth(spellLevel, caster)),
            Component.translatable("ui.cataclysm_spellbooks.revenant_damage", this.getRevenantDamage(spellLevel, caster)));
    }

    //法术属性
    public ConjureIgnitedReinforcement()
    {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(3).setCooldownSeconds(100.0).build();
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

    //法术释放时候的声音
    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.REVENANT_IDLE.get());
    }

    //法术释放完成时候的声音
    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.REVENANT_HURT.get());
    }

    //施法
    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            //随机位置
            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnIgnitedNearby(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer,spellLevel);
        }

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.IGNITED_TIMER.get(), summonTimer, 0, false, true, true);
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    //生成方法 加入属性变化
    private void spawnIgnitedNearby(double x, double y, double z, LivingEntity caster, Level level, int summonTimer,int spellLevel) {
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.IGNITED_TIMER.get(),
                summonTimer, 0, false, false, false);
        boolean isBerserker = Utils.random.nextDouble() < 0.7f;

        SummonedIgnitedRevenant revenantEntity = new SummonedIgnitedRevenant(level, caster);
        SummonedIgnitedBerserker berserkerEntity = new SummonedIgnitedBerserker(level, caster);
        berserkerEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(this.getBerserkerDamage(spellLevel, caster));
        berserkerEntity.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(this.getBerserkerHealth(spellLevel,caster));
        berserkerEntity.setHealth(berserkerEntity.getMaxHealth());

        revenantEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(this.getRevenantDamage(spellLevel, caster));
        revenantEntity.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(this.getRevenantHealth(spellLevel,caster));
        revenantEntity.setHealth(revenantEntity.getMaxHealth());

        Monster ignited = isBerserker ? berserkerEntity : revenantEntity;

        ignited.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(ignited.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        ignited.moveTo(x, y, z);

        ignited.addEffect(effect);

        level.addFreshEntity(ignited);
    }
    }
    private float getBerserkerHealth(int spellLevel, LivingEntity caster) {
        return 25.0F + (float)spellLevel*this.getSpellPower(spellLevel, caster);
    }

    private float getBerserkerDamage(int spellLevel, LivingEntity caster) {
        float baseDamage = (float) (2.0F + this.getSpellPower(spellLevel, caster)/2);
        return (float)((double)baseDamage);
    }

    private float getRevenantHealth(int spellLevel, LivingEntity caster) {
        return 30.0F + (float)spellLevel*this.getSpellPower(spellLevel, caster);
    }

    private float getRevenantDamage(int spellLevel, LivingEntity caster) {
        float baseDamage = (float) (2.0F + this.getSpellPower(spellLevel, caster)/2.5);
        return (float)((double)baseDamage);
    }
}
