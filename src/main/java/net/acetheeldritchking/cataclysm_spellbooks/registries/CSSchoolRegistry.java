package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSTags;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CSSchoolRegistry {
    public static final DeferredRegister<SchoolType> CATACLYSM_SCHOOLS;
    public static final ResourceLocation ABYSSAL_RESOURCE;
    public static final Supplier<SchoolType> ABYSSAL;

    public CSSchoolRegistry() {
    }

    public static RegistryObject<SchoolType> registerSchool(SchoolType schoolType) {
        return CATACLYSM_SCHOOLS.register(schoolType.getId().getPath(), () -> {
            return schoolType;
        });
    }

    public static void register(IEventBus eventBus) {
        CATACLYSM_SCHOOLS.register(eventBus);
    }

//    private static Supplier<SchoolType> registerSchool(SchoolType type) {
//        return CATACLYSM_SCHOOLS.register(type.getId().getPath(), () -> {
//            return type;
//        });
//    }

    static {
        CATACLYSM_SCHOOLS = DeferredRegister.create(SchoolRegistry.SCHOOL_REGISTRY_KEY, "cataclysm_spellbooks");
        ABYSSAL_RESOURCE = new ResourceLocation(Cataclysm_Spellbooks.MODID,"abyssal");
        ABYSSAL = registerSchool(new SchoolType(ABYSSAL_RESOURCE, CSTags.ABYSSAL_FOCUS,
                Component.translatable("school.cataclysm_spellbooks.abyssal").withStyle(Style.EMPTY.withColor(3544428)),
                LazyOptional.of(CSAttributeRegistry.ABYSSAL_MAGIC_POWER::get),
                LazyOptional.of(CSAttributeRegistry.ABYSSAL_MAGIC_RESIST::get),
                LazyOptional.of(SoundRegistry.EVOCATION_CAST::get),
                CSDamageTypes.ABYSSAL_MAGIC));

    }
}

