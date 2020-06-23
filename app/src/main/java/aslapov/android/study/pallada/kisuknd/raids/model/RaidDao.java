package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidInspectionMember;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import io.reactivex.Observable;

@Dao
public abstract class RaidDao {

    @Transaction
    @Query("SELECT * FROM RaidInspections WHERE Status = :status")
    public abstract Observable<List<RaidWithInspectors>> queryRaidList(@NonNull Integer status);

    @Transaction
    @Query("SELECT * FROM RaidInspections WHERE Id = :raidId")
    public abstract Observable<RaidWithInspectors> queryRaidById(String raidId);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertRaid(Raid raid);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertRaidInspector(RaidInspectionMember member);

    @Transaction
    public void insertRaidWithInspectors(RaidWithInspectors raidInspection) {
        insertRaid(raidInspection.getRaid());
        for (RaidInspectionMember member : raidInspection.getInspectors()) {
            insertRaidInspector(member);
        }
    }

    @Transaction
    @Update
    protected abstract void updateRaid(Raid raid);

    @Transaction
    @Update
    protected abstract void updateRaidInspector(RaidInspectionMember member);

    @Transaction
    public void updateRaidWithInspectors(RaidWithInspectors raidInspection) {
        updateRaid(raidInspection.getRaid());
        for (RaidInspectionMember member : raidInspection.getInspectors()) {
            updateRaidInspector(member);
        }
    }

    @Transaction
    public void updateRaids(List<RaidWithInspectors> raidInspections) {
        for (RaidWithInspectors raidInspection : raidInspections) {
            updateRaid(raidInspection.getRaid());
            for (RaidInspectionMember member : raidInspection.getInspectors()) {
                updateRaidInspector(member);
            }
        }
    }

    @Transaction
    @Delete
    protected abstract void deleteRaid(Raid raid);

    @Transaction
    @Delete
    protected abstract void deleteRaidInspector(RaidInspectionMember member);

    @Transaction
    public void deleteRaid(RaidWithInspectors raidInspection) {
        deleteRaid(raidInspection.getRaid());
        for (RaidInspectionMember member : raidInspection.getInspectors()) {
            deleteRaidInspector(member);
        }
    }

    @Transaction
    public void deleteRaids(List<RaidWithInspectors> raidInspections) {
        for (RaidWithInspectors raidInspection : raidInspections) {
            deleteRaid(raidInspection.getRaid());
            for (RaidInspectionMember member : raidInspection.getInspectors()) {
                deleteRaidInspector(member);
            }
        }
    }
}
