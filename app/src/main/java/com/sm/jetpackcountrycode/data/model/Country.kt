package com.sm.jetpackcountrycode.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive


@Serializable
data class Country(
    val name: String,
    val code: String,
    val emoji: String,
    val unicode: String,
    val image: String,
    @SerialName("dial_code") val dialCode: String,
    @Serializable(with = PhoneLengthSerializer::class)
    val phoneLength: List<Int>? = null
)


object PhoneLengthSerializer : KSerializer<List<Int>?> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PhoneLength")

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: List<Int>?) {
        when {
            value == null -> encoder.encodeNull()
            value.size == 1 -> encoder.encodeInt(value.first())
            else -> encoder.encodeSerializableValue(ListSerializer(Int.serializer()), value)
        }
    }

    override fun deserialize(decoder: Decoder): List<Int>? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("PhoneLengthSerializer requires a JsonDecoder")
        val element = jsonDecoder.decodeJsonElement()

        return when {
            element is JsonArray -> element.jsonArray.map { it.jsonPrimitive.int }
            element.jsonPrimitive.intOrNull != null -> listOf(element.jsonPrimitive.int)
            else -> null
        }
    }
}