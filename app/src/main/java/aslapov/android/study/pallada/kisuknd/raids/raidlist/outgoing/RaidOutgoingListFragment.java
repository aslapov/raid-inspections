package aslapov.android.study.pallada.kisuknd.raids.raidlist.outgoing;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseRaidListFragment;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.ViewModelFactory;

public class RaidOutgoingListFragment extends BaseRaidListFragment {

	private RaidOutgoingListViewModel mViewModel;

	public static RaidOutgoingListFragment newInstance() {
		return new RaidOutgoingListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidOutgoingListViewModel.class);

		return mViewModel;
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
