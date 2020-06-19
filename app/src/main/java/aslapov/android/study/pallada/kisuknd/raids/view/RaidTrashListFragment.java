package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidTrashListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidTrashListFragment extends BaseRaidListFragment {

	private RaidTrashListViewModel mViewModel;

	static RaidTrashListFragment newInstance() {
		return new RaidTrashListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidTrashListViewModel.class);

		return mViewModel;
	}

	@Override
	protected int getListNameId() { return R.string.navigation_trash; }

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_trash, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.restore_all:
				mViewModel.restoreDeletedRaids();
				return true;
			case R.id.menu_delete:
				mViewModel.emptyTrash();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
