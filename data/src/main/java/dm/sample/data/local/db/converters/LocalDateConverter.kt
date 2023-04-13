package dm.sample.data.local.db.converters

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return if (dateString == null) {
            null
        } else LocalDate.parse(dateString)
    }

    @TypeConverter
    fun toTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}