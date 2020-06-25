package aslapov.android.study.pallada.kisuknd.raids.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseViewModel;

public abstract class BaseEditableRaidActivity extends AppCompatActivity {

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

	private ArrayAdapter<CharSequence> mAdapterDepartment;
	private ArrayAdapter<CharSequence> mAdapterTransportType;

	private Locale mLocaleRu = new Locale("ru");
	private DateFormat mTimeFormatter = SimpleDateFormat.getTimeInstance(DateFormat.SHORT, mLocaleRu);

	protected abstract BaseViewModel getViewModel();

	protected abstract void viewModelInit();

	protected abstract void doSave();

	protected abstract void updateUI(ViewModel viewModel);

	protected void onUpButtonPressed() {
		onBackPressed();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editable_raid_activity);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(view -> onUpButtonPressed());

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

		BaseViewModel viewModel = getViewModel();
		viewModelInit();
		viewModel.getViewModelObserver().observe(this, this::updateUI);

		View.OnClickListener dateChooseClick = view -> {
			//TODO Отображать выбранную дату
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
			//TODO Отображать выбранное время
			Calendar calendar = Calendar.getInstance();
			int currentHour = calendar.get(Calendar.YEAR);
			int currentMinute = calendar.get(Calendar.MONTH);

			TimePickerDialog.OnTimeSetListener listener = (picker, hour, minute) -> {
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				Date date = calendar.getTime();
				String dateText = mTimeFormatter.format(date);
				((MaterialButton) view).setText(dateText);
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

		mAdapterDepartment = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_spinner_item);
		mAdapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mDepartment.setAdapter(mAdapterDepartment);

		mAdapterTransportType = ArrayAdapter.createFromResource(this, R.array.transport_type, android.R.layout.simple_spinner_item);
		mAdapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_item);
		mTransportType.setAdapter(mAdapterTransportType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_raid, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		super.onOptionsItemSelected(item);
		if (item.getItemId() == R.id.menu_save) {
			doSave();
			return true;
		}
		return false;
	}

	public Spinner getDepartment() {
		return mDepartment;
	}

	public EditText getInspector() {
		return mInspector;
	}

	public MaterialButton getStartDate() {
		return mStartDate;
	}

	public MaterialButton getStartTime() {
		return mStartTime;
	}

	public MaterialButton getEndDate() {
		return mEndDate;
	}

	public MaterialButton getEndTime() {
		return mEndTime;
	}

	public Spinner getTransportType() {
		return mTransportType;
	}

	public EditText getAddress() {
		return mAddress;
	}

	public EditText getActNumber() {
		return mActNumber;
	}

	public MaterialButton getActDate() {
		return mActDate;
	}

	public EditText getOrderNumber() {
		return mOrderNumber;
	}

	public MaterialButton getOrderDate() {
		return mOrderDate;
	}

	public EditText getTaskNumber() {
		return mTaskNumber;
	}

	public MaterialButton getTaskDate() {
		return mTaskDate;
	}

	public EditText getWarningCount() {
		return mWarningCount;
	}

	public MaterialButton getWarningDate() {
		return mWarningDate;
	}

	public MaterialCheckBox getViolationExisting() {
		return mViolationExisting;
	}

	public EditText getVehicleInfo() {
		return mVehicleInfo;
	}

	public EditText getVehicleOwner() {
		return mVehicleOwner;
	}

	public EditText getOwnerInn() {
		return mOwnerInn;
	}

	public EditText getOwnerOgrn() {
		return mOwnerOgrn;
	}

	public ArrayAdapter<CharSequence> getAdapterDepartment() {
		if (mAdapterDepartment == null)
			throw new IllegalStateException();
		return mAdapterDepartment;
	}

	public ArrayAdapter<CharSequence> getAdapterTransportType() {
		if (mAdapterTransportType == null)
			throw new IllegalStateException();
		return mAdapterTransportType;
	}
}
