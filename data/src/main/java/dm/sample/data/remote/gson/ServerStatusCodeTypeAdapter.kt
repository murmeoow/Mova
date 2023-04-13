package dm.sample.data.remote.gson

import dm.sample.mova.domain.base.ServerStatusCode
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class ServerStatusCodeTypeAdapter : TypeAdapter<ServerStatusCode>() {

    override fun write(out: JsonWriter?, value: ServerStatusCode?) {
        out ?: return
        value ?: return
        out.value(value.code)
    }

    override fun read(reader: JsonReader?): ServerStatusCode {
        reader ?: return ServerStatusCode.UNKNOWN_ERROR
        if(reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ServerStatusCode.UNKNOWN_ERROR
        }
        return ServerStatusCode.find(reader.nextInt())
    }

}