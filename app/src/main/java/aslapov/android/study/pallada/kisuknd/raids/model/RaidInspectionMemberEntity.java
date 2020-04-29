package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "RaidInspectionMember", primaryKeys = {"RaidInspectionId", "ContactName"},
    foreignKeys = @ForeignKey(
        entity = RaidEntity.class,
        parentColumns = "Id",
        childColumns = "RaidInspectionId",
        onDelete = CASCADE)
    )
public class RaidInspectionMemberEntity {

    @ColumnInfo(name = "RaidInspectionId")
    @NonNull
    private String raidInspectionId;

    @ColumnInfo(name = "ContactName")
    @NonNull
    private String contactName;

    @NonNull
    public String getRaidInspectionId() {
        return raidInspectionId;
    }

    public void setRaidInspectionId(@NonNull String raidInspectionId) {
        this.raidInspectionId = raidInspectionId;
    }

    @NonNull
    public String getContactName() {
        return contactName;
    }

    public void setContactName(@NonNull String contactName) {
        this.contactName = contactName;
    }
}
