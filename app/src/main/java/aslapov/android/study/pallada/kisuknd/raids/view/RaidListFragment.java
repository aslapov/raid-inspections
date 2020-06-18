package aslapov.android.study.pallada.kisuknd.raids.view;

import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidListFragment extends BaseRaidListFragment {

	static RaidListFragment newInstance() {
		return new RaidListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		return new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidListViewModel.class);
	}

	@Override
	protected int getListNameId() {
		return R.string.navigation_from_server;
	}
}
