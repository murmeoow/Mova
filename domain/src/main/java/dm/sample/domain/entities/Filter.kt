package dm.sample.mova.domain.entities

data class Filter(
    val value: String,
    var text: String,
    val isDefaultSelected: Boolean,
    val filterType: FilterType,
)

data class FilterCategory(
    val text: String,
    val filters: List<Filter>,
)

enum class FilterType(val isSingleSelection: Boolean) {
    GENRE(isSingleSelection = false),
    TIME(isSingleSelection = true),
    SORT(isSingleSelection = true)
}