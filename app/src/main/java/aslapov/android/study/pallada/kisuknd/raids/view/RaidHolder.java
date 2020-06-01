package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

class RaidHolder extends RecyclerView.ViewHolder {
    private TextView mTransportType;
    private TextView mActNumber;
    private TextView mStartDate;

    RaidHolder(View itemView) {
        super(itemView);
        mTransportType = (TextView) itemView.findViewById(R.id.transport_type);
        mActNumber = (TextView) itemView.findViewById(R.id.act_number);
        mStartDate = (TextView) itemView.findViewById(R.id.start_date);
    }

    void bind(RaidWithInspectors raid) {
        Raid mRaid = raid.getRaid();
        mTransportType.setText(mRaid.getTransportType());
        mActNumber.setText(mRaid.getActNumber());
        Date realStartDate = mRaid.getRealStart();
        String realStart = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).format(realStartDate);
        mStartDate.setText(realStart);
    }
}
