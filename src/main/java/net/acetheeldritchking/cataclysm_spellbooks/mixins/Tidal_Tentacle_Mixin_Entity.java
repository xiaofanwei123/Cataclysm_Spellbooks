package net.acetheeldritchking.cataclysm_spellbooks.mixins;

import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Tidal_Tentacle_Entity.class)
public abstract class Tidal_Tentacle_Mixin_Entity extends Entity {
    public Tidal_Tentacle_Mixin_Entity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "getBaseDamage",remap = false,at = @At("HEAD"), cancellable = true)
    private void getBaseDamage(CallbackInfoReturnable<Float> cir) {
        if (!this.level().isClientSide&&this.getCreatorEntity() instanceof Player ) {
            cir.setReturnValue((float) (6.0F*((Player)this.getCreatorEntity()).getAttributeValue(CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get())*(float)((Player)this.getCreatorEntity()).getAttributeValue(AttributeRegistry.SPELL_POWER.get())));
        }
    }
    @Shadow public abstract Entity getCreatorEntity();
}
