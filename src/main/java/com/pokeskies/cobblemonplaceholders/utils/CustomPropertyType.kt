package com.pokeskies.cobblemonplaceholders.utils

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import com.cobblemon.mod.common.pokemon.properties.BooleanProperty
import com.cobblemon.mod.common.pokemon.properties.FlagProperty
import com.cobblemon.mod.common.pokemon.properties.FloatProperty
import com.cobblemon.mod.common.pokemon.properties.IntProperty
import com.cobblemon.mod.common.pokemon.properties.StringProperty

enum class CustomPropertyType(
    private val clazz: Class<*>,
    private val idSupplier: (Any) -> String,
    private val valueSupplier: (Any) -> String
) {
    BOOLEAN(
        BooleanProperty::class.java,
        { clazz -> (clazz as BooleanProperty).key },
        { clazz -> (clazz as BooleanProperty).value.toString() }
    ),
    INT(
        IntProperty::class.java,
        { clazz -> (clazz as IntProperty).key },
        { clazz -> (clazz as IntProperty).value.toString() }
    ),
    FLOAT(
        FloatProperty::class.java,
        { clazz -> (clazz as FloatProperty).key },
        { clazz -> (clazz as FloatProperty).value.toString() }
    ),
    STRING(
        StringProperty::class.java,
        { clazz -> (clazz as StringProperty).key },
        { clazz -> (clazz as StringProperty).value }
    ),
    FLAG(
        FlagProperty::class.java,
        { clazz -> (clazz as FlagProperty).key },
        { clazz -> (clazz as FlagProperty).key }
    );

    fun id(property: CustomPokemonProperty): String = idSupplier.invoke(property)
    fun value(property: CustomPokemonProperty): String = valueSupplier.invoke(property)

    companion object {
        fun getPropertyType(property: CustomPokemonProperty): CustomPropertyType? {
            for (type in entries) {
                if (type.clazz.isInstance(property)) {
                    return type
                }
            }

            return null
        }
    }
}