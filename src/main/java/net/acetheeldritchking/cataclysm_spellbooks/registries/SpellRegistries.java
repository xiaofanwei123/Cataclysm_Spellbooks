package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.evocation.PilferSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.fire.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.acetheeldritchking.cataclysm_spellbooks.spells.holy.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ice.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.*;

public class SpellRegistries {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, Cataclysm_Spellbooks.MODID);

    public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell)
    {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    // ABYSSAL //

    public static final RegistryObject<AbstractSpell> ABYSSAL_BLAST = registerSpell(new AbyssalBlastSpell());

    public static final RegistryObject<AbstractSpell> ABYSSAL_PREDATOR = registerSpell(new AbyssalPredatorSpell());

    public static final RegistryObject<AbstractSpell> ABYSSAL_SLASH = registerSpell(new AbyssalSlashSpell());

    public static final RegistryObject<AbstractSpell> CONJURE_ABYSSAL_GNOWER = registerSpell(new ConjureAbyssalGnawerSpell());

    public static final RegistryObject<AbstractSpell> DEPTH_CHARGE = registerSpell(new DepthChargeSpell());

    public static final RegistryObject<AbstractSpell> DIMENSIONAL_RIFT = registerSpell(new DimensionalRiftSpell());

    public static final RegistryObject<AbstractSpell> TIDAL_GRAB = registerSpell(new TidalGrabSpell());
    // ENDER //

    public static final RegistryObject<AbstractSpell> GRAVITATION_PULL = registerSpell(new GravitationPullSpell());

    public static final RegistryObject<AbstractSpell> Gravity_Storm = registerSpell(new GravityStormSpell());

    public static final RegistryObject<AbstractSpell> VOID_RUNE_BULWARK = registerSpell(new VoidRuneBulwarkSpell());

    public static final RegistryObject<AbstractSpell> Void_Rune = registerSpell(new VoidRuneSpell());
    // EVOCATION //

    public static final RegistryObject<AbstractSpell> Pilfer = registerSpell(new PilferSpell());

    // HOLY //
    public static final RegistryObject<AbstractSpell> CONJURE_KOBOLDIATOR = registerSpell(new ConjureKoboldiatorSpell());

    public static final RegistryObject<AbstractSpell> CONJURE_KOBOLETON = registerSpell(new ConjureKoboletonSpell());
    // FIRE //

    // Abyss Fireball (Gurl even I don't know wtf it does)
    public static final RegistryObject<AbstractSpell> ABYSS_FIREBALL = registerSpell(new AbyssFireballSpell());

    public static final RegistryObject<AbstractSpell> FIREBALL = registerSpell(new AshenBreathSpell());

    public static final RegistryObject<AbstractSpell> HELLISH_BLADE = registerSpell(new HellishBladeSpell());

    public static final RegistryObject<AbstractSpell> INCINERATION = registerSpell(new IncinerationSpell());

    public static final RegistryObject<AbstractSpell> BONE_PIERCE = registerSpell(new BonePierceSpell());

    public static final RegistryObject<AbstractSpell> BONE_STORM = registerSpell(new BoneStormSpell());

    public static final RegistryObject<AbstractSpell> CONJURE_IGNITED_REINFORCEMENT = registerSpell(new ConjureIgnitedReinforcement());

    public static final RegistryObject<AbstractSpell> INFERNAL_STRIKE = registerSpell(new InfernalStrikeSpell());

    public static final RegistryObject<AbstractSpell> TECTONIC_TREMBLE = registerSpell(new TectonicTrembleSpell());

    public static final RegistryObject<AbstractSpell> VOID_BEAM = registerSpell(new VoidBeamSpell());

    // LIGHTNING //


    // ICE //

    public static final RegistryObject<AbstractSpell> CONJURE_THRALLS = registerSpell(new ConjureThrallsSpell());

    //public static final RegistryObject<AbstractSpell> CONJURE_APTRGANGR = registerSpell(new ConjureAptrgangrSpell());

    public static final RegistryObject<AbstractSpell> FORGONE_RAGE = registerSpell(new ForgoneRageSpell());

    public static final RegistryObject<AbstractSpell> CONJURE_APTRGANGR = registerSpell(new ConjureAptrgangrSpell());


    public static final RegistryObject<AbstractSpell> DESPAIR = registerSpell(new DespairAxeBladeSpell());

    public static final RegistryObject<AbstractSpell> CURSED_RUSH = registerSpell(new CursedRushSpell());

    public static final RegistryObject<AbstractSpell> MALEVOLENT_BATTLEFIELD = registerSpell(new MalevolentBattlefieldSpell());
    // NATURE //

    public static final RegistryObject<AbstractSpell> AMETHYST_PUNCTURE = registerSpell(new AmethystPunctureSpell());

    public static final RegistryObject<AbstractSpell> DESERT_WINDS = registerSpell(new DesertWindsSpell());

    public static final RegistryObject<AbstractSpell> MONOLITH_CRASH = registerSpell(new MonolithCrashSpell());

    public static final RegistryObject<AbstractSpell> SANDSTORM = registerSpell(new SandstormSpell());
    // TECHNOMANCY //
    // EMP (Cast an emp blast?)

    // Lock-on (Summon a target particle above the entity's head, stuns and incapacitates them for a few seconds)

    // Hijack (Steals a target's summons for yourself)

    // Laserbolt (Shoots out the little Harbinger small laser)

    // Atomic Laser (Harbinger big laser blast)

    // Botnet Swarm (Summons a swarm of Watchers that act as a counterspell projectile)

    // Missile Launch (Shoots out a missile)

    // Construct: Watchers (Summons a group of Watchers)

    // Construct: Prowler (Summons a Prowler)

    // DDoS (Name WIP, AoE counterspell)

    // Shutdown (Prevent the target from attacking or using items. Does not stack with Lock-on)

    // Rewire (Buff selected summons' speed and damage, reducing their armor and armor toughness)

    // Hardware Update (Increases your damage and armor, does not stack with charge/clears it)

    // Software Update (Increases your speed and cooldown, does not stack with charge and haste/clears it)

    // Circuit Breaker (Chain lightning but RED, gives a miniscule amount of lifesteal and weakens targets)

    // Bothearder (AoE summon steal)


    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
