package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "RaidInspectionMember")
public class RaidInspectionMemberEntity {

    @PrimaryKey
    @ColumnInfo(name = "RaidInspectionId")
    public UUID raidInspectionId;

    @PrimaryKey
    @ColumnInfo(name = "ContactName")
    public String contactName;
}
