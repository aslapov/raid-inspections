package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.room.Dao;
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
    @Query("SELECT * FROM RaidInspections WHERE IsDraft = :isDraft")
    public abstract Observable<List<RaidWithInspectors>> queryRaidList(boolean isDraft);

    @Transaction
    @Query("SELECT * FROM RaidInspections WHERE Id = :raidId")
    public abstract Observable<RaidWithInspectors> queryRaidById(String raidId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertRaid(Raid raid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertRaidInspector(RaidInspectionMember member);

    @Transaction
    public void insertRaidWithInspectors(Raid raid, List<RaidInspectionMember> inspectors) {
        insertRaid(raid);
        for (RaidInspectionMember member : inspectors) {
            insertRaidInspector(member);
        }
    }

    @Update
    public abstract void updateRaid(Raid raid);

    @Update
    public abstract void updateRaidInspector(RaidInspectionMember member);

    @Transaction
    public void updateRaidWithInspectors(Raid raid, List<RaidInspectionMember> inspectors) {
        updateRaid(raid);
        for (RaidInspectionMember member : inspectors) {
            updateRaidInspector(member);
        }
    };
}
