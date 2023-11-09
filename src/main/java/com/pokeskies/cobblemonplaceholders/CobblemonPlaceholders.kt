package com.pokeskies.cobblemonplaceholders

import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonAudiencePlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonGlobalPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.types.party.PartySpecies
import com.pokeskies.cobblemonplaceholders.placeholders.types.species.SpeciesName
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
            registerPlaceholders()
        })
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerStopped { server: MinecraftServer? ->
            this.adventure = null
        })
    }

    fun registerPlaceholders() {
        val builder = Expansion.builder("cobblemon")

        Stream.of<CobblemonGlobalPlaceholder>(
            SpeciesName(),
        ).forEach { placeholder: CobblemonGlobalPlaceholder -> placeholder.register(builder) }

        Stream.of<CobblemonAudiencePlaceholder>(
            PartySpecies(),
        ).forEach { placeholder: CobblemonAudiencePlaceholder -> placeholder.register(builder) }

        builder.build().register()
    }
}
