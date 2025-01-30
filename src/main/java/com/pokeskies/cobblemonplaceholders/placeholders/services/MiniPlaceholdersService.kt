package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.level.ServerPlayer

class MiniPlaceholdersService: IPlaceholderService {
    private val builder = Expansion.builder("cobblemon")

    init {
        Utils.printInfo("MiniPlaceholders found, loading placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        builder.filter(ServerPlayer::class.java)
            .audiencePlaceholder(placeholder.id()) { audience, queue, _ ->
                val arguments: MutableList<String> = mutableListOf()
                while (queue.peek() != null) {
                    arguments.add(queue.pop().toString())
                }
                return@audiencePlaceholder Tag.inserting(placeholder.handle(audience as ServerPlayer, arguments).result)
            }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        builder.globalPlaceholder(placeholder.id()) { queue, _ ->
            val arguments: MutableList<String> = mutableListOf()
            while (queue.peek() != null) {
                arguments.add(queue.pop().toString())
            }
            return@globalPlaceholder Tag.inserting(placeholder.handle(arguments).result)
        }
    }

    override fun finalizeRegister() {
        builder.build().register()
    }
}
