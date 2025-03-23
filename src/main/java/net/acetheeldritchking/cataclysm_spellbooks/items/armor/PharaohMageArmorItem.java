package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import mod.azure.azurelib.animatable.client.RenderProvider;
import net.minecraft.world.item.ArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.PharaohMageArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class PharaohMageArmorItem extends ImbuableCataclysmArmor {
    public PharaohMageArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(CSArmorMaterials.PHARAOH_MAGE_ARMOR, slot, settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private PharaohMageArmorRenderer renderer;

            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new PharaohMageArmorRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    // Durability
    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (CMConfig.Armor_Infinity_Durability)
        {
            super.setDamage(stack, 0);
        }
        else
        {
            super.setDamage(stack, damage);
        }
    }
}
