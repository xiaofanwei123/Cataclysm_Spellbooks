package net.acetheeldritchking.cataclysm_spellbooks.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class AddToLootTableModifier extends LootModifier {
    public static final Supplier<Codec<AddToLootTableModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).
            and(ResourceLocation.CODEC.fieldOf("loot_table").
                    forGetter(m -> m.lootTable)).apply(inst, AddToLootTableModifier::new)));

    public ResourceLocation lootTable;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected AddToLootTableModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable)
    {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (!context.getQueriedLootTableId().getPath().startsWith("chests/")) {
            return generatedLoot;
        } else {
            ResourceLocation lootTablePath = new ResourceLocation(Cataclysm_Spellbooks.MODID);
            LootTable lootTable = context.getLevel().getServer().getLootData().getLootTable(lootTablePath);
            ObjectArrayList<ItemStack> additionalLoot = new ObjectArrayList();
            Objects.requireNonNull(additionalLoot);
            lootTable.getRandomItemsRaw(context, additionalLoot::add);
            generatedLoot.addAll(additionalLoot);
            return generatedLoot;
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC.get();
    }
}
