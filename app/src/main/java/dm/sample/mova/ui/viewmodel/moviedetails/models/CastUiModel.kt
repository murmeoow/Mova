package dm.sample.mova.ui.viewmodel.moviedetails.models

import dm.sample.mova.domain.entities.MovieCast

data class CastUiModel(
    val name: String,
    val jobTitle: String,
    val image: String
)

fun MovieCast.toUi(): List<CastUiModel> {
    val cast = cast.map {
        CastUiModel(
            name = it.name,
            jobTitle = if (it.department == "Acting") "Cast" else it.department,
            image = it.posterImageUrl()
        )
    }
    val director  = crew.find { it.job == "Director" }
    val screenplay = crew.find { it.job == "Writer" || it.job == "Screenplay"}
    val producer = crew.find { it.job == "Producer" }

    val crew = mutableListOf<CastUiModel>()
    director?.let {
        crew.add(CastUiModel(it.name, it.job, it.posterImageUrl()))
    }
    screenplay?.let {
        crew.add(CastUiModel(it.name, it.job, it.posterImageUrl()))
    }
    producer?.let {
        crew.add(CastUiModel(it.name, it.job, it.posterImageUrl()))
    }

    return  crew + cast
}