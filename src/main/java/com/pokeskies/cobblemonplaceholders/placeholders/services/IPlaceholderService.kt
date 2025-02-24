package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder

interface IPlaceholderService {
    fun registerPlayer(placeholder: PlayerPlaceholder)
    fun registerServer(placeholder: ServerPlaceholder)
    fun finalizeRegister()
}
