package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import mod.azure.azurelib.animatable.client.RenderProvider;
import net.minecraft.world.item.ArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.MonstrousWizardHatArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class MonstrousWizardHatArmorItem extends ImbuableCataclysmArmor {
    public MonstrousWizardHatArmorItem(ArmorItem.Type slot, Item.Properties settings) {
        super(CSArmorMaterials.MONSTROUS_WIZARD_ARMOR, slot, settings);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private  MonstrousWizardHatArmorRenderer renderer;

            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new  MonstrousWizardHatArmorRenderer();
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm.desc").withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm2.desc").withStyle(ChatFormatting.DARK_GREEN));
    }
}
