package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.view.BaseAdapter;

public class RaidAdapter extends BaseAdapter<RaidHolder, Raid> {

    public RaidAdapter(@NonNull List<Raid> items) { super(items); }

    @Override
    public RaidHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RaidHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_raid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RaidHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Raid raid = getItem(position);
        holder.bind(raid);
    }
}
