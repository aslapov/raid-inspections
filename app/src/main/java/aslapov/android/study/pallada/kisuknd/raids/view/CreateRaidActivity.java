package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.CreateRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class CreateRaidActivity extends BaseEditableRaidActivity {

    private CreateRaidViewModel mViewModel;

    private Locale mLocaleRu = new Locale("ru");
    private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu);
    private DateFormat mTimeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, mLocaleRu);

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateRaidActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setViewModel() {
        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(CreateRaidViewModel.class);
        mViewModel.init();
        mViewModel.getViewModel().observe(this, this::updateUI);
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

        mViewModel.saveRaid();
    }

    private void updateUI(CreateRaidViewModel viewModel) {
        if (viewModel.isCreated()) {
            finish();
        } else {
            if (viewModel.getStartDateError() != null) {
                // TODO Показать сообщение об ошибке
            }
            Raid raid = viewModel.getRaid();

            //TODO show Department and TransportType
            //getDepartment().setSelection(mDepartment.get);
            getInspector().setText(viewModel.getInspector().getContactName());
            getStartDate().setText(mDateFormatter.format(raid.getRealStart()));
            getStartTime().setText(mTimeFormatter.format(raid.getRealStart()));
            getEndDate().setText(mDateFormatter.format(raid.getRealEnd()));
            getEndTime().setText(mTimeFormatter.format(raid.getRealEnd()));
            //getTransportType().setSelection();
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
