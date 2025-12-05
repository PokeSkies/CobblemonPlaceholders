package com.pokeskies.cobblemonplaceholders

import com.pokeskies.cobblemonplaceholders.commands.BaseCommands
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.services.IPlaceholderService
import com.pokeskies.cobblemonplaceholders.placeholders.services.PlaceholderServices
import com.pokeskies.cobblemonplaceholders.placeholders.types.misc.MolangPlayer
import com.pokeskies.cobblemonplaceholders.placeholders.types.misc.MolangServer
import com.pokeskies.cobblemonplaceholders.placeholders.types.party.*
import com.pokeskies.cobblemonplaceholders.placeholders.types.pc.PCBoxCount
import com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex.*
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.*
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting
import net.fabricmc.loader.api.FabricLoader
import net.kyori.adventure.platform.fabric.FabricServerAudiences
import net.minecraft.server.MinecraftServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.util.stream.Stream

class CobblemonPlaceholders : ModInitializer {
    companion object {
        lateinit var INSTANCE: CobblemonPlaceholders
        val LOGGER: Logger = LogManager.getLogger("cobblemonplaceholders")
    }

    lateinit var configDir: File

    lateinit var adventure: FabricServerAudiences
    lateinit var server: MinecraftServer

    var placeholderServices: List<IPlaceholderService> = emptyList()

    override fun onInitialize() {
        INSTANCE = this

        this.configDir = File(FabricLoader.getInstance().configDirectory, "cobblemonplaceholders")
        ConfigManager.init()

        ServerLifecycleEvents.SERVER_STARTING.register(ServerStarting { server: MinecraftServer? ->
            this.adventure = FabricServerAudiences.of(
                server!!
            )
            this.server = server
            Utils.printInfo("Initializing mod and registering placeholders...")
            this.placeholderServices = PlaceholderServices.getActiveServices()
            registerPlaceholders()
            Utils.printInfo("All placeholders now registered!")
        })
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            BaseCommands().register(
                dispatcher
            )
        }
    }

    fun reload() {
        ConfigManager.reload()
    }

    private fun registerPlaceholders() {
        // SERVER PLACEHOLDERS
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
            SpeciesLabels(),
            SpeciesLabelsHas(),
            PokedexTotal(),
            MolangServer(),
        ).forEach { placeholder -> placeholderServices.forEach { it.registerServer(placeholder) } }

        // PLAYER PLACEHOLDERS
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
            PartyOTUUID(),
            PartyOTName(),
            PartyLegendary(),
            PartyMythical(),
            PartyUltraBeast(),
            PartyPropertyGet(),
            PartyPropertyHas(),
            PokedexAmountSeen(),
            PokedexAmountCaught(),
            PokedexPercentSeen(),
            PokedexPercentCaught(),
            PokedexSpeciesSeen(),
            PokedexSpeciesCaught(),
            PokedexShiniesCaught(),
            PokedexShiniesSeen(),
            MolangPlayer(),
            PCBoxCount(),
        ).forEach { placeholder -> placeholderServices.forEach { it.registerPlayer(placeholder) } }

        placeholderServices.forEach { it.finalizeRegister() }
    }
}
