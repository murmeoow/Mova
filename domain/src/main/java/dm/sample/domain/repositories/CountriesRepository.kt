package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.Country

interface CountriesRepository {
    fun getCountriesList(): List<Country>
}