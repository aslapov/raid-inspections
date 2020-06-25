package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidDraftListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidDraftListFragment extends BaseRaidListFragment {

	private RaidDraftListViewModel mViewModel;

	static RaidDraftListFragment newInstance() {
		return new RaidDraftListFragment();
	}

	@Override
	protected BaseListViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidDraftListViewModel.class);

		return mViewModel;
	}

	@Override
	protected int getListNameId() {
		return R.string.navigation_drafts;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_drafts, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.send_drafts:
				mViewModel.sendAll();
				return true;
			case R.id.delete_drafts:
				moveAllToTrashClick();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void moveAllToTrashClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_MaterialComponents_Light_Dialog);
		builder.setTitle(R.string.dialog_move_all_title);
		builder.setMessage(R.string.dialog_move_all_message);
		builder.setPositiveButton(R.string.trash, (dialog, which) -> mViewModel.moveAllToTrash());
		builder.setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
