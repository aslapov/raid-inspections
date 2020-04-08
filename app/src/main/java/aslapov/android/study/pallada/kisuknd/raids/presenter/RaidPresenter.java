package aslapov.android.study.pallada.kisuknd.raids.presenter;

import java.util.UUID;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raid.IRaidView;

public class RaidPresenter implements IBasePresenter<IRaidView> {

    private IRaidView mView;

    private Raid mRaid;

    public void setRaid(Raid raid) {
        mRaid = raid;
    }

    public Raid getRaid() {
        return mRaid;
    }

    @Override
    public void attachView(IRaidView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadRaid(UUID raidId) {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaidById(raidId, this);
    }

    public void showRaidInfo(Raid raid) {
        if (raid != null)
            mView.showRaidInfo(raid);
    }
}
