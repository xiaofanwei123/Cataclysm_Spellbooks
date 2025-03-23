package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import mod.azure.azurelib.animatable.client.RenderProvider;
import net.minecraft.world.item.ArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.ExcelsiusResistArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ExcelsiusResistArmorItem extends MechanicalFlightArmorItem {
    public ExcelsiusResistArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(CSArmorMaterials.EXCELSIUS_RESIST_ARMOR, slot, settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private ExcelsiusResistArmorRenderer renderer;

            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new ExcelsiusResistArmorRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
