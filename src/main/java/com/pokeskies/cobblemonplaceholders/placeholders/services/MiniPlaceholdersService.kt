package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import io.github.miniplaceholders.api.MiniPlaceholders
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.minecraft.server.level.ServerPlayer

class MiniPlaceholdersService: IPlaceholderService {
    private val builder = Expansion.builder("cobblemon")
    private val miniMessage = MiniMessage.builder()
        .tags(TagResolver.builder().build())
        .build()

    init {
        Utils.printInfo("MiniPlaceholders found, loading placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        placeholder.id().forEach { id ->
            builder.filter(ServerPlayer::class.java)
                .audiencePlaceholder(id) { audience, queue, _ ->
                    val player = audience as ServerPlayer
                    val arguments: MutableList<String> = mutableListOf()
                    while (queue.peek() != null) {
                        arguments.add(queue.pop().toString())
                    }
                    return@audiencePlaceholder Tag.preProcessParsed(placeholder.handle(player, arguments).string)
                }
        }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        placeholder.id().forEach { id ->
            builder.globalPlaceholder(id) { queue, ctx ->
                val arguments: MutableList<String> = mutableListOf()
                while (queue.peek() != null) {
                    arguments.add(queue.pop().toString())
                }
                return@globalPlaceholder Tag.preProcessParsed(placeholder.handle(arguments).string)
            }.audiencePlaceholder(id) { audience, queue, ctx ->
                if (audience !is ServerPlayer) return@audiencePlaceholder Tag.inserting(Component.empty())
                val arguments: MutableList<String> = mutableListOf()
                while (queue.peek() != null) {
                    arguments.add(queue.pop().toString())
                }
                return@audiencePlaceholder Tag.preProcessParsed(placeholder.handle(arguments).string)
            }
        }
    }

    override fun finalizeRegister() {
        builder.build().register()
    }

    override fun parse(input: String, player: ServerPlayer?): String {
        val resolvers = mutableListOf(MiniPlaceholders.getGlobalPlaceholders())
        player?.let { resolvers.add(MiniPlaceholders.getAudiencePlaceholders(it)) }
        val resolver = TagResolver.resolver(*resolvers.toTypedArray())

        return CobblemonPlaceholders.INSTANCE.adventure.toNative(
            miniMessage.deserialize(input, resolver)
        ).string
    }
}
