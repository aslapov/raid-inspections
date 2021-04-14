package aslapov.android.study.pallada.kisuknd.raids.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.EditRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class EditRaidActivity extends BaseEditableRaidActivity {
	public static final String EXTRA_RAID_ID = "aslapov.android.study.pallada.kisuknd.raids.ui.raid_id";

	private EditRaidViewModel mViewModel;

	private Locale mLocaleRu = new Locale("ru");
	private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu);
	private DateFormat mTimeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, mLocaleRu);

	public static void start(Activity activity, UUID raidId) {
		Intent intent = new Intent(activity, EditRaidActivity.class);
		intent.putExtra(EXTRA_RAID_ID, raidId);
		activity.startActivity(intent);
	}

	@Override
	protected BaseViewModel getViewModel() {
		if (mViewModel == null)
			mViewModel = new ViewModelProvider(this, new ViewModelFactory(this)).get(EditRaidViewModel.class);

		return mViewModel;
	}

	@Override
	protected void viewModelInit() {
		UUID raidId = (UUID) getIntent().getSerializableExtra(EXTRA_RAID_ID);
		mViewModel.init(raidId);
	}

	@Override
	protected void doSave() {
		mViewModel.setDepartment(getDepartment().getSelectedItem().toString());
		mViewModel.setInspector(getInspector().getText());
		mViewModel.setStartDate(getStartDate().getText(), getStartTime().getText());
		mViewModel.setEndDate(getEndDate().getText(), getEndTime().getText());
		mViewModel.setTransportType(getTransportType().getSelectedItem().toString());
		mViewModel.setPlaceAddress(getAddress().getText());
		mViewModel.setActNumber(getActNumber().getText());
		mViewModel.setActDate(getActDate().getText());
		mViewModel.setOrderNumber(getOrderNumber().getText());
		mViewModel.setOrderDate(getOrderDate().getText());
		mViewModel.setTaskNumber(getTaskNumber().getText());
		mViewModel.setTaskDate(getTaskDate().getText());
		mViewModel.setWarningCount(getWarningCount().getText());
		mViewModel.setWarningDate(getWarningDate().getText());
		mViewModel.setViolationsPresence(getViolationExisting().isChecked());
		mViewModel.setVehicleInfo(getVehicleInfo().getText());
		mViewModel.setVehicleOwner(getVehicleOwner().getText());
		mViewModel.setOwnerInn(getOwnerInn().getText());
		mViewModel.setOwnerOgrn(getOwnerOgrn().getText());

		mViewModel.editRaid();
	}

	@Override
	protected void updateUI(ViewModel viewModel) {
		EditRaidViewModel raidViewModel = (EditRaidViewModel) viewModel;

		if (raidViewModel.isEdited()) {
			finish();
		} else {
			Raid raid = raidViewModel.getRaid();

			getDepartment().setSelection(getAdapterDepartment().getPosition(raid.getDepartment()));
			getInspector().setText(raidViewModel.getInspector().getContactName());
			getStartDate().setText(mDateFormatter.format(raid.getRealStart()));
			getStartTime().setText(mTimeFormatter.format(raid.getRealStart()));
			getEndDate().setText(mDateFormatter.format(raid.getRealEnd()));
			getEndTime().setText(mTimeFormatter.format(raid.getRealEnd()));
			getTransportType().setSelection(getAdapterTransportType().getPosition(raid.getTransportType()));
			getAddress().setText(raid.getPlaceAddress());
			getActNumber().setText(raid.getActNumber());
			getActDate().setText(mDateFormatter.format(raid.getActDate()));
			getOrderNumber().setText(raid.getOrderNumber());
			getOrderDate().setText(mDateFormatter.format(raid.getOrderDate()));
			getTaskNumber().setText(raid.getTaskNumber());
			getTaskDate().setText(mDateFormatter.format(raid.getTaskDate()));
			getWarningCount().setText(String.valueOf(raid.getWarningCount()));
			getWarningDate().setText(mDateFormatter.format(raid.getWarningDate()));
			getViolationExisting().setChecked(raid.isViolationsPresence());
			getVehicleInfo().setText(raid.getVehicleInfo());
			getVehicleOwner().setText(raid.getVehicleOwner());
			getOwnerInn().setText(raid.getOwnerInn());
			getOwnerOgrn().setText(raid.getOwnerOgrn());
		}
	}

	@Override
	protected void onUpButtonPressed() {
		// TODO Проверка на изменения
		super.onUpButtonPressed();
	}
}
