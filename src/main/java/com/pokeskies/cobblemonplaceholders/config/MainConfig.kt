package com.pokeskies.cobblemonplaceholders.config

import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.placeholders.types.party.*
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.SpeciesAbilities
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.SpeciesEggGroups
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.SpeciesTypes

class MainConfig(
    val placeholders: PlaceholderOptions = PlaceholderOptions()
) {
    class PlaceholderOptions(
        val party: Party = Party(),
        val species: Species = Species()
    ) {
        class Party(
            @SerializedName("invalid_slot")
            val invalidSlot: String = "Invalid party slot argument (1-6)!",
            @SerializedName("empty_slot")
            val emptySlot: String = "Empty Slot",

            val aspects: PartyAspects.Options = PartyAspects.Options(),
            @SerializedName("aspects_has")
            val aspectsHas: PartyAspectsHas.Options = PartyAspectsHas.Options(),
            val moveset: PartyMoveset.Options = PartyMoveset.Options(),
            @SerializedName("ivs_percent")
            val ivsPercent: PartyIVsPercent.Options = PartyIVsPercent.Options(),
            @SerializedName("evs_percent")
            val evsPercent: PartyEVsPercent.Options = PartyEVsPercent.Options(),
            val nickname: PartyNickname.Options = PartyNickname.Options(),
        ) {
            override fun toString(): String {
                return "Party(invalidSlot='$invalidSlot', emptySlot='$emptySlot', aspects=$aspects, aspectsHas=$aspectsHas, moveset=$moveset, ivsPercent=$ivsPercent, evsPercent=$evsPercent, nickname=$nickname)"
            }
        }

        class Species(
            @SerializedName("invalid_species")
            val invalidSpecies: String = "Provide a valid species argument!",

            val abilities: SpeciesAbilities.Options = SpeciesAbilities.Options(),
            val egggroups: SpeciesEggGroups.Options = SpeciesEggGroups.Options(),
            val types: SpeciesTypes.Options = SpeciesTypes.Options(),
        ) {
            override fun toString(): String {
                return "Species(invalidSpecies='$invalidSpecies', abilities=$abilities, egggroups=$egggroups, types=$types)"
            }
        }

        override fun toString(): String {
            return "PlaceholderOptions(party=$party, species=$species)"
        }
    }

    override fun toString(): String {
        return "MainConfig(placeholders=$placeholders)"
    }
}