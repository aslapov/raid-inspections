package aslapov.android.study.pallada.kisuknd.raids.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.RaidListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class RaidListFragment extends Fragment implements BaseAdapter.OnItemClickListener<RaidWithInspectors> {
    private static final String ARG_IS_DRAFT = "is_draft";

    private OnRaidSelectedListener mListener;

    private RaidAdapter mAdapter;
    private ProgressBar mLoading;

    public interface OnRaidSelectedListener {
        void onRaidSelected(RaidWithInspectors raid);
    }

    static RaidListFragment newInstance(boolean isDraft) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_IS_DRAFT, isDraft);

        RaidListFragment fragment = new RaidListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isDraft() {
        return (boolean) getArguments().getSerializable(ARG_IS_DRAFT);
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
        EmptyRecyclerView mRecyclerView = (EmptyRecyclerView) v.findViewById(R.id.raid_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setEmptyView(v.findViewById(R.id.raid_recycler_view));

        mAdapter = new RaidAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(this);

        mLoading = v.findViewById(R.id.loading);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLoading.setVisibility(View.VISIBLE);

        RaidListViewModel viewModel = new ViewModelProvider(this, new ViewModelFactory(getContext())).get(RaidListViewModel.class);
        viewModel.getRaidList(isDraft());
        viewModel.getViewModel().observe(getViewLifecycleOwner(), this::showRaids);
    }

    private void showRaids(RaidListViewModel viewModel) {
        mLoading.setVisibility(View.GONE);

        if (viewModel.getShowError() == null) {
            List<RaidWithInspectors> raids = viewModel.getRaids();
            mAdapter.changeDataSet(raids);
        } else {
            // TODO show error notification
        }
    }

    @Override
    public void onItemClick(@NonNull RaidWithInspectors raid) {
        mListener.onRaidSelected(raid);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.search_raid) {
            return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
