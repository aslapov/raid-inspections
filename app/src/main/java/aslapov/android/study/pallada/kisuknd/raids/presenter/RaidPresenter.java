package aslapov.android.study.pallada.kisuknd.raids.presenter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import java.util.UUID;
import aslapov.android.study.pallada.kisuknd.raids.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.IRaidFragmentView;

public class RaidPresenter {
    private LifecycleOwner mLifecycleOwner;
    private IRaidFragmentView mRaidFragmentView;
    private Raid mRaid;

    public RaidPresenter(@NonNull LifecycleOwner lifecycleOwner,
                         @NonNull IRaidFragmentView raidFragmentView) {
        mLifecycleOwner = lifecycleOwner;
        mRaidFragmentView = raidFragmentView;
    }

    public void init(UUID raidId) {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaidById(raidId, this);
    }

    public void setRaid(Raid raid) {
        mRaid = raid;
    }

    public Raid getRaid() {
        return mRaid;
    }

    public void showRaidInfo(Raid raid) {
        if (raid != null)
            mRaidFragmentView.showRaidInfo(raid);
    }
}
