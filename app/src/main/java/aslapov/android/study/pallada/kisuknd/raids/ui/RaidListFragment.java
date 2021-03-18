package aslapov.android.study.pallada.kisuknd.raids.ui;

import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidListFragment extends BaseRaidListFragment {

	private RaidListViewModel mViewModel;

	static RaidListFragment newInstance() {
		return new RaidListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidListViewModel.class);

		return mViewModel;
	}
}
