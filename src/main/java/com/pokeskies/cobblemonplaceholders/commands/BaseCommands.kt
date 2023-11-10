package com.pokeskies.cobblemonplaceholders.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import com.pokeskies.cobblemonplaceholders.commands.subcommands.ReloadCommand
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

class BaseCommands {
    private val aliases = listOf("cobblemonplaceholders")

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        val rootCommands: List<LiteralCommandNode<ServerCommandSource>> = aliases.map {
            CommandManager.literal(it).build()
        }

        val subCommands: List<LiteralCommandNode<ServerCommandSource>> = listOf(
            ReloadCommand().build(),
        )

        rootCommands.forEach { root ->
            subCommands.forEach { sub -> root.addChild(sub) }
            dispatcher.root.addChild(root)
        }
    }
}