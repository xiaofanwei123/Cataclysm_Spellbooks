package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.items.CodexOfMaliceSpellBookRenderer;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CodexOfMaliceSpellBook extends UniqueSpellBook implements GeoItem {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public CodexOfMaliceSpellBook() {
        super(SpellRarity.LEGENDARY, SpellDataRegistryHolder.of(), 12, () -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put((Attribute)AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "CMSB MANA", 300.0, AttributeModifier.Operation.ADDITION));
            builder.put((Attribute)AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("6c21841d-794e-4a06-9225-d966d8752ba4"), "CMSB ICE", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
            return builder.build();
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    //我不明白这玩意只在1.20有是什么意思
    @Override
    public Supplier<Object> getRenderProvider() {
        return GeoItem.makeRenderer(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private CodexOfMaliceSpellBookRenderer renderer = null;

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new CodexOfMaliceSpellBookRenderer();
                }

                return this.renderer;
            }
        });
    }
}
