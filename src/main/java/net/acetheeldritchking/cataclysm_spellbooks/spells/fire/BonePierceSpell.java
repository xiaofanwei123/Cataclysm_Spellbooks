package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ICastDataSerializable;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import java.util.List;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

//继承炎魔法术
@AutoSpellConfig
public class BonePierceSpell extends AbstractIgnisSpell {
    //法术命名空间
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "piercing_bone");
    private final DefaultConfig defaultConfig;

    //法术卷轴工具提示
    //加入数量环绕玩家
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.blazing_bone_speed", new Object[]{Utils.stringTruncation((double)this.getBoneSpeed(0.5F, this.getSpellPower(spellLevel, caster)), 2)}),
            Component.translatable("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 2)}),
            Component.translatable("ui.cataclysm_spellbooks.blazing_bone_count", new Object[]{Utils.stringTruncation(2*spellLevel,  0)}));

    }

    //法术属性
    public BonePierceSpell() {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.EPIC).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(8).setCooldownSeconds(20.0).build();
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 50;
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

    //屏幕上面的计数
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    //施法
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 100, castSource, (ICastDataSerializable)null), playerMagicData);
        }

        this.shootBone(entity, spellLevel, level);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    //最后一发
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (recastResult == RecastResult.USED_ALL_RECASTS) {
            Level level = serverPlayer.level();
            this.spreadBoneShoot(serverPlayer, level,recastInstance.getSpellLevel());
        }
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
    }

    //发射骨头方法
    private void shootBone(LivingEntity caster, int spellLevel, Level level) {
        caster.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 0.75F);
        double casterX = caster.getX();
        double casterY = CSUtils.getEyeHeight(caster);
        double casterZ = caster.getZ();
        Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, this.getDamage(spellLevel, caster), caster);
        blazingBone.moveTo(casterX, casterY, casterZ, 0.0F, caster.getXRot());
        float speed = 0.4F;
        float speedSpellPower = this.getBoneSpeed(speed, this.getSpellPower(spellLevel, caster));
        //他还是会下坠呀
        blazingBone.setNoGravity(true);
        blazingBone.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0.0F, speedSpellPower, 1.0F);
        level.addFreshEntity(blazingBone);
    }

    //最后发射一圈的方法
    private void spreadBoneShoot(LivingEntity caster, Level level,int count) {
        caster.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 0.75F);

        for(int i = 0; i < 4*count; ++i) {
            float throwAngle = (float)((double)i * Math.PI / count);
            double casterX = caster.getX() + (double)Mth.cos(throwAngle);
            double casterY = caster.getY() + (double)caster.getBbHeight() * 0.62;
            double casterZ = caster.getZ() + (double)Mth.sin(throwAngle);
            double angleX = (double)Mth.cos(throwAngle);
            double angleY = 0.2;
            double angleZ = (double)Mth.sin(throwAngle);
            Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, 3.0F, caster);
            blazingBone.moveTo(casterX, casterY, casterZ, (float)i * 45.0F, caster.getXRot());
            float speed = 0.6F;
            blazingBone.setNoGravity(true);
            blazingBone.shoot(angleX, angleY, angleZ, speed, 1.0F);
            level.addFreshEntity(blazingBone);
        }

    }

    private float getBoneSpeed(float speed, float spellPower) {
        return speed * spellPower;
    }


    private float getDamage(int spellLevel, LivingEntity caster) {
        return this.getSpellPower(spellLevel, caster);
    }
}

