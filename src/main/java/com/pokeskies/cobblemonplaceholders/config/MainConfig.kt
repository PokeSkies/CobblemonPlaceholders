package com.pokeskies.cobblemonplaceholders.config

import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.placeholders.types.party.*
import com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex.PokedexPercentCaught
import com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex.PokedexPercentSeen
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.*

class MainConfig(
    val placeholders: PlaceholderOptions = PlaceholderOptions()
) {
    class PlaceholderOptions(
        val party: Party = Party(),
        val species: Species = Species(),
        val pokedex: Pokedex = Pokedex(),
        val misc: Misc = Misc()
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
            @SerializedName("ot_uuid")
            val otUUID: PartyOTUUID.Options = PartyOTUUID.Options(),
            @SerializedName("ot_name")
            val otName: PartyOTName.Options = PartyOTName.Options(),
            @SerializedName("custom_property")
            val customProperty: PartyCustomProperty.Options = PartyCustomProperty.Options(),
        ) {
            override fun toString(): String {
                return "Party(invalidSlot='$invalidSlot', emptySlot='$emptySlot', aspects=$aspects, " +
                        "aspectsHas=$aspectsHas, moveset=$moveset, ivsPercent=$ivsPercent, evsPercent=$evsPercent, " +
                        "nickname=$nickname, otUUID=$otUUID, otName=$otName)"
            }
        }

        class Species(
            @SerializedName("invalid_species")
            val invalidSpecies: String = "Provide a valid species argument!",

            val abilities: SpeciesAbilities.Options = SpeciesAbilities.Options(),
            val egggroups: SpeciesEggGroups.Options = SpeciesEggGroups.Options(),
            val types: SpeciesTypes.Options = SpeciesTypes.Options(),
            val labels: SpeciesLabels.Options = SpeciesLabels.Options(),
            @SerializedName("labels_has")
            val labelsHas: SpeciesLabelsHas.Options = SpeciesLabelsHas.Options(),
        ) {
            override fun toString(): String {
                return "Species(invalidSpecies='$invalidSpecies', abilities=$abilities, egggroups=$egggroups, types=$types, labels=$labels, labelsHas=$labelsHas)"
            }
        }

        class Pokedex(
            @SerializedName("include_unimplemented")
            val includeUnimplemented: Boolean = false,
            @SerializedName("dex_seen_percent")
            val dexSeenPercent: PokedexPercentSeen.Options = PokedexPercentSeen.Options(),
            @SerializedName("dex_caught_percent")
            val dexCaughtPercent: PokedexPercentCaught.Options = PokedexPercentCaught.Options(),
            @SerializedName("invalid_species")
            val invalidSpecies: String = "Provide a valid species argument!",

        ) {
            override fun toString(): String {
                return "Pokedex(includeUnimplemented=$includeUnimplemented, dexSeenPercent=$dexSeenPercent, dexCaughtPercent=$dexCaughtPercent, invalidSpecies='$invalidSpecies')"
            }
        }

        class Misc(
            @SerializedName("invalid_molang")
            val invalidMolang: String = "Invalid Molang expression!",
        ) {
            override fun toString(): String {
                return "Misc(invalidMolang='$invalidMolang')"
            }
        }

        override fun toString(): String {
            return "PlaceholderOptions(party=$party, species=$species, pokedex=$pokedex)"
        }
    }

    override fun toString(): String {
        return "MainConfig(placeholders=$placeholders)"
    }
}
