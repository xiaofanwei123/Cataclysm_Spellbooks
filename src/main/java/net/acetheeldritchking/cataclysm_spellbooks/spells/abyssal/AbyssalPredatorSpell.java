package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.List;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@AutoSpellConfig
public class AbyssalPredatorSpell extends AbstractAbyssalSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "abyssal_predator");
    private final DefaultConfig defaultConfig;

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.effect_length", new Object[]{Utils.timeFromTicks(this.getSpellPower(spellLevel, caster) * 20.0F, 1)}),
                Component.translatable("attribute.modifier.plus.1", new Object[]{Utils.stringTruncation((double)this.getPercentageSwimSpeed(spellLevel, caster), 0),
                        Component.translatable("attribute.name.forge.swim_speed")}), Component.translatable("attribute.modifier.plus.1", new Object[]{Utils.stringTruncation((double)this.getPercentageAttackDamage(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_damage")}), Component.translatable("attribute.modifier.plus.1", new Object[]{Utils.stringTruncation((double)this.getPercentageAttackSpeed(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_speed")}));
    }

    public AbyssalPredatorSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.RARE).setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE).setMaxLevel(3).setCooldownSeconds(40.0).build();
        this.manaCostPerLevel = 25;
        this.baseSpellPower = 30;
        this.spellPowerPerLevel = 8;
        this.castTime = 0;
        this.baseManaCost = 55;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public CastType getCastType() {
        return CastType.INSTANT;
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get(), (int)(this.getSpellPower(spellLevel, entity) * 20.0F), spellLevel - 1, false, false, true));
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getPercentageSwimSpeed(int spellLevel, LivingEntity caster) {
        return (float)spellLevel * 0.4F * 100.0F;
    }

    private float getPercentageAttackDamage(int spellLevel, LivingEntity caster) {
        return (float)spellLevel * 0.2F * 100.0F;
    }

    private float getPercentageAttackSpeed(int spellLevel, LivingEntity caster) {
        return (float)spellLevel * 0.2F * 100.0F;
    }
}
