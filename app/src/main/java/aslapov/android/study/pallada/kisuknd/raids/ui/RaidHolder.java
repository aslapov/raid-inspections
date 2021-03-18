package aslapov.android.study.pallada.kisuknd.raids.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

class RaidHolder extends RecyclerView.ViewHolder {
    private TextView mActTextView;
    private ImageView mTransportImage;
    private TextView mTimeTextView;
    private TextView mPlaceTextView;

    private Locale mLocaleRu = new Locale("ru");
    private DateFormat mDateFormatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT, mLocaleRu);

    RaidHolder(View itemView) {
        super(itemView);
        mTransportImage = (ImageView) itemView.findViewById(R.id.raid_transport_image);
        mTimeTextView = (TextView) itemView.findViewById(R.id.raid_time);
        mPlaceTextView = (TextView) itemView.findViewById(R.id.raid_location);
        mActTextView = (TextView) itemView.findViewById(R.id.raid_act_number);
    }

    void bind(RaidWithInspectors raidWithInspectors) {
        Raid raid = raidWithInspectors.getRaid();
        mPlaceTextView.setText(raid.getPlaceAddress());
        mActTextView.setText("Акт: № " + raid.getActNumber());

        String startDate = mDateFormatter.format(raid.getRealStart());
        mTimeTextView.setText(startDate);


        switch (raid.getTransportType()) {
            case "Автомобильный":
                mTransportImage.setImageResource(R.drawable.auto);
                break;
            case "Воздушный":
                mTransportImage.setImageResource(R.drawable.airplane);
                break;
            case "Железнодорожный":
                mTransportImage.setImageResource(R.drawable.railroad);
                break;
            case "Морской":
                mTransportImage.setImageResource(R.drawable.sea);
                break;
            case "Внутренний водный":
                mTransportImage.setImageResource(R.drawable.water);
                break;
        }
    }
}
