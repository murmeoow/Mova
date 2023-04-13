package dm.sample.data.remote.gson

import dm.sample.data.remote.dtos.MovieAccountStatesDto
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class AccountStateAdapter : TypeAdapter<MovieAccountStatesDto?>() {

    override fun write(out: JsonWriter?, value: MovieAccountStatesDto?) {
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
        out.value(Gson().toJson(value))
    }

    override fun read(reader: JsonReader?): MovieAccountStatesDto? {
        var id: Int? = null
        var favorite: Boolean? = null
        var rating: Float? = null
        var rated = false
        var watchlist: Boolean? = null

        reader ?: return null
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when {
                name.equals("id") -> {
                    id = reader.nextInt()
                }
                name.equals("favorite") -> {
                    favorite = reader.nextBoolean()
                }
                name.equals("watchlist") -> {
                    watchlist = reader.nextBoolean()
                }
                name.equals("rated") -> {
                    try {
                        rated = reader.nextBoolean()
                    } catch (e: Exception) {
                        reader.beginObject()
                        val name2 = reader.nextName()
                        if (name2.equals("value")) {
                            rating = reader.nextDouble().toFloat()
                            rated = true
                        }
                        reader.endObject()
                    }
                }
            }
        }

        reader.endObject()

        return MovieAccountStatesDto(id = id,
            rating = rating,
            rated = rated,
            favorite = favorite,
            watchlist = watchlist,
        )
    }
}