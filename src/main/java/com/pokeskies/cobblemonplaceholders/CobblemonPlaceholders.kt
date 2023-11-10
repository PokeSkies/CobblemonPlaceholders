package com.pokeskies.cobblemonplaceholders

import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonGlobalPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.types.party.*
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.*
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStopped
import net.kyori.adventure.platform.fabric.FabricServerAudiences
import net.minecraft.server.MinecraftServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.stream.Stream


class CobblemonPlaceholders : ModInitializer {
    companion object {
        lateinit var INSTANCE: CobblemonPlaceholders
        val LOGGER: Logger = LogManager.getLogger("cobblemonplaceholders")
    }

    var adventure: FabricServerAudiences? = null
    var server: MinecraftServer? = null

    override fun onInitialize() {
        INSTANCE = this

        ServerLifecycleEvents.SERVER_STARTING.register(ServerStarting { server: MinecraftServer? ->
            this.adventure = FabricServerAudiences.of(
                server!!
            )
            this.server = server
            Utils.printInfo("Initializing mod and registering placeholders...")
            registerPlaceholders()
            Utils.printInfo("Placeholders registered!")
        })
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerStopped { server: MinecraftServer? ->
            this.adventure = null
        })
    }

    fun registerPlaceholders() {
        val builder = Expansion.builder("cobblemon")

        Stream.of(
            SpeciesName(),
            SpeciesIDNational(),
            SpeciesTypes(),
            SpeciesCatchRate(),
            SpeciesAbilities(),
            SpeciesEggGroups(),
            SpeciesMaleRatio(),
            SpeciesBaseStatsHP(),
            SpeciesBaseStatsAttack(),
            SpeciesBaseStatsDefence(),
            SpeciesBaseStatsSpecialAttack(),
            SpeciesBaseStatsSpecialDefence(),
            SpeciesBaseStatsSpeed(),
        ).forEach { placeholder: CobblemonGlobalPlaceholder -> placeholder.register(builder) }

        Stream.of(
            PartyName(),
            PartySpecies(),
            PartySpeciesID(),
            PartyNickname(),
            PartyForm(),
            PartyLevel(),
            PartyExperience(),
            PartyShiny(),
            PartyHeldItem(),
            PartyHeldItemID(),
            PartyFriendship(),
            PartyAbility(),
            PartyNature(),
            PartyGender(),
            PartyCaughtBall(),
            PartyCaughtBallID(),
            PartyDynamax(),
            PartyGigantamax(),
            PartyTeratype(),
            PartyTradable(),
            PartyMoveset(),
            PartyStatsHP(),
            PartyStatsAttack(),
            PartyStatsDefence(),
            PartyStatsSpecialAttack(),
            PartyStatsSpecialDefence(),
            PartyStatsSpeed(),
            PartyIVsHP(),
            PartyIVsAttack(),
            PartyIVsDefence(),
            PartyIVsSpecialAttack(),
            PartyIVsSpecialDefence(),
            PartyIVsSpeed(),
            PartyIVsTotal(),
            PartyIVsPercent(),
            PartyEVsHP(),
            PartyEVsAttack(),
            PartyEVsDefence(),
            PartyEVsSpecialAttack(),
            PartyEVsSpecialDefence(),
            PartyEVsSpeed(),
            PartyEVsTotal(),
            PartyEVsPercent(),
            PartyAspects(),
            PartyAspectsHas(),
        ).forEach { placeholder: CobblemonPlaceholder -> placeholder.register(builder) }

        builder.build().register()
    }
}
