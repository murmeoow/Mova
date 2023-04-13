package dm.sample.data.remote.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : TypeAdapter<LocalDate?>() {

    override fun write(out: JsonWriter?, value: LocalDate?) {
        out ?: return
        value ?: run {
            if (out.serializeNulls.not()) {
                out.serializeNulls = true
                out.nullValue()
                out.serializeNulls = false
            } else {
                out.nullValue()
            }
            return
        }
        out.value(DateTimeFormatter.ISO_LOCAL_DATE.format(value))
    }

    override fun read(reader: JsonReader?): LocalDate? {
        reader ?: return null
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        val dateString = reader.nextString()
        return if (dateString.isNullOrBlank()) null
        else LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
