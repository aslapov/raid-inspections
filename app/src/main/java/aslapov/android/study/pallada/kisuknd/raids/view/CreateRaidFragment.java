package aslapov.android.study.pallada.kisuknd.raids.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidInspectionMember;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.CreateRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class CreateRaidFragment extends Fragment {

    private CreateRaidViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_raid_layout, container, false);

        MaterialTextView mDepartment = (MaterialTextView) v.findViewById(R.id.department);
        AppCompatSpinner mTransportType = (AppCompatSpinner) v.findViewById(R.id.transport_type);
        EditText mAddress = (EditText) v.findViewById(R.id.address);
        EditText mActNumber = (EditText) v.findViewById(R.id.act_number);
        EditText mInspector = (EditText) v.findViewById(R.id.inspector);
        EditText mDispositionNumber = (EditText) v.findViewById(R.id.disposition_number);
        //MaterialButton mDispositionDate = (MaterialButton) v.findViewById(R.id.disposition_date);
        EditText mTaskNumber = (EditText) v.findViewById(R.id.task_number);
        //MaterialButton mTaskDate = (MaterialButton) v.findViewById(R.id.task_date);
        //MaterialButton mStartDate = (MaterialButton) v.findViewById(R.id.start_date);
        //MaterialButton mStartTime =  (MaterialButton) v.findViewById(R.id.start_time);
        //MaterialButton mEndDate =  (MaterialButton) v.findViewById(R.id.end_date);
        //MaterialButton mEndTime = (MaterialButton) v.findViewById(R.id.end_time);
        EditText mVehicleInfo = (EditText) v.findViewById(R.id.vehicle_info);
        EditText mOwnerOgrn = (EditText) v.findViewById(R.id.owner_ogrn);
        EditText mOwnerInn = (EditText) v.findViewById(R.id.owner_inn);
        EditText mVehicleOwner = (EditText) v.findViewById(R.id.vehicle_owner);
        //MaterialButton mWarningCount = (MaterialButton) v.findViewById(R.id.warning_count);
        //MaterialButton mWarningDate = (MaterialButton) v.findViewById(R.id.warning_date);
        MaterialCheckBox mViolationExisting = (MaterialCheckBox) v.findViewById(R.id.violation_existing);

        MaterialButton mSave = (MaterialButton) v.findViewById(R.id.save_button);
        mSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RaidWithInspectors raid = new RaidWithInspectors();
                Raid raidEntity = new Raid();
                UUID raidId = UUID.randomUUID();
                raidEntity.setId(raidId.toString());
                raidEntity.setDepartment("ОНОТБ УГАН НОТБ СЗФО");
                //raidEntity.setTransportType();
                raidEntity.setPlaceAddress(mAddress.getText().toString());
                raidEntity.setActNumber(mActNumber.getText().toString());
                raidEntity.setOrderNumber(mDispositionNumber.getText().toString());
                raidEntity.setTaskNumber(mTaskNumber.getText().toString());
                raidEntity.setVehicleInfo(mVehicleInfo.getText().toString());
                raidEntity.setOwnerOgrn(mOwnerOgrn.getText().toString());
                raidEntity.setOwnerInn(mOwnerInn.getText().toString());
                raidEntity.setVehicleOwner(mVehicleOwner.getText().toString());
                //raidEntity.setViolationsPresence(mViolationExisting.getText().);

                List<RaidInspectionMember> members = new ArrayList<>();
                RaidInspectionMember member = new RaidInspectionMember();
                member.setContactName(mInspector.getText().toString());
                member.setRaidInspectionId(raidId.toString());

                members.add(member);
                raid.setRaid(raidEntity);
                raid.setInspectors(members);

                mViewModel.createRaid(raid);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(CreateRaidViewModel.class);
    }
}
