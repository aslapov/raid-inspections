package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class CreateRaidFragment extends Fragment {

    private CreateRaidViewModel mViewModel;

    private EditText mDepartment;
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
    private DateFormat mDateTimeFormatter = SimpleDateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, mLocaleRu);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_raid_layout, container, false);
        setHasOptionsMenu(true);

        Date currentDate = Calendar.getInstance().getTime();
        String dateString = mDateFormatter.format(currentDate);
        String timeString = mTimeFormatter.format(currentDate);

        mDepartment = v.findViewById(R.id.department);
        mInspector = v.findViewById(R.id.inspector);
        mStartDate = v.findViewById(R.id.start_date);
        mStartTime = v.findViewById(R.id.start_time);
        mEndDate = v.findViewById(R.id.end_date);
        mEndTime = v.findViewById(R.id.end_time);
        mTransportType = v.findViewById(R.id.transport_type);
        mAddress = v.findViewById(R.id.address);
        mActNumber = v.findViewById(R.id.act_number);
        mActDate = v.findViewById(R.id.act_date);
        mOrderNumber = v.findViewById(R.id.order_number);
        mOrderDate = v.findViewById(R.id.order_date);
        mTaskNumber = v.findViewById(R.id.task_number);
        mTaskDate = v.findViewById(R.id.task_date);
        mWarningCount = v.findViewById(R.id.warning_count);
        mWarningDate = v.findViewById(R.id.warning_date);
        mViolationExisting = v.findViewById(R.id.violation_existing);
        mVehicleInfo = v.findViewById(R.id.vehicle_info);
        mVehicleOwner = v.findViewById(R.id.vehicle_owner);
        mOwnerInn = v.findViewById(R.id.owner_inn);
        mOwnerOgrn = v.findViewById(R.id.owner_ogrn);

        mStartDate.setText(dateString);
        mStartTime.setText(timeString);
        mEndDate.setText(dateString);
        mEndTime.setText(timeString);
        mActDate.setText(dateString);
        mOrderDate.setText(dateString);
        mTaskDate.setText(dateString);
        mWarningDate.setText(dateString);

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

        MaterialButton mSave = v.findViewById(R.id.editor_menu_save_button);
        mSave.setOnClickListener(view -> {
            doSave();
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(CreateRaidViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_raid, menu);
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
        try {
            RaidWithInspectors raid = new RaidWithInspectors();

            Raid raidEntity = new Raid();
            List<RaidInspectionMember> members = new ArrayList<>();

            UUID raidId = UUID.randomUUID();

            raidEntity.setId(raidId.toString());
            raidEntity.setDraft(true);  // По умолчанию создаваемая запись попадает в черновики
            raidEntity.setDepartment(mDepartment.getText().toString());

            raidEntity.setRealStart(getDateFromDateAndTimeButtons(mStartDate, mStartTime));
            raidEntity.setRealEnd(getDateFromDateAndTimeButtons(mEndDate, mEndTime));

            raidEntity.setTransportType(mTransportType.getSelectedItem().toString());
            raidEntity.setPlaceAddress(mAddress.getText().toString());

            raidEntity.setActNumber(mActNumber.getText().toString());
            raidEntity.setActDate(getDateFromButtonText(mActDate));

            raidEntity.setOrderNumber(mOrderNumber.getText().toString());
            raidEntity.setOrderDate(getDateFromButtonText(mOrderDate));

            raidEntity.setTaskNumber(mTaskNumber.getText().toString());
            raidEntity.setTaskDate(getDateFromButtonText(mTaskDate));

            int warningCount = Integer.parseInt(mWarningCount.getText().toString());
            raidEntity.setWarningCount(warningCount);
            raidEntity.setWarningDate(getDateFromButtonText(mWarningDate));

            raidEntity.setViolationsPresence(mViolationExisting.isChecked());

            raidEntity.setVehicleInfo(mVehicleInfo.getText().toString());
            raidEntity.setVehicleOwner(mVehicleOwner.getText().toString());
            raidEntity.setOwnerInn(mOwnerInn.getText().toString());
            raidEntity.setOwnerOgrn(mOwnerOgrn.getText().toString());

            Date currentDate = Calendar.getInstance().getTime();
            raidEntity.setCreateDate(currentDate);
            raidEntity.setUpdateDate(currentDate);
            //raidEntity.setTimeZone(currentDate);

            RaidInspectionMember member = new RaidInspectionMember();
            member.setContactName(mInspector.getText().toString());
            member.setRaidInspectionId(raidId.toString());
            members.add(member);

            raid.setRaid(raidEntity);
            raid.setInspectors(members);

            mViewModel.createRaid(raid);
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date getDateFromButtonText(MaterialButton button) throws ParseException {
        String dateString = button.getText().toString();
        return mDateFormatter.parse(dateString);
    }

    private Date getDateFromDateAndTimeButtons(MaterialButton buttonDate, MaterialButton buttonTime) throws ParseException {
        String dateString = buttonDate.getText().toString() + " " + buttonTime.getText().toString();
        return mDateTimeFormatter.parse(dateString);
    }
}
