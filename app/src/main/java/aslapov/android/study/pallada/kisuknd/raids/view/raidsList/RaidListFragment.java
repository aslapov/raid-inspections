package aslapov.android.study.pallada.kisuknd.raids.view.raidsList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidListPresenter;
import aslapov.android.study.pallada.kisuknd.raids.view.BaseAdapter;
import aslapov.android.study.pallada.kisuknd.raids.view.EmptyRecyclerView;

public class RaidListFragment extends Fragment implements IRaidListView, BaseAdapter.OnItemClickListener<Raid> {
    private RaidListPresenter mPresenter;

    private OnRaidSelectedListener mListener;

    private EmptyRecyclerView mRecyclerView;
    private RaidAdapter mAdapter;

    public interface OnRaidSelectedListener {
        void onRaidSelected(Raid raid);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnRaidSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRaidSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raid_list, container, false);
        mRecyclerView = (EmptyRecyclerView) v.findViewById(R.id.raid_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setEmptyView(v);

        mAdapter = new RaidAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(this);

        mPresenter = new RaidListPresenter();
        mPresenter.attachView(this);
        mPresenter.loadRaids();

        return v;
    }

    @Override
    public void showRaids(List<Raid> raids) {
        mAdapter.changeDataSet(raids);
    }

    @Override
    public void showRaidInfo(Raid raid) {
        mListener.onRaidSelected(raid);
    }

    @Override
    public void onItemClick(@NonNull Raid raid) {
        mPresenter.showRaidInfo(raid);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem subtitleItem = menu.findItem(R.id.new_raid);
        subtitleItem.setTitle("Create Raid");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_raid:

                return true;
            case R.id.search_raid:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
