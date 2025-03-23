package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

//炎魔法术
public abstract class AbstractIgnisSpell extends AbstractSpell {

    //炎魔法术继承这个类，因为都为无法获取和制作
    @Override
    public boolean allowLooting() {
        return false;
    }

    //玩家手持BURNING_ASHES才可以学习炎魔法术
    @Override
    public boolean canBeCraftedBy(Player player) {
        Item burningEmbers = ModItems.BURNING_ASHES.get();
        return player.getMainHandItem().is(burningEmbers);
    }
}
