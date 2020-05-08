package aslapov.android.study.pallada.kisuknd.raids.view;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidEntity;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidWithInspectors;

public class RaidHolder extends RecyclerView.ViewHolder {
    private TextView mTransportType;
    private TextView mActNumber;
    private TextView mStartDate;

    public RaidHolder(View itemView) {
        super(itemView);
        mTransportType = (TextView) itemView.findViewById(R.id.transport_type);
        mActNumber = (TextView) itemView.findViewById(R.id.act_number);
        mStartDate = (TextView) itemView.findViewById(R.id.start_date);
    }

    public void bind(RaidWithInspectors raid) {
        RaidEntity mRaid = raid.getRaidEntity();
        mTransportType.setText(mRaid.getTransportType());
        mActNumber.setText(mRaid.getActNumber());
        Date realStartDate = mRaid.getRealStart();
        String realStart = new SimpleDateFormat("dd.MM.yyyy").format(realStartDate);
        mStartDate.setText(realStart);
    }
}
