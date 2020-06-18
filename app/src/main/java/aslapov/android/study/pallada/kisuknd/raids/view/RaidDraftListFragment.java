package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidDraftListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidDraftListFragment extends BaseRaidListFragment {

	static RaidDraftListFragment newInstance() {
		return new RaidDraftListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		return new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidDraftListViewModel.class);
	}

	@Override
	protected int getListNameId() {
		return R.string.navigation_drafts;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.send_drafts, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.send_drafts) {
			sendDrafts();
			return true;
		} else
			return super.onOptionsItemSelected(item);
	}

	private void sendDrafts() {
		// TODO
	}
}
