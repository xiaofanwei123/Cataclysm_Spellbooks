package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import mod.azure.azurelib.model.GeoModel;
import net.minecraft.resources.ResourceLocation;
import net.acetheeldritchking.cataclysm_spellbooks.Cataclysm_Spellbooks;

public class CodexOfMaliceSpellBookModel extends GeoModel<CodexOfMaliceSpellBook> {

    @Override
    public ResourceLocation getModelResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "geo/codex_of_malice.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "textures/item/spell_books/codex_of_malice_spell_book_model.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        return new ResourceLocation(Cataclysm_Spellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
