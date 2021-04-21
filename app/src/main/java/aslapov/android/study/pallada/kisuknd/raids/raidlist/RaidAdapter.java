package aslapov.android.study.pallada.kisuknd.raids.raidlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.RaidWithInspectors;

public class RaidAdapter extends BaseAdapter<RaidHolder, RaidWithInspectors> {

    public RaidAdapter(@NonNull List<RaidWithInspectors> items) { super(items); }

    @Override
    public RaidHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RaidHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raid_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RaidHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RaidWithInspectors raid = getItem(position);
        holder.bind(raid);
    }
}
