package aslapov.android.study.pallada.kisuknd.raids.view.raid;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidPresenter;

public class RaidFragment extends Fragment implements IRaidView {
    public static final String TAG = "RaidFragment";
    private static final String ARG_RAID_ID = "raid_id";

    private RaidPresenter mPresenter;

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

    public static RaidFragment newInstance(UUID raidId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RAID_ID, raidId);

        RaidFragment fragment = new RaidFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UUID getShownIndex() {
        return (UUID) getArguments().getSerializable(ARG_RAID_ID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("log " + this.toString(), "onCreate");
        super.onCreate(savedInstanceState);

        mPresenter = new RaidPresenter();
        mPresenter.attachView(this);
        mPresenter.loadRaid(getShownIndex());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("log " + this.toString(), "onCreateView");
        View v = inflater.inflate(R.layout.raid_layout, container, false);

        mDepartment = (MaterialTextView) v.findViewById(R.id.department);
        mTransportType = (AppCompatSpinner) v.findViewById(R.id.transport_type);
        mAddress = (EditText) v.findViewById(R.id.address);
        mActNumber = (EditText) v.findViewById(R.id.act_number);
        mInspector = (EditText) v.findViewById(R.id.inspector);
        mDispositionNumber = (EditText) v.findViewById(R.id.disposition_number);
        mDispositionDate =  (MaterialButton) v.findViewById(R.id.disposition_date);
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
    public void showRaidInfo(Raid raid) {
        Log.d("log " + this.toString(), "showRaidInfo");

        // Если активити не финишируется (в случае поворота экрана)
        // Явно финишируется RaidActivity в случае поворота экрана на двупанельный экран
        if (!getActivity().isFinishing()) { // TODO возможно кривое условие
            mDepartment.setText(raid.getDepartment());
            //transportType.text
            mAddress.setText(raid.getPlaceAddress());
            mActNumber.setText(raid.getActNumber());
            mInspector.setText(raid.getInspectors().get(0));
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

    @Override
    public void onPause() {
        Log.d("log " + this.toString(), "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("log " + this.toString(), "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("log " + this.toString(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("log " + this.toString(), "onDetach");
        mPresenter.detachView();
        super.onDetach();
    }
}
