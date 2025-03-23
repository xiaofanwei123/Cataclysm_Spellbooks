package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import java.util.List;
import java.util.Optional;

import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

//继承炎魔法术
@AutoSpellConfig
public class InfernalStrikeSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "infernal_strike");
    private final DefaultConfig defaultConfig;

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 2)}),
                Component.translatable("ui.cataclysm_spellbooks.incinerator_damage", new Object[]{Utils.stringTruncation((double)this.getBonusDamage(spellLevel, caster), 2)}));
    }

    //法术属性
    public InfernalStrikeSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.EPIC).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(8).setCooldownSeconds(1.0).build();
        this.manaCostPerLevel = 2;
        this.baseSpellPower = 2;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 85;
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
        return CastType.INSTANT;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.FLAMING_STRIKE_SWING.get());
    }

    //施法
    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        InfernalBladeProjectile infernalBlade = new InfernalBladeProjectile(level, entity);
        infernalBlade.setPos(entity.position().add(0.0, (double)entity.getEyeHeight() - infernalBlade.getBoundingBox().getYsize() * 0.5, 0.0));
        infernalBlade.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0.0F, 1.0F, 1.0F);

        //手持炎葬
        Item incinerator =ModItems.THE_INCINERATOR.get();

        float damage = this.getDamage(spellLevel, entity);
        float bonusDamage = this.getBonusDamage(spellLevel, entity);
        if (entity.getMainHandItem().is(incinerator)) {
            infernalBlade.setDamage(bonusDamage);
        } else {
            infernalBlade.setDamage(damage);
        }

        level.addFreshEntity(infernalBlade);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster) * 1.5F;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster) {
        float baseDamage = this.getDamage(spellLevel, caster);
        int bonusAmount = (int)(1.5 + (double)spellLevel);
        return baseDamage + (float)bonusAmount;
    }
}

