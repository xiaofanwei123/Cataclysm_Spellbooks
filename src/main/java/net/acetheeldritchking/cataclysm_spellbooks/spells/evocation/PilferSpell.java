//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.acetheeldritchking.cataclysm_spellbooks.spells.evocation;

import com.github.L_Ender.cataclysm.init.ModTag;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ICastData;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import org.jetbrains.annotations.Nullable;


@AutoSpellConfig
public class PilferSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "pilfer");
    private final DefaultConfig defaultConfig;

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.pilfer_priority"));
    }

    public PilferSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE).setMaxLevel(3).setCooldownSeconds(100.0).build();
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 0;
        this.castTime = 20;
        this.baseManaCost = 110;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public CastType getCastType() {
        return CastType.LONG;
    }

    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return this.getCastTime(spellLevel);
    }

    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_HORIZONTAL_SWING_ANIMATION;
    }

    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.PLAYER_ATTACK_SWEEP);
    }

    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, spellLevel * 2, 0.15F);
    }

    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        ICastData var7 = playerMagicData.getAdditionalCastData();
        if (var7 instanceof TargetEntityCastData targetEntityCastData) {
            LivingEntity targetEntity = targetEntityCastData.getTarget((ServerLevel)level);
            if (targetEntity != null) {
                ItemStack offhandItem = targetEntity.getOffhandItem();
                ItemStack mainhandItem = targetEntity.getMainHandItem();
                int i;
                ItemStack offhandCopyItem;
                if (!offhandItem.isEmpty()) {
                    if (!offhandItem.is(ModTag.STICKY_ITEM)) {
                        i = offhandItem.getCount();
                        offhandCopyItem = offhandItem.copy();
                        offhandCopyItem.setCount(1);
                        this.stealItemDrop(offhandCopyItem, targetEntity);
                        targetEntity.setItemSlot(EquipmentSlot.OFFHAND, offhandItem.split(i - 1));
                    }
                } else if (!mainhandItem.isEmpty() && !offhandItem.is(ModTag.STICKY_ITEM)) {
                    i = mainhandItem.getCount();
                    offhandCopyItem = mainhandItem.copy();
                    offhandCopyItem.setCount(1);
                    this.stealItemDrop(offhandCopyItem, targetEntity);
                    targetEntity.setItemSlot(EquipmentSlot.MAINHAND, mainhandItem.split(i - 1));
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private ItemEntity stealItemDrop(ItemStack itemStack, LivingEntity target) {
        if (itemStack.isEmpty()) {
            return null;
        } else if (target.level().isClientSide) {
            return null;
        } else {
            double d0 = target.getEyeY() - 0.30000001192092896;
            ItemEntity itemEntity = new ItemEntity(target.level(), target.getX(), d0, target.getZ(), itemStack);
            itemEntity.setDefaultPickUpDelay();
            itemEntity.setExtendedLifetime();
            float targetSinX = Mth.sin((float)((double)target.getXRot() * 0.017453292519943295));
            float targetCosX = Mth.cos((float)((double)target.getXRot() * 0.017453292519943295));
            float targetSinY = Mth.sin((float)((double)target.getYRot() * 0.017453292519943295));
            float targetCosY = Mth.cos((float)((double)target.getYRot() * 0.017453292519943295));
            float f5 = (float)((double)target.getRandom().nextFloat() * 6.283185307179586);
            float f6 = 0.02F * target.getRandom().nextFloat();
            itemEntity.setDeltaMovement((double)(-targetSinY * targetCosX * 0.3F) + Math.cos((double)f5) * (double)f6, (double)(-targetSinX * 0.3F + 0.1F + (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.1F), (double)(targetCosY * targetCosX * 0.3F) + Math.sin((double)f5) * (double)f6);
            target.level().addFreshEntity(itemEntity);
            return itemEntity;
        }
    }
}

