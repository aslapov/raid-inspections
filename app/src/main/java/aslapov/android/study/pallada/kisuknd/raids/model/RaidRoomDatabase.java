package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidInspectionMember;

@Database(entities = {Raid.class, RaidInspectionMember.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RaidRoomDatabase extends RoomDatabase {

    public abstract RaidDao raidDao();

    private static volatile RaidRoomDatabase INSTANCE;

    static RaidRoomDatabase getDatabase(final Context context) {
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
