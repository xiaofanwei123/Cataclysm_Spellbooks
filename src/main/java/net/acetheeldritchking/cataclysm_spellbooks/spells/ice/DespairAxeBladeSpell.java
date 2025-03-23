package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
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
import java.util.List;
import java.util.Optional;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import org.jetbrains.annotations.Nullable;


//TODO:需要修改
@AutoSpellConfig
public class DespairAxeBladeSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(Cataclysm_Spellbooks.MODID, "despair_axe_blade");
    private final DefaultConfig defaultConfig;

    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)this.getDamage(spellLevel, caster), 1)}));
    }

    public DespairAxeBladeSpell() {
        this.defaultConfig = new DefaultConfig().setMinRarity(SpellRarity.COMMON).setSchoolResource(SchoolRegistry.ICE_RESOURCE).setMaxLevel(10).setCooldownSeconds(18.0).build();
        this.manaCostPerLevel = 18;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 18;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    //施放法术时候屏幕上面的点数
    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        //点数与法术等级一致
        return recastCount(spellLevel);
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.MALEDICTUS_MACE_SWING.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    //施法结束(上面点数消失)
    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (recastResult == RecastResult.USED_ALL_RECASTS) {
            var level = serverPlayer.level();
            shootMultipleAxeBlade(serverPlayer, level,recastInstance.getSpellLevel());
        }
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
    }





    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(this.getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(this.getSpellId(), spellLevel, this.getRecastCount(spellLevel, entity), 100, castSource, (ICastDataSerializable)null), playerMagicData);
        }
        shootSingleAxeBlade(entity, level,spellLevel);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void shootSingleAxeBlade(LivingEntity entity, Level level,int spellLevel) {
                Vec3 lookVector = entity.getLookAngle();
        double speedMultiplier = 2.0;
        Axe_Blade_Entity witherskull = new Axe_Blade_Entity(entity, lookVector.x, lookVector.y, lookVector.z, level, getDamage(spellLevel, entity), entity.getYRot());
        witherskull.setDeltaMovement(lookVector.scale(speedMultiplier));
        level.addFreshEntity(witherskull);
    }

    //发射最后一发
    private void shootMultipleAxeBlade(LivingEntity entity, Level level,int spellLevel) {
        float angleStep= 30.0F;
        int numberOfSkulls = 5;
        for (int i = 0; i < numberOfSkulls; ++i) {
            float angle = entity.yBodyRot + (float) (i - numberOfSkulls / 2) * angleStep;
            float rad = (float) Math.toRadians((double) angle);
            double dx = -Math.sin((double) rad);
            double dz = Math.cos((double) rad);
            Axe_Blade_Entity witherskull = new Axe_Blade_Entity(entity, dx, 0.0, dz, level, getDamage(spellLevel, entity), angle);
            witherskull.setPos(entity.getX(), entity.getY(), entity.getZ());
            level.addFreshEntity(witherskull);
        }
    }

    private float getDamage (int spellLevel, LivingEntity caster){
        return 3.0F + this.getSpellPower(spellLevel, caster) * 1.5F;
    }

    public int recastCount (int spellLevel){
        return spellLevel;
    }

}

