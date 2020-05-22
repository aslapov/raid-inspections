package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidInspectionMember;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.CreateRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class CreateRaidActivity extends AppCompatActivity {

    private CreateRaidViewModel mViewModel;

    private Spinner mDepartment;
    private EditText mInspector;
    private MaterialButton mStartDate;
    private MaterialButton mStartTime;
    private MaterialButton mEndDate;
    private MaterialButton mEndTime;
    private Spinner mTransportType;
    private EditText mAddress;
    private EditText mActNumber;
    private MaterialButton mActDate;
    private EditText mOrderNumber;
    private MaterialButton mOrderDate;
    private EditText mTaskNumber;
    private MaterialButton mTaskDate;
    private EditText mWarningCount;
    private MaterialButton mWarningDate;
    private MaterialCheckBox mViolationExisting;
    private EditText mVehicleInfo;
    private EditText mVehicleOwner;
    private EditText mOwnerInn;
    private EditText mOwnerOgrn;

    private Locale mLocaleRu = new Locale("ru");
    private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu);
    private DateFormat mTimeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, mLocaleRu);

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CreateRaidActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_raid_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(CreateRaidViewModel.class);
        mViewModel.getViewModel().observe(this, this::updateUI);

        mDepartment = findViewById(R.id.department);
        mInspector = findViewById(R.id.inspector);
        mStartDate = findViewById(R.id.start_date);
        mStartTime = findViewById(R.id.start_time);
        mEndDate = findViewById(R.id.end_date);
        mEndTime = findViewById(R.id.end_time);
        mTransportType = findViewById(R.id.transport_type);
        mAddress = findViewById(R.id.address);
        mActNumber = findViewById(R.id.act_number);
        mActDate = findViewById(R.id.act_date);
        mOrderNumber = findViewById(R.id.order_number);
        mOrderDate = findViewById(R.id.order_date);
        mTaskNumber = findViewById(R.id.task_number);
        mTaskDate = findViewById(R.id.task_date);
        mWarningCount = findViewById(R.id.warning_count);
        mWarningDate = findViewById(R.id.warning_date);
        mViolationExisting = findViewById(R.id.violation_existing);
        mVehicleInfo = findViewById(R.id.vehicle_info);
        mVehicleOwner = findViewById(R.id.vehicle_owner);
        mOwnerInn = findViewById(R.id.owner_inn);
        mOwnerOgrn = findViewById(R.id.owner_ogrn);

        View.OnClickListener dateChooseClick = view -> {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH);
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener listener = (picker, year, month, day) -> {
                MaterialButton btn = (MaterialButton) view;
                calendar.set(year, month, day);
                Date date = calendar.getTime();
                String dateText = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, mLocaleRu).format(date);
                btn.setText(dateText);
            };

            DatePickerDialog dialog = new DatePickerDialog(view.getContext(), listener, currentYear, currentMonth, currentDay);
            dialog.show();
        };

        View.OnClickListener timeChooseClick = view -> {
            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.YEAR);
            int currentMinute = calendar.get(Calendar.MONTH);

            TimePickerDialog.OnTimeSetListener listener = (picker, hour, minute) -> {
                MaterialButton btn = (MaterialButton) view;
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                Date date = calendar.getTime();
                String dateText = mTimeFormatter.format(date);
                btn.setText(dateText);
            };

            TimePickerDialog dialog = new TimePickerDialog(view.getContext(), listener, currentHour, currentMinute, true);
            dialog.show();
        };

        mStartDate.setOnClickListener(dateChooseClick);
        mStartTime.setOnClickListener(timeChooseClick);
        mEndDate.setOnClickListener(dateChooseClick);
        mEndTime.setOnClickListener(timeChooseClick);
        mActDate.setOnClickListener(dateChooseClick);
        mOrderDate.setOnClickListener(dateChooseClick);
        mTaskDate.setOnClickListener(dateChooseClick);
        mWarningDate.setOnClickListener(dateChooseClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_raid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                doSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doSave() {
        mViewModel.setDepartment(mDepartment.getSelectedItem().toString());
        mViewModel.setInspector(mInspector.getText());
        mViewModel.setStartDate(mStartDate.getText(), mStartTime.getText());
        mViewModel.setEndDate(mEndDate.getText(), mEndTime.getText());
        mViewModel.setTransportType(mTransportType.getSelectedItem().toString());
        mViewModel.setPlaceAddress(mAddress.getText());
        mViewModel.setActNumber(mActNumber.getText());
        mViewModel.setActDate(mActDate.getText());
        mViewModel.setOrderNumber(mOrderNumber.getText());
        mViewModel.setOrderDate(mOrderDate.getText());
        mViewModel.setTaskNumber(mTaskNumber.getText());
        mViewModel.setTaskDate(mTaskDate.getText());
        mViewModel.setWarningCount(mWarningCount.getText());
        mViewModel.setWarningDate(mWarningDate.getText());
        mViewModel.setViolationsPresence(mViolationExisting.isChecked());
        mViewModel.setVehicleInfo(mVehicleInfo.getText());
        mViewModel.setVehicleOwner(mVehicleOwner.getText());
        mViewModel.setOwnerInn(mOwnerInn.getText());
        mViewModel.setOwnerOgrn(mOwnerOgrn.getText());

        mViewModel.saveRaid();
    }

    private void updateUI(CreateRaidViewModel viewModel) {
        if (viewModel.getStartDateError() != null) {
            // TODO Показать сообщение об ошибке
        }
        Raid raid = viewModel.getRaid();

        //mDepartment.setSelection(mDepartment.get);
        mInspector.setText(viewModel.getInspector().getContactName());
        mStartDate.setText(mDateFormatter.format(raid.getRealStart()));
        mStartTime.setText(mTimeFormatter.format(raid.getRealStart()));
        mEndDate.setText(mDateFormatter.format(raid.getRealEnd()));
        mEndTime.setText(mTimeFormatter.format(raid.getRealEnd()));
        //mTransportType.setSelection();
        mAddress.setText(raid.getPlaceAddress());
        mActNumber.setText(raid.getActNumber());
        mActDate.setText(mDateFormatter.format(raid.getActDate()));
        mOrderNumber.setText(raid.getOrderNumber());
        mOrderDate.setText(mDateFormatter.format(raid.getOrderDate()));
        mTaskNumber.setText(raid.getTaskNumber());
        mTaskDate.setText(mDateFormatter.format(raid.getTaskDate()));
        mWarningCount.setText(raid.getWarningCount());
        mWarningDate.setText(mDateFormatter.format(raid.getWarningDate()));
        mViolationExisting.setChecked(raid.isViolationsPresence());
        mVehicleInfo.setText(raid.getVehicleInfo());
        mVehicleOwner.setText(raid.getVehicleOwner());
        mOwnerInn.setText(raid.getOwnerInn());
        mOwnerOgrn.setText(raid.getOwnerOgrn());
    }
}
