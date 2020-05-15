package aslapov.android.study.pallada.kisuknd.raids.model.local;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Класс для связи Room-сущностей Raid и RaidInspectionMember
// Связь один ко многим
public class RaidWithInspectors {

    @Embedded
    private Raid raid;

    @Relation(
            parentColumn = "Id",
            entityColumn = "RaidInspectionId"
    )
    private List<RaidInspectionMember> inspectors;

    public Raid getRaid() {
        return raid;
    }

    public void setRaid(Raid raid) {
        this.raid = raid;
    }

    public List<RaidInspectionMember> getInspectors() {
        return inspectors;
    }

    public void setInspectors(List<RaidInspectionMember> inspectors) {
        this.inspectors = inspectors;
    }
}
