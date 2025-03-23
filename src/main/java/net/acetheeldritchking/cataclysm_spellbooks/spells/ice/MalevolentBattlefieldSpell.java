package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


@AutoSpellConfig
public class MalevolentBattlefieldSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "malevolent_battlefield");
    private final DefaultConfig defaultConfig;

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.halberd_rings", new Object[]{spellLevel}), Component.translatable("ui.cataclysm_spellbooks.halberd_amount", new Object[]{Utils.stringTruncation((double)this.getHalberdsPerBranch(spellLevel, caster), 0)}), Component.translatable("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 1)}), Component.translatable("ui.cataclysm_spellbooks.maledictus_armory_bonus", new Object[]{Utils.stringTruncation((double)this.getBonusDamage(spellLevel, caster), 1)}));
    }

    public MalevolentBattlefieldSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.EPIC).setSchoolResource(SchoolRegistry.ICE_RESOURCE).setMaxLevel(10).setCooldownSeconds(60.0).build();
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 80;
        this.baseManaCost = 100;
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

    //施法动作
    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_MALEVOLENT_HAND_SIGN;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.WARDEN_HEARTBEAT);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of((SoundEvent)ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    //不可被打断
    @Override
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        double radius = 15.0;
        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        Iterator var8 = entitiesNearby.iterator();

        while(var8.hasNext()) {
            LivingEntity targets = (LivingEntity)var8.next();
            targets.addEffect(new MobEffectInstance(MobEffects.DARKNESS, this.getCastTime(spellLevel), 1, false, false, false));
        }

        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, this.getCastTime(spellLevel), 1, false, false, false));
        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Item soulRenderer = (Item)ModItems.SOUL_RENDER.get();
        Item annihilator = (Item)ModItems.THE_ANNIHILATOR.get();
        if (!entity.getMainHandItem().is(soulRenderer) && !entity.getMainHandItem().is(annihilator)) {
            CSUtils.spawnHalberdWindmill(spellLevel * 4, this.getHalberdsPerBranch(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, this.getDamage(spellLevel, entity), spellLevel);
        } else {
            CSUtils.spawnHalberdWindmill(spellLevel * 4, this.getHalberdsPerBranch(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, this.getBonusDamage(spellLevel, entity), spellLevel);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getHalberdsPerBranch(int spellLevel, LivingEntity caster) {
        return (int)Mth.clamp(this.getSpellPower(spellLevel, caster), 1.0F, 5.0F);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster) + 7.0F;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster) {
        float baseDamage = this.getDamage(spellLevel, caster);
        int bonusAmount = (int)(3.5 + (double)spellLevel);
        return baseDamage + (float)bonusAmount;
    }
}
