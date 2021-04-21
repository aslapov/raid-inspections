package aslapov.android.study.pallada.kisuknd.raids.data.source.local;

import androidx.room.TypeConverter;

import java.util.Date;

class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
