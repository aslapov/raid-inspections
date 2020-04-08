package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;

public interface IRaidListActivityView {

    void showRaids(List<Raid> raids);
    void showRaidInfo(Raid raid);
}
