package aslapov.android.study.pallada.kisuknd.raids.view.raid;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.content.Raid;

public interface IRaidFragmentView {

    void showRaidInfo(Raid raid);

    UUID getShownIndex();
}
