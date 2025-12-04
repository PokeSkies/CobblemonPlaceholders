package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import eu.pb4.placeholders.api.PlaceholderContext
import eu.pb4.placeholders.api.PlaceholderResult
import eu.pb4.placeholders.api.Placeholders
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

class PlaceholderAPIService: IPlaceholderService {
    init {
        Utils.printInfo("PlaceholderAPI found, loading placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        placeholder.id().forEach { id ->
            Placeholders.register(ResourceLocation.fromNamespaceAndPath("cobblemon", id)) { ctx, arg ->
                val player = ctx.player ?: return@register PlaceholderResult.invalid("NO PLAYER")
                val args = if (arg != null) parse(arg, player).split(":") else emptyList()
                val result = placeholder.handle(player, args)
                return@register if (result.isSuccessful) {
                    PlaceholderResult.value(CobblemonPlaceholders.INSTANCE.adventure.toNative(result.asComponent()))
                } else {
                    PlaceholderResult.invalid(result.string)
                }
            }
        }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        placeholder.id().forEach { id ->
            Placeholders.register(ResourceLocation.fromNamespaceAndPath("cobblemon", id)) { ctx, arg ->
                val args = if (arg != null) parse(arg, ctx.player).split(":") else emptyList()
                val result = placeholder.handle(args.map { parse(it) })
                return@register if (result.isSuccessful) {
                    PlaceholderResult.value(CobblemonPlaceholders.INSTANCE.adventure.toNative(result.asComponent()))
                } else {
                    PlaceholderResult.invalid(result.string)
                }
            }
        }
    }

    override fun finalizeRegister() {

    }

    override fun parse(input: String, player: ServerPlayer?): String {
        return Placeholders.parseText(Component.literal(input),
            if (player != null)
                PlaceholderContext.of(player)
            else
                PlaceholderContext.of(CobblemonPlaceholders.INSTANCE.server)
        ).string
    }
}
