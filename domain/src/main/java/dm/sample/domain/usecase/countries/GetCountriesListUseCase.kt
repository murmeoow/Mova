package dm.sample.mova.domain.usecase.countries

import dm.sample.mova.domain.repositories.CountriesRepository
import javax.inject.Inject

class GetCountriesListUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {
    operator fun invoke() = countriesRepository.getCountriesList()
}
