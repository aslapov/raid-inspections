package aslapov.android.study.pallada.kisuknd.raids.presenter;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raidsList.IRaidListActivityView;

public class RaidListPresenter implements IBasePresenter<IRaidListActivityView> {

    private IRaidListActivityView mView;

    private List<Raid> mRaids = null;

    @Override
    public void attachView(IRaidListActivityView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void init() {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaids(this);
    }

    public void setRaids(List<Raid> raids) {
        mRaids = raids;
    }

    public List<Raid> getRaids() {
        return mRaids;
    }

    public void showRaids() {
        if (mView != null)
            mView.showRaids(getRaids());
    }

    public Raid getRaidById(UUID raidId) {
        if (mRaids != null) {
            for (Raid raid : mRaids) {
                if (raid.getId() == raidId)
                    return raid;
            }
        }
        return null;
    }
}
