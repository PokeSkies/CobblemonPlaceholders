package com.pokeskies.cobblemonplaceholders.placeholders.types.misc

import com.bedrockk.molang.runtime.MoLangRuntime
import com.cobblemon.mod.common.api.molang.MoLangFunctions.addFunctions
import com.cobblemon.mod.common.api.molang.MoLangFunctions.asMoLangValue
import com.cobblemon.mod.common.api.molang.MoLangFunctions.setup
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.resolve
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.minecraft.server.level.ServerPlayer

class MolangPlayer : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.misc.invalidMolang)

        val runtime = MoLangRuntime().setup().also {
            it.environment.query.addFunctions(
                mapOf(
                    "player" to java.util.function.Function {
                        return@Function player.asMoLangValue().addFunctions(hashMapOf())
                    }
                )
            )
        }

        val result = runtime.resolve(
            args.asExpressionLike()
        )

        return GenericResult.valid(result.asString())
    }

    override fun id(): String = "molang_player"
}
