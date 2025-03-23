package net.acetheeldritchking.cataclysm_spellbooks.spells.holy;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public abstract class AncientCursedSpell extends AbstractSpell {
    //远古诅咒法术继承这个类，因为都为无法获取和制作
    @Override
    public boolean allowLooting() {
        return false;
    }

    //玩家手持ANCIENT_METAL_INGOT才可以学习远古诅咒法术
    @Override
    public boolean canBeCraftedBy(Player player) {
        Item burningEmbers = ModItems.ANCIENT_METAL_INGOT.get();
        return player.getMainHandItem().is(burningEmbers);
    }
}
