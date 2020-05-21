package aslapov.android.study.pallada.kisuknd.raids.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

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

    private MaterialTextView mDepartment;
    private AppCompatSpinner mTransportType;
    private EditText mAddress;
    private EditText mActNumber;
    private EditText mInspector;
    private EditText mDispositionNumber;
    private MaterialButton mDispositionDate;
    private EditText mTaskNumber;
    private MaterialButton mTaskDate;
    private MaterialButton mStartDate;
    private MaterialButton mStartTime;
    private MaterialButton mEndDate;
    private MaterialButton mEndTime;
    private EditText mVehicleInfo;
    private EditText mOwnerOgrn;
    private EditText mOwnerInn;
    private EditText mVehicleOwner;
    private MaterialButton mWarningCount;
    private MaterialButton mWarningDate;
    private MaterialCheckBox mViolationExisting;
    private MaterialButton mSave;

    public static ShowRaidFragment newInstance(UUID raidId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RAID_ID, raidId);

        ShowRaidFragment fragment = new ShowRaidFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UUID getRaidId() {
        return (UUID) getArguments().getSerializable(ARG_RAID_ID);
    }

    private RaidWithInspectors mRaid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_raid_layout, container, false);

        mLoading = v.findViewById(R.id.loading);

        mDepartment = (MaterialTextView) v.findViewById(R.id.department);
        mTransportType = (AppCompatSpinner) v.findViewById(R.id.transport_type);
        mAddress = (EditText) v.findViewById(R.id.address);
        mActNumber = (EditText) v.findViewById(R.id.act_number);
        mInspector = (EditText) v.findViewById(R.id.inspector);
        mDispositionNumber = (EditText) v.findViewById(R.id.order_number);
        mDispositionDate =  (MaterialButton) v.findViewById(R.id.order_date);
        mTaskNumber = (EditText) v.findViewById(R.id.task_number);
        mTaskDate = (MaterialButton) v.findViewById(R.id.task_date);
        mStartDate = (MaterialButton) v.findViewById(R.id.start_date);
        mStartTime =  (MaterialButton) v.findViewById(R.id.start_time);
        mEndDate =  (MaterialButton) v.findViewById(R.id.end_date);
        mEndTime = (MaterialButton) v.findViewById(R.id.end_time);
        mVehicleInfo = (EditText) v.findViewById(R.id.vehicle_info);
        mOwnerOgrn = (EditText) v.findViewById(R.id.owner_ogrn);
        mOwnerInn = (EditText) v.findViewById(R.id.owner_inn);
        mVehicleOwner = (EditText) v.findViewById(R.id.vehicle_owner);
        mWarningCount = (MaterialButton) v.findViewById(R.id.warning_count);
        mWarningDate = (MaterialButton) v.findViewById(R.id.warning_date);
        mViolationExisting = (MaterialCheckBox) v.findViewById(R.id.violation_existing);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoading.setVisibility(View.VISIBLE);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(ShowRaidViewModel.class);
        mViewModel.getRaid(getRaidId()).observe(getViewLifecycleOwner(), this::showRaidInfo);
    }

    private void showRaidInfo(RaidWithInspectors raidInspection) {
        mLoading.setVisibility(View.GONE);
        mRaid = raidInspection;

        Raid raid = raidInspection.getRaid();
        mDepartment.setText(raid.getDepartment());
        //transportType.text
        mAddress.setText(raid.getPlaceAddress());
        mActNumber.setText(raid.getActNumber());
        mInspector.setText(raidInspection.getInspectors().get(0).getContactName());
        mDispositionNumber.setText(raid.getOrderNumber());
        mDispositionDate.setText(raid.getOrderDate().toString());
        mTaskNumber.setText(raid.getTaskNumber());
        mTaskDate.setText(raid.getTaskDate().toString());
        mStartDate.setText(raid.getRealStart().toString());
        mEndDate.setText(raid.getRealEnd().toString());
        mVehicleInfo.setText(raid.getVehicleInfo());
        mOwnerInn.setText(raid.getOwnerInn());
        mOwnerOgrn.setText(raid.getOwnerOgrn());
        mVehicleOwner.setText(raid.getVehicleOwner());
        mWarningCount.setText(Integer.toString(raid.getWarningCount()));
        //warningDate.setText(raid.getWarningDate().toString());
        mViolationExisting.setChecked(raid.isViolationsPresence());
    }
}
