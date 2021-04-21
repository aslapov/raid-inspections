package aslapov.android.study.pallada.kisuknd.raids.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Raid.class, RaidInspectionMember.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RaidRoomDatabase extends RoomDatabase {

    public abstract RaidDao raidDao();

    private static volatile RaidRoomDatabase INSTANCE;

    public static RaidRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RaidRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RaidRoomDatabase.class, "raid_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
