package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;


//继承炎魔法术
@AutoSpellConfig
public class IncinerationSpell extends AbstractIgnisSpell {
    //法术命名空间
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "incineration");
    private final DefaultConfig defaultConfig;

    //法术卷轴工具提示
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.cataclysm_spellbooks.flame_strikes_count", getFlameStrikeCount(spellLevel, caster)),
                Component.translatable("ui.cataclysm_spellbooks.flame_strike_time", Utils.timeFromTicks(spellLevel*30, 2)));
    }

    //法术属性
    public IncinerationSpell()
    {
        this.defaultConfig= new DefaultConfig().setMinRarity(SpellRarity.LEGENDARY).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(5).setCooldownSeconds(10.0).build();
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 60;
        this.baseManaCost = 100;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    //长时
    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    //施法音效
    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.FLAME_BURST.get());
    }

    //完毕音效
    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.SWORD_STOMP.get());
    }

    //施法动画
    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    //施法
    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        final float MAX_HEALTH = entity.getMaxHealth();
        float baseHealth = entity.getHealth();
        double percent = (baseHealth/MAX_HEALTH) * 100;

        double casterX = entity.getX();
        double casterZ = entity.getZ();
        double casterHeadY = entity.getY() + 1.0D;
        int standOnYPos = Mth.floor(entity.getY()) - 2;
        float yawRadians = (float) Math.toRadians(90.0F + entity.getYRot());
        for (int i = 0; i < getFlameStrikeCount(spellLevel, entity); i++)
        {
            double d2 = 2.25D * (i + 1);
            int j2 = (int) (1.5F * i);

            double casterXYaw = casterX + (double)Mth.cos(yawRadians) * d2;
            double casterZYaw = casterZ + (double)Mth.sin(yawRadians) * d2;

            //生命值少于50时候变成蓝色
            if (percent <= 50)
            {
                spawnFlameStrike(casterXYaw, casterZYaw, standOnYPos, casterHeadY, yawRadians, spellLevel * 30, j2, j2, level, 1.0F, true, entity, spellLevel);
            }
            else
            {
                spawnFlameStrike(casterXYaw, casterZYaw, standOnYPos, casterHeadY, yawRadians, spellLevel * 30, j2, j2, level, 1.0F, false, entity, spellLevel);
            }
        }
        ScreenShake_Entity.ScreenShake(level, entity.position(), 10.0F, 0.03F, 10, 20);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    //召唤方法
    //TODO:穿炎魔法术书或炎魔套的时候增加伤害？
    private void spawnFlameStrike(double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level level, float radius, boolean isSoul, LivingEntity caster, int spellLevel)
    {
        BlockPos pos =BlockPos.containing(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos pos1 = pos.below();
            BlockState blockState = level.getBlockState(pos1);
            if (blockState.isFaceSturdy(level, pos1, Direction.UP)) {
                if (!level.isEmptyBlock(pos)) {
                    BlockState blockState1 = level.getBlockState(pos);
                    VoxelShape voxelShape = blockState1.getCollisionShape(level, pos);
                    if (!voxelShape.isEmpty()) {
                        d0 = voxelShape.max(Direction.Axis.Y);
                    }
                }
                flag = true;
                break;
            }
            pos = pos.below();
        }
        while (pos.getY() >= Mth.floor(minY) - 1);
        if (flag) {
            level.addFreshEntity(new Flame_Strike_Entity(level, x, pos.getY() + d0, z, rotation, duration, wait, delay, radius, getDamage(spellLevel, caster), 0, isSoul, caster));
            if (isSoul) {
                level.addFreshEntity(new Flame_Strike_Entity(level, x, pos.getY() + d0, z, rotation, duration, wait, delay, radius, (float) (getDamage(spellLevel, caster) * 1.5), 0, isSoul, caster));
                //System.out.println("Is soul?");
            }
        }
    }


    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return (float) (getSpellPower(spellLevel, caster)/1.5);
    }

    private int getFlameStrikeCount(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster)/3);
    }
}
