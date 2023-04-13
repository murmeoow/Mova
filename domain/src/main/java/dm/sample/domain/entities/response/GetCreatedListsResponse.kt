package dm.sample.mova.domain.entities.response

data class GetCreatedListsResponse(
    val createdLists: List<CreatedList>
)

data class CreatedList(
    val description: String,
    val favouriteCount: Int,
    val id: Long,
    val listType: String,
    val itemCount: Int,
    val name: String,
)