package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.fabricmc.loader.api.FabricLoader
import kotlin.reflect.full.primaryConstructor

enum class PlaceholderServices(val id: String, val clazz: Class<out IPlaceholderService>) {
    MINIPLACEHOLDERS("miniplaceholders", MiniPlaceholdersService::class.java),
    PLACEHOLDERAPI("placeholder-api", PlaceholderAPIService::class.java);

    companion object {
        fun getActiveServices(): List<IPlaceholderService> {
            return entries.filter { FabricLoader.getInstance().isModLoaded(it.id) }.mapNotNull {
                try {
                    it.clazz.kotlin.primaryConstructor!!.call()
                } catch (ex: Exception) {
                    Utils.printError("There was an exception while initializing the Placeholder Service: ${it.id}. Is it loaded?")
                    ex.printStackTrace()
                    null
                }
            }
        }
    }
}
