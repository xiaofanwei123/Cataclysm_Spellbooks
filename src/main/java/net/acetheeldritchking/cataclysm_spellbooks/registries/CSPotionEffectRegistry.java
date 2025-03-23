package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.effect.PlanarSightEffect;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSPotionEffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Cataclysm_Spellbooks.MODID);

    public static final RegistryObject<MobEffect> SUMMON_VOID_RUNE =
            MOB_EFFECTS.register("summon_void_rune", VoidRunePotionEffect::new);

    public static final RegistryObject<MobEffect> ABYSSAL_PREDATOR_EFFECT =
            MOB_EFFECTS.register("abyssal_predator_effect", () -> new AbyssalPredatorPotionEffect(MobEffectCategory.BENEFICIAL, 5984177));

    public static final RegistryObject<MobEffect> INCAPACITATED_EFFECT =
            MOB_EFFECTS.register("incapacitated_effect", IncapacitatedPotionEffect::new);

    public static final RegistryObject<SummonTimer> ABYSSAL_GNAWER_TIMER =
            MOB_EFFECTS.register("abyssal_gnawer_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 0xbea925));

    public static final RegistryObject<SummonTimer> IGNITED_TIMER =
            MOB_EFFECTS.register("ignited_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16734003));

    public static final RegistryObject<MobEffect> WRATHFUL =
            MOB_EFFECTS.register("wrathful_effect", WrathfulPotionEffect::new);

    public static final RegistryObject<SummonTimer> KOBOLDIATOR_TIMER =
            MOB_EFFECTS.register("koboldiator_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16443474));

    public static final RegistryObject<SummonTimer> KOBOLDETON_TIMER =
            MOB_EFFECTS.register("koboleton_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16443474));

    public static final RegistryObject<MobEffect> CURSED_FRENZY =
            MOB_EFFECTS.register("cursed_frenzy", CursedFrenzyEffect::new);

    public static final RegistryObject<SummonTimer> DRAUGUR_TIMER =
            MOB_EFFECTS.register("draugur_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 4583645));

    //新药水
    public static final RegistryObject<SummonTimer> APTRGANGR_TIMER =
            MOB_EFFECTS.register("aptrgangr_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 4583645));

    public static final RegistryObject<MobEffect> PLANAR_SIGHT =
            MOB_EFFECTS.register("planar_sight", () -> new PlanarSightEffect(MobEffectCategory.BENEFICIAL, 0x6c42f5));

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
