package com.pokeskies.cobblemonplaceholders.utils

import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minecraft.text.Text
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern


object Utils {
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()
    private val decimalFormatter: DecimalFormat = DecimalFormat()

    init {
        decimalFormatter.minimumFractionDigits = 0
        decimalFormatter.maximumFractionDigits = 0
    }

    fun deserializeText(text: String): Text {
        return CobblemonPlaceholders.INSTANCE.adventure!!.toNative(miniMessage.deserialize(text))
    }

    fun printError(message: String) {
        CobblemonPlaceholders.LOGGER.error("[CobblemonPlaceholders] ERROR: $message")
    }

    fun printInfo(message: String) {
        CobblemonPlaceholders.LOGGER.info("[CobblemonPlaceholders] $message")
    }

    fun titleCase(input: String): String {
        val pattern = Pattern.compile("\\b([a-zÀ-ÖØ-öø-ÿ])([\\w]*)")
        val matcher = pattern.matcher(input.lowercase(Locale.getDefault()))
        val buffer = StringBuilder()
        while (matcher.find()) matcher.appendReplacement(
            buffer, matcher.group(1).uppercase(Locale.getDefault()) + matcher.group(2)
        )
        return matcher.appendTail(buffer).toString()
    }

    fun parseDouble(double: Double): String {
        return decimalFormatter.format(double)
    }
}