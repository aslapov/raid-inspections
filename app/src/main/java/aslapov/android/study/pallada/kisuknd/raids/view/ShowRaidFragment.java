package aslapov.android.study.pallada.kisuknd.raids.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ShowRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class ShowRaidFragment extends Fragment {
	private static final String ARG_RAID_ID = "raid_id";

	private ShowRaidViewModel mViewModel;

	private ProgressBar mLoading;

	private MaterialTextView mTimeTextView;
	private MaterialTextView mLocationTextView;
	private MaterialTextView mDocsTextView;
	private MaterialTextView mViolationTextView;
	private MaterialTextView mVehicleTextView;
	private FloatingActionButton mSendDraftButton;

	private Locale mLocaleRu = new Locale("ru");
	private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu);
	private DateFormat mTimeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, mLocaleRu);

	static ShowRaidFragment newInstance(UUID raidId) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_RAID_ID, raidId);

		ShowRaidFragment fragment = new ShowRaidFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public UUID getRaidId() {
		return (UUID) getArguments().getSerializable(ARG_RAID_ID);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.show_raid_layout, container, false);
		setHasOptionsMenu(true);

		mLoading = v.findViewById(R.id.loading);
		mLoading.setVisibility(View.VISIBLE);

		mTimeTextView = (MaterialTextView) v.findViewById(R.id.raid_info_time);
		mLocationTextView = (MaterialTextView) v.findViewById(R.id.raid_info_location);
		mDocsTextView = (MaterialTextView) v.findViewById(R.id.raid_info_doc);
		mViolationTextView = (MaterialTextView) v.findViewById(R.id.raid_info_violation);
		mVehicleTextView = (MaterialTextView) v.findViewById(R.id.raid_info_vehicle_info);
		mSendDraftButton = (FloatingActionButton) v.findViewById(R.id.float_send_draft);

		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(ShowRaidViewModel.class);
		mViewModel.getViewModelObserver().observe(getViewLifecycleOwner(), this::updateUI);
		mViewModel.requestRaid(getRaidId());
	}

	private void updateUI(ShowRaidViewModel viewModel) {
		mLoading.setVisibility(View.GONE);

		getActivity().invalidateOptionsMenu();

		if (viewModel.isDraft())
			mSendDraftButton.setVisibility(View.VISIBLE);
		else
			mSendDraftButton.setVisibility(View.GONE);

		RaidWithInspectors raidInspection = viewModel.getRaid();
		if (raidInspection != null) {
			showRaidInfo(raidInspection);
			mSendDraftButton.setOnClickListener(v -> {
				viewModel.sendRaidDraft(raidInspection);
				if (getActivity() instanceof ShowRaidActivity)
					getActivity().finish();
			});
		} else {
			// TODO show error notification
		}
	}

	@Override
	public void onPrepareOptionsMenu(@NonNull Menu menu) {
		if (mViewModel.isRaidReceived()) {
			MenuInflater inflater = getActivity().getMenuInflater();

			switch (mViewModel.getRaidStatus()) {
				case DRAFT:
					if (menu.findItem(R.id.menu_move_to_trash) == null) {
						inflater.inflate(R.menu.move_raid_to_trash_item, menu);
						inflater.inflate(R.menu.edit_raid_item, menu);
					}
					break;
				case OUTGOING:
					if (menu.findItem(R.id.menu_cancel_raid) == null)
						inflater.inflate(R.menu.cancel_raid_item, menu);
					break;
				case TRASH:
					if (menu.findItem(R.id.menu_restore_raid) == null)
						inflater.inflate(R.menu.restore_raid_item, menu);
					break;
				default:
					super.onPrepareOptionsMenu(menu);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_edit:
				EditRaidActivity.start(getActivity(), getRaidId()); //TODO startForResult EditRaidActivity
				return true;
			case R.id.menu_move_to_trash:
				moveToTrashClick();
				if (getActivity() instanceof ShowRaidActivity)
					getActivity().finish();
				return true;
			case R.id.menu_cancel_raid:
				mViewModel.cancelSending();
				if (getActivity() instanceof ShowRaidActivity)
					getActivity().finish();
				return true;
			case R.id.menu_restore_raid:
				mViewModel.restore();
				if (getActivity() instanceof ShowRaidActivity)
					getActivity().finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void showRaidInfo(@NotNull RaidWithInspectors raidInspection) {
		Raid raid = raidInspection.getRaid();

		String startDate = mDateFormatter.format(raid.getRealStart());
		String startTime = mTimeFormatter.format(raid.getRealStart());
		String endDate = mDateFormatter.format(raid.getRealEnd());
		String endTime = mTimeFormatter.format(raid.getRealEnd());
		if (startDate.compareTo(endDate) == 0) {
			mTimeTextView.setText(startDate
					.concat(" ")
					.concat(startTime)
					.concat(" - ")
					.concat(endTime));
		} else {
			mTimeTextView.setText(startDate
					.concat(" ")
					.concat(startTime)
					.concat(" - ")
					.concat(endDate)
					.concat(" ")
					.concat(endTime));
		}

		mLocationTextView.setText(raid.getPlaceAddress());

		String docs = String.format(mLocaleRu,
				"Акт: %s,   %s\n\nЗадание: %s,   %s\n\nРаспоряжение: %s,   %s\n\n%d предостережений,   %s",
				raid.getActNumber(), mDateFormatter.format(raid.getActDate()),
				raid.getTaskNumber(), mDateFormatter.format(raid.getTaskDate()),
				raid.getOrderNumber(), mDateFormatter.format(raid.getOrderDate()),
				raid.getWarningCount(), mDateFormatter.format(raid.getWarningDate()));
		mDocsTextView.setText(docs);

		if (raid.isViolationsPresence()) {
			mViolationTextView.setText(R.string.violation_exist);
		} else {
			mViolationTextView.setText(R.string.violation_not_exist);
		}

		String vehicleInfo = "";
		if (!raid.getVehicleInfo().isEmpty())
			vehicleInfo = String.format(mLocaleRu, "%s %s\n\n", vehicleInfo, raid.getVehicleInfo());
		if (!raid.getVehicleOwner().isEmpty()) {

			vehicleInfo = String.format(mLocaleRu, "%sСубъект/Перевозчик: %s\n", vehicleInfo, raid.getVehicleOwner());

			if (!raid.getOwnerInn().isEmpty())
				vehicleInfo = String.format(mLocaleRu, "%sИНН: %s, ", vehicleInfo, raid.getOwnerInn());

			if (!raid.getOwnerOgrn().isEmpty())
				vehicleInfo = String.format(mLocaleRu, "%sОГРН: %s", vehicleInfo, raid.getOwnerOgrn());
		}
		mVehicleTextView.setText(vehicleInfo);
	}

	private void moveToTrashClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_MaterialComponents_Light_Dialog);
		builder.setTitle(R.string.dialog_move_title);
		builder.setMessage(R.string.dialog_move_message);
		builder.setPositiveButton(R.string.trash, (dialog, which) -> mViewModel.moveToTrash());
		builder.setNegativeButton(R.string.cancel, ((dialog, which) -> dialog.dismiss()));
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
