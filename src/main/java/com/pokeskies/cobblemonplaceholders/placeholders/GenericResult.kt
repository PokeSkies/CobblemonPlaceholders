package com.pokeskies.cobblemonplaceholders.placeholders

import net.kyori.adventure.text.Component

class GenericResult private constructor(
    val result: Component,
    val isSuccessful: Boolean = true
) {
    companion object {
        fun valid(result: Component): GenericResult {
            return GenericResult(result, true)
        }

        fun invalid(result: Component): GenericResult {
            return GenericResult(result, false)
        }
    }
}
