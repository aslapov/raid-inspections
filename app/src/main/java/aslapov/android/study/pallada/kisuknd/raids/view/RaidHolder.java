package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidEntity;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;

public class RaidHolder extends RecyclerView.ViewHolder {
    private TextView mOwnerTextView;
    private TextView mTransportTypeTextView;
    private TextView mVehicleInfoTextView;
    private RaidEntity mRaid;

    public RaidHolder(View itemView) {
        super(itemView);
        mOwnerTextView = (TextView) itemView.findViewById(R.id.vehicle_owner);
        mTransportTypeTextView = (TextView) itemView.findViewById(R.id.transport_type);
        mVehicleInfoTextView = (TextView) itemView.findViewById(R.id.vehicle_info);
    }

    public void bind(RaidWithInspectors raid) {
        mRaid = raid.getRaidEntity();
        mOwnerTextView.setText(mRaid.getVehicleOwner());
        mTransportTypeTextView.setText(mRaid.getTransportType());
        mVehicleInfoTextView.setText(mRaid.getVehicleInfo());
    }
}
