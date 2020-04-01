package aslapov.android.study.pallada.kisuknd.raids.presenter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.raidsList.IRaidListView;
import aslapov.android.study.pallada.kisuknd.raids.view.raidsList.IRaidsFragmentView;

public class RaidListPresenter {

    private LifecycleOwner mLifecycleOwner;
    private IRaidsFragmentView mRaidsFragmentView;

    private List<Raid> mRaids = null;

    public RaidListPresenter(@NonNull LifecycleOwner lifecycleOwner,
                         @NonNull IRaidsFragmentView raidsFragmentView) {
        mLifecycleOwner = lifecycleOwner;
        mRaidsFragmentView = raidsFragmentView;
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
        if (mRaidsFragmentView != null)
            mRaidsFragmentView.showRaids(getRaids());
    }
}
