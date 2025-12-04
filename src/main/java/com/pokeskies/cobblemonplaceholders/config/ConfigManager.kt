package com.pokeskies.cobblemonplaceholders.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.utils.Utils
import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption


object ConfigManager {
    lateinit var CONFIG: MainConfig
    var gson: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    fun init() {
        reload()
    }

    fun reload() {
        copyDefaults()
        CONFIG = loadFile("config.json", MainConfig::class.java)!!
    }

    fun copyDefaults() {
        val classLoader = CobblemonPlaceholders::class.java.classLoader

        CobblemonPlaceholders.INSTANCE.configDir.mkdirs()

        // Main Config
        val configFile = CobblemonPlaceholders.INSTANCE.configDir.resolve("config.json")
        if (!configFile.exists()) {
            try {
                val inputStream: InputStream = classLoader.getResourceAsStream("assets/cobblemonplaceholders/config.json")
                Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: Exception) {
                Utils.printError("Failed to copy the default config file: $e - ${e.message}")
            }
        }
    }

    fun <T : Any> loadFile(filename: String, classObject: Class<T>): T? {
        val file = File(CobblemonPlaceholders.INSTANCE.configDir, filename)
        var value: T? = null
        try {
            Files.createDirectories(CobblemonPlaceholders.INSTANCE.configDir.toPath())
            try {
                FileReader(file).use { reader ->
                    val jsonReader = JsonReader(reader)
                    value = gson.fromJson(jsonReader, classObject)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return value
    }

    fun <T> saveFile(filename: String, `object`: T) {
        val file = File(CobblemonPlaceholders.INSTANCE.configDir, filename)
        try {
            FileWriter(file).use { fileWriter ->
                fileWriter.write(gson.toJson(`object`))
                fileWriter.flush()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}