package com.pokeskies.cobblemonplaceholders.commands.subcommands

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.utils.SubCommand
import com.pokeskies.cobblemonplaceholders.utils.Utils
import me.lucko.fabric.api.permissions.v0.Permissions
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

class ReloadCommand : SubCommand {
    override fun build(): LiteralCommandNode<ServerCommandSource> {
        return CommandManager.literal("reload")
            .requires(Permissions.require("cobblemonplaceholders.command.reload", 4))
            .executes(Companion::reload)
            .build()
    }

    companion object {
        fun reload(ctx: CommandContext<ServerCommandSource>): Int {
            CobblemonPlaceholders.INSTANCE.reload()
            ctx.source.sendMessage(Utils.deserializeText("<green>Reloaded CobblemonPlaceholders"))
            return 1
        }
    }
}