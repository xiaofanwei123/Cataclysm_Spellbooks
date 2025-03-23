package net.acetheeldritchking.cataclysm_spellbooks.spells.holy;

import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import java.util.List;
import java.util.Optional;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedKoboldiator;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

//继承远古诅咒法术
@AutoSpellConfig
public class ConjureKoboldiatorSpell extends AncientCursedSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "conjure_koboldiator");
    private final DefaultConfig defaultConfig;

    //法术卷轴工具提示
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.koboldiator_count", spellLevel),

                Component.translatable("ui.cataclysm_spellbooks.aptrgangr_hp", this.getKobolediatorHealth(spellLevel, caster)),
                Component.translatable("ui.cataclysm_spellbooks.aptrgangr_damage", this.getKobolediatorDamage(spellLevel, caster)));
    }

    //法术属性
    public ConjureKoboldiatorSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.HOLY_RESOURCE).setMaxLevel(1).setCooldownSeconds(200.0).build();
        this.manaCostPerLevel = 100;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 80;
        this.baseManaCost = 250;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of((SoundEvent) ModSounds.REMNANT_CHARGE_ROAR.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;
        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnKoboldiator(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer, spellLevel);
        }

        //原mod没加药水参数
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.KOBOLDIATOR_TIMER.get(),summonTimer, 0, false, true, true);
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnKoboldiator(double x, double y, double z, LivingEntity caster, Level level, int summonTimer, int spellLevel)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.KOBOLDIATOR_TIMER.get(),
                summonTimer, 0, false, false, false);
        SummonedKoboldiator kobolediator = new SummonedKoboldiator(level, caster);

        kobolediator.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue((double)this.getKobolediatorDamage(spellLevel, caster));
        kobolediator.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue((double)this.getKobolediatorHealth(spellLevel, caster));

        kobolediator.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(kobolediator.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);
        kobolediator.moveTo(x, y, z);
        kobolediator.setSleep(false);
        kobolediator.addEffect(effect);
        level.addFreshEntity(kobolediator);
    }

    private float getKobolediatorHealth(int spellLevel,LivingEntity caster) {
        return 55.0F + (float)spellLevel * 35.0F*this.getSpellPower(spellLevel, caster);
    }

    private float getKobolediatorDamage(int spellLevel, LivingEntity caster) {
        float baseDamage = 10.0F + this.getSpellPower(spellLevel, caster) * 1.5F;
        return (float)((double)baseDamage);
    }
}

