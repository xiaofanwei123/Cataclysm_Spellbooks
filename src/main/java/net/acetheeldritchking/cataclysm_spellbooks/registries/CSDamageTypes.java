package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;

public class CSDamageTypes {
    public static final ResourceKey<DamageType> ABYSSAL_MAGIC;

    public CSDamageTypes() {
    }

    static {
        ABYSSAL_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Cataclysm_Spellbooks.MODID, "abyssal_magic"));
    }
}
