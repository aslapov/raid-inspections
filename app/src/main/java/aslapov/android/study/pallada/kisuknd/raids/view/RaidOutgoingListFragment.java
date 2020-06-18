package aslapov.android.study.pallada.kisuknd.raids.view;

import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidOutgoingListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidOutgoingListFragment extends BaseRaidListFragment {

	static RaidOutgoingListFragment newInstance() {
		return new RaidOutgoingListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		return new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidOutgoingListViewModel.class);
	}

	@Override
	protected int getListNameId() {
		return R.string.navigation_outgoing;
	}
}
