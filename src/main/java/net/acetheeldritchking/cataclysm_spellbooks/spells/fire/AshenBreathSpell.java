package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;
import com.github.L_Ender.cataclysm.entity.projectile.Ashen_Breath_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

//继承自炎魔法术
@AutoSpellConfig
public class AshenBreathSpell extends AbstractIgnisSpell {
    //法术命名空间
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "ashen_breath");
    private final DefaultConfig defaultConfig;

    //法术卷轴的工具提示
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage",
                        Utils.stringTruncation(getDamage(spellLevel, caster), 1))
        );
    }

    //法术属性
    //法术注册的构造器
    public AshenBreathSpell()
    {
        this.defaultConfig = (new DefaultConfig()).setMinRarity(SpellRarity.RARE).setSchoolResource(SchoolRegistry.FIRE_RESOURCE).setMaxLevel(5).setCooldownSeconds(12.0).build();
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 2;
        this.castTime = 100;
        this.baseManaCost = 10;
    }

    //默认配置
    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    //连续施法，每秒2次
    @Override
    public CastType getCastType() {
        return CastType.CONTINUOUS;
    }

    //法术施放时
    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterX = entity.getX();
        double casterY = CSUtils.getEyeHeight(entity);
        double casterZ = entity.getZ();

        //new一个实体同时传给他参数
        Ashen_Breath_Entity breath = new Ashen_Breath_Entity(ModEntities.ASHEN_BREATH.get(), level, getDamage(spellLevel, entity), entity);
        breath.absMoveTo(casterX, casterY, casterZ, entity.getYHeadRot(), entity.getXRot());
        level.addFreshEntity(breath);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster);
    }
}

