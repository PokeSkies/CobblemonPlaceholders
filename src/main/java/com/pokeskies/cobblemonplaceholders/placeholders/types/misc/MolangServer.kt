package com.pokeskies.cobblemonplaceholders.placeholders.types.misc

import com.bedrockk.molang.runtime.MoLangRuntime
import com.cobblemon.mod.common.api.molang.MoLangFunctions.setup
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.resolve
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.kyori.adventure.text.Component

class MolangServer : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.misc.invalidMolang
            ))

        val runtime = MoLangRuntime().setup()

        val result = runtime.resolve(
            args.asExpressionLike()
        )

        return GenericResult.valid(Component.text(result.asString()))
    }

    override fun id(): String = "molang_server"
}
