package net.acetheeldritchking.cataclysm_spellbooks.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;

@EventBusSubscriber(
        modid = "cataclysm_spellbooks",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
public class ForgeClientEvents {
    private static final float[] VIOLET_SKY_COLOR = new float[]{0.25F, 0.0F, 0.45F};
    private static final float[] ABYSSAL_BLUE = new float[]{0.05F, 0.1F, 0.4F};

    public ForgeClientEvents() {
    }

    @SubscribeEvent
    public static void onSkyRender(ViewportEvent.ComputeFogColor event) {
        Minecraft mc = Minecraft.getInstance();
        MobEffectInstance effect;
        if (mc.player != null) {
            effect = mc.player.getEffect((MobEffect) CSPotionEffectRegistry.PLANAR_SIGHT.get());
            if (effect != null) {
                event.setRed(VIOLET_SKY_COLOR[0]);
                event.setGreen(VIOLET_SKY_COLOR[1]);
                event.setBlue(VIOLET_SKY_COLOR[2]);
            }
        }

        if (mc.player != null) {
            effect = mc.player.getEffect((MobEffect)CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get());
            if (effect != null) {
                event.setRed(ABYSSAL_BLUE[0]);
                event.setGreen(ABYSSAL_BLUE[1]);
                event.setBlue(ABYSSAL_BLUE[2]);
            }
        }

    }
}

