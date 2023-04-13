package dm.sample.data.repositories

import dm.sample.data.utils.CountryUtils
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.domain.repositories.CountriesRepository
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor() : CountriesRepository {

    override fun getCountriesList(): List<Country> = CountryUtils.getCountriesList()
}