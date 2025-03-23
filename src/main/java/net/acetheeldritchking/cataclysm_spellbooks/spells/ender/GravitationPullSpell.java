package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;

@AutoSpellConfig
public class GravitationPullSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "gravitation_pull");
    private final DefaultConfig defaultConfig;

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.gravitation"),
                Component.translatable("ui.cataclysm_spellbooks.range",
                        new Object[]{getRange(spellLevel, caster)}),
                Component.translatable("ui.cataclysm_spellbooks.difference", new Object[]{this.getDifference(spellLevel, caster)}));
    }

    public GravitationPullSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.RARE).setSchoolResource(SchoolRegistry.ENDER_RESOURCE).setMaxLevel(5).setCooldownSeconds(30.0).build();
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 5;
        this.castTime = 100;
        this.baseManaCost = 10;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public CastType getCastType() {
        return CastType.CONTINUOUS;
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.PORTAL_AMBIENT);
    }

    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.PLANAR_SIGHT.get(),2*30, 0, false, true, true);
        entity.addEffect(effect);
        entity.level().addParticle(new StormParticle.OrbData(0.4F, 0.1F, 0.8F, 2.5F + entity.getRandom().nextFloat() * 0.9F, 5.0F + entity.getRandom().nextFloat() * 0.9F, entity.getId()), entity.getX(), entity.getY(), entity.getZ(), 0.0, 0.0, 0.0);
        double radius=getRange(spellLevel, entity);
        MobEffectInstance effect2 = new MobEffectInstance(MobEffects.GLOWING,2*30, 0, false, true, true);

        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        entitiesNearby.forEach(target -> {
            target.addEffect(effect2);
            if (target == entity || target instanceof Player player && player.getAbilities().invulnerable)
            {
                return;
            }
            Vec3 pos = entity.position();
            Vec3 pos2 = target.position();
            double dx = pos2.x() - pos.x();
            double dy = pos2.y() - pos.y();
            double dz = pos2.z() - pos.z();
            double length = Math.sqrt(dx*dx  + dy*dy + dz*dz);
            double speedMultiplier;
            float distance = target.distanceTo(entity);
            if (entity.isCrouching()) {
                speedMultiplier = (double) spellLevel /2;
            }
            else {
                speedMultiplier = (distance <= 3 ? 0: (double) - spellLevel / 2);
            }
            if (length > 0) {
                dx /= length;
                dy /= length;
                dz /= length;
            } else {
                dx = dy = dz = 0;
            }
            Vec3 finalSpeed = new Vec3(
                    dx * speedMultiplier,
                    dy * speedMultiplier,
                    dz * speedMultiplier
            );
            target.setDeltaMovement(finalSpeed);
        });

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }


    private float getDifference(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster)/2;
    }


    private float getRange(int spellLevel, LivingEntity caster)
    {
        Item gauntlet = ModItems.GAUNTLET_OF_GUARD.get();
        return (caster.getOffhandItem().is(gauntlet) || caster.getMainHandItem().is(gauntlet)) ? spellLevel * 15:spellLevel * 10;
    }
}

