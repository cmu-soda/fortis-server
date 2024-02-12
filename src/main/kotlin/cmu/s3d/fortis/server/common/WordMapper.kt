package cmu.s3d.fortis.server.common

import cmu.s3d.fortis.common.SerializableWord
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import net.automatalib.word.Word
import org.springframework.boot.jackson.JsonComponent

@JsonComponent
class WordSerializer : JsonSerializer<Word<String>>() {
    override fun serialize(value: Word<String>, gen: JsonGenerator, serializerProvider: SerializerProvider) {
        gen.writeArray(value.asList().toTypedArray(), 0, value.length())
    }

}

@JsonComponent
class WordDeserializer : JsonDeserializer<Word<String>>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Word<String> {
        val node = parser.codec.readTree<JsonNode>(parser)
        return if (node.isArray) {
            SerializableWord(node.map { it.asText() }.toTypedArray())
        } else {
            SerializableWord(emptyArray())
        }
    }
}