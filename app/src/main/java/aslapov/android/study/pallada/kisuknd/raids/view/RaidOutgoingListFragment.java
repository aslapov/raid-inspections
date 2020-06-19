package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidOutgoingListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidOutgoingListFragment extends BaseRaidListFragment {

	private RaidOutgoingListViewModel mViewModel;

	static RaidOutgoingListFragment newInstance() {
		return new RaidOutgoingListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidOutgoingListViewModel.class);

		return mViewModel;
	}

	@Override
	protected int getListNameId() {
		return R.string.navigation_outgoing;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_outgoing, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.cancel_send) {
			mViewModel.cancelSending();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
