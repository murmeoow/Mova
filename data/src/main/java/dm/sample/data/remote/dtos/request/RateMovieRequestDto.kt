package dm.sample.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class RateMovieRequestDto(
    @SerializedName("value")
    val value: Float
)
