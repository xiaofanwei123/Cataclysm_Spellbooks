package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
//import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;


//继承自炎魔法术
@AutoSpellConfig
public class AbyssFireballSpell extends AbstractIgnisSpell {
    //法术命名空间
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "abyss_fireball");
    private final DefaultConfig defaultConfig;


    //法术卷轴的工具提示
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.fireball_details"),
                Component.translatable("ui.cataclysm_spellbooks.fireball_count",spellLevel));
    }

    //法术属性 法术所属学派 最大等级 冷却时间
    //法术注册的构造器
    public AbyssFireballSpell()
    {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.RARE).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(8).setCooldownSeconds(20.0).build();
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 100;
    }

    //默认
    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    //立即施法
    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    //施放法术时候屏幕上面的点数
    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        //点数与法术等级一致
        return recastCount(spellLevel);
    }


    //施法时
    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 100, castSource, null), playerMagicData);
        }
        // 基于玩家生命值发射不同炎魔弹
        final float MAX_HEALTH = entity.getMaxHealth();
        float baseHealth = entity.getHealth();
        double percent = (baseHealth/MAX_HEALTH) * 100;

        if (percent <= 30)
        {
            shootAbyssFireball(entity, level);
        }
        else if (percent <= 50)
        {
            shootFireball(entity, level, true);
        }
        else
        {
            shootFireball(entity, level, false);
        }


        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    //施法结束(上面点数消失)
    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (recastResult == RecastResult.USED_ALL_RECASTS)
        {
            var level = serverPlayer.level();
            shootFireball(serverPlayer, level,true);
            shootFireball(serverPlayer, level,true);
        }

        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
    }

    //发射紫炎魔弹方法
    private void shootAbyssFireball(LivingEntity caster, Level level)
    {
        Ignis_Abyss_Fireball_Entity fireball = new Ignis_Abyss_Fireball_Entity  (level, caster);
        fireball.setPos(caster.position().add(0, caster.getEyeHeight() - fireball.getBoundingBox().getYsize() * 0.5F, 0));
        fireball.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0, 1, 1);
        level.addFreshEntity(fireball);
    }

    //发射炎魔弹方法
    private void shootFireball(LivingEntity caster, Level level, boolean soul)
    {
        //往前挪一点 快速施法会闪瞎眼
        Ignis_Fireball_Entity fireball = new Ignis_Fireball_Entity(level, caster);
        Vec3 pos=caster.getLookAngle().normalize().scale(1.5).add(caster.getEyePosition());
        fireball.setPos(pos.x, pos.y-0.5, pos.z);
        fireball.setSoul(soul);
        fireball.shoot(caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z, 2.5F, 2.5F);
        level.addFreshEntity(fireball);
    }

    private int recastCount(int count)
    {
        return count;
    }

}
