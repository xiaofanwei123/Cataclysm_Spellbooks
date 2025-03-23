package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;


import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

//咒翼灵骸
public abstract class AbstractMaledictusSpell extends AbstractSpell {
    public AbstractMaledictusSpell() {
    }

    public boolean allowLooting() {
        return false;
    }

    //手持咒魂锭才能做
    public boolean canBeCraftedBy(Player player) {
        Item cursium = (Item) ModItems.CURSIUM_INGOT.get();
        return player.getMainHandItem().is(cursium);
    }
}
