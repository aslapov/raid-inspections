package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
				emptyTrashClick();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void emptyTrashClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_MaterialComponents_Light_Dialog);
		builder.setTitle(R.string.dialog_empty_title);

		String message = getString(R.string.dialog_empty_message)
				.concat(": ")
				.concat(String.valueOf(mViewModel.getRaidList().size()));

		builder.setMessage(message);
		builder.setPositiveButton(R.string.empty, (dialog, which) -> mViewModel.emptyTrash());
		builder.setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
