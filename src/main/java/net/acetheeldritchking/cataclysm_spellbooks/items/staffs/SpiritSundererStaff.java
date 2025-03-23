package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.util.AzureLibUtil;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.items.SpiritSundererStaffRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Rarity;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpiritSundererStaff extends StaffItem implements GeoItem {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public SpiritSundererStaff() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 4, -3,
                Map.of(
                        AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.MANA_REGEN.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .30, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                ));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        // Guh
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    //我不明白这玩意只在1.20有是什么意思
    @Override
    public Supplier<Object> getRenderProvider() {
        return GeoItem.makeRenderer(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private SpiritSundererStaffRenderer renderer = null;

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                this.renderer = new SpiritSundererStaffRenderer();
                return this.renderer;
            }
        });
    }
}
