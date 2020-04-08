package aslapov.android.study.pallada.kisuknd.raids.presenter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import java.util.UUID;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.IRaidActivityView;

public class RaidPresenter implements IBasePresenter<IRaidActivityView> {

    private IRaidActivityView mView;
    private Raid mRaid;

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
            mView.showRaidInfo(raid);
    }

    @Override
    public void attachView(IRaidActivityView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
