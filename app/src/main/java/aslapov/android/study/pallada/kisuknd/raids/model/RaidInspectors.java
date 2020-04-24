package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Класс для связи Room-сущностей RaidEntity и RaidInspectionMemberEntity
// Связь один ко многим
public class RaidInspectors {

    @Embedded
    public RaidEntity raid;

    @Relation(
            parentColumn = "Id",
            entityColumn = "RaidInspectionId"
    )
    public List<RaidInspectionMemberEntity> inspectors;
}
