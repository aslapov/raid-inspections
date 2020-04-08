package aslapov.android.study.pallada.kisuknd.raids.presenter;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raidsList.IRaidListView;

public class RaidListPresenter implements IBasePresenter<IRaidListView> {

    private IRaidListView mView;

    private List<Raid> mRaids = null;

    public List<Raid> getRaids() {
        return mRaids;
    }
    public void setRaids(List<Raid> raids) {
        mRaids = raids;
    }

    @Override
    public void attachView(IRaidListView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void loadRaids() {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaids(this);
    }

    public void showRaids() {
        if (mView != null)
            mView.showRaids(getRaids());
    }

    public void showRaidInfo(Raid raid) {
        if (mView != null)
            mView.showRaidInfo(raid);
    }
}
