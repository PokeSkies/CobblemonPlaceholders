package com.pokeskies.cobblemonplaceholders.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import com.pokeskies.cobblemonplaceholders.commands.subcommands.ReloadCommand
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

class BaseCommands {
    private val aliases = listOf("cobblemonplaceholders")

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val rootCommands: List<LiteralCommandNode<CommandSourceStack>> = aliases.map {
            Commands.literal(it).build()
        }

        val subCommands: List<LiteralCommandNode<CommandSourceStack>> = listOf(
            ReloadCommand().build(),
        )

        rootCommands.forEach { root ->
            subCommands.forEach { sub -> root.addChild(sub) }
            dispatcher.root.addChild(root)
        }
    }
}
