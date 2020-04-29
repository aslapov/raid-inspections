package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Класс для связи Room-сущностей RaidEntity и RaidInspectionMemberEntity
// Связь один ко многим
public class RaidWithInspectors {

    @Embedded
    private RaidEntity raidEntity;

    @Relation(
            parentColumn = "Id",
            entityColumn = "RaidInspectionId"
    )
    private List<RaidInspectionMemberEntity> inspectors;

    public RaidEntity getRaidEntity() {
        return raidEntity;
    }

    public void setRaidEntity(RaidEntity raid) {
        this.raidEntity = raid;
    }

    public List<RaidInspectionMemberEntity> getInspectors() {
        return inspectors;
    }

    public void setInspectors(List<RaidInspectionMemberEntity> inspectors) {
        this.inspectors = inspectors;
    }
}
