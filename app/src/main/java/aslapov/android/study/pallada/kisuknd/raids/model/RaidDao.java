package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class RaidDao {

    /*@Transaction
    @Query("SELECT * FROM RaidInspections")
    public abstract LiveData<List<RaidWithInspectors>> queryRaidList();*/

    @Transaction
    @Query("SELECT * FROM RaidInspections")
    public abstract List<RaidWithInspectors> queryRaidList();

    @Transaction
    @Query("SELECT * FROM RaidInspections WHERE Id = :raidId")
    public abstract LiveData<RaidWithInspectors> queryRaidListById(String raidId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertRaid(RaidEntity raid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertRaidInspector(RaidInspectionMemberEntity member);

    @Transaction
    public void insertRaidWithInspectors(RaidEntity raid, List<RaidInspectionMemberEntity> inspectors) {
        insertRaid(raid);
        for (RaidInspectionMemberEntity member : inspectors) {
            insertRaidInspector(member);
        }
    }
}
