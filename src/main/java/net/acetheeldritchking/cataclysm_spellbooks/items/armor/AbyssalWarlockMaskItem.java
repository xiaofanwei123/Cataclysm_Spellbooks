package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import mod.azure.azurelib.animatable.client.RenderProvider;
import net.minecraft.world.item.ArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.AbyssalWarlockMaskRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class AbyssalWarlockMaskItem extends ImbuableCataclysmArmor{
    public AbyssalWarlockMaskItem(ArmorItem.Type slot, Item.Properties settings) {
        super(CSArmorMaterials.ABYSSAL_WARLOCK_ARMOR, slot, settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private AbyssalWarlockMaskRenderer renderer;

            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new AbyssalWarlockMaskRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
