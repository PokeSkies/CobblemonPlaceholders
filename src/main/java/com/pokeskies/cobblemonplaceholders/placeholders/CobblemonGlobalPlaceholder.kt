package com.pokeskies.cobblemonplaceholders.placeholders

import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import java.util.function.BiFunction

interface CobblemonGlobalPlaceholder : BiFunction<ArgumentQueue, Context, Tag?> {
    fun register(builder: Expansion.Builder)
}