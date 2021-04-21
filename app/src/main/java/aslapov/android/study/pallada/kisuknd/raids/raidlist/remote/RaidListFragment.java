package aslapov.android.study.pallada.kisuknd.raids.raidlist.remote;

import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseRaidListFragment;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.ViewModelFactory;

public class RaidListFragment extends BaseRaidListFragment {

	private RaidListViewModel mViewModel;

	public static RaidListFragment newInstance() {
		return new RaidListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidListViewModel.class);

		return mViewModel;
	}
}
