package aslapov.android.study.pallada.kisuknd.raids.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;

public abstract class BaseRaidListFragment extends Fragment implements BaseAdapter.OnItemClickListener<RaidWithInspectors> {

	private OnRaidSelectedListener mListener;

	private RaidAdapter mAdapter;
	private ProgressBar mLoading;
	private TextView mEmptyListTextView;
	private TextView mRaidListNameTextView;
	private EmptyRecyclerView mRecyclerView;

	protected abstract BaseListViewModel getViewModel();

	protected abstract int getListNameId();

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
		View v = inflater.inflate(R.layout.raid_list_fragment, container, false);

		mRecyclerView = (EmptyRecyclerView) v.findViewById(R.id.raid_recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setEmptyView(mRecyclerView);
		mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

		mAdapter = new RaidAdapter(new ArrayList<>());
		mAdapter.attachToRecyclerView(mRecyclerView);
		mAdapter.setOnItemClickListener(this);

		mLoading = v.findViewById(R.id.loading);
		mEmptyListTextView = (TextView) v.findViewById(R.id.empty_list_text_view);
		mRaidListNameTextView = (TextView) v.findViewById(R.id.raid_list_name);
		mRaidListNameTextView.setText(getListNameId());

		FloatingActionButton addRaidButton = v.findViewById(R.id.float_create);
		addRaidButton.setOnClickListener(view -> {
			CreateRaidActivity.start(getActivity()); //TODO startForResult CreateRaidActivity
		});

		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mLoading.setVisibility(View.VISIBLE);

		BaseListViewModel viewModel = getViewModel();
		viewModel.getViewModel().observe(getViewLifecycleOwner(), this::showRaids);
		viewModel.getRaidList();
	}

	private void showRaids(ViewModel viewModel) {
		mLoading.setVisibility(View.GONE);

		BaseListViewModel listViewModel = (BaseListViewModel) viewModel;

		if (listViewModel.getShowError() == null) {
			List<RaidWithInspectors> raids = listViewModel.getRaids();

			if (raids.isEmpty()) {
				mEmptyListTextView.setVisibility(View.VISIBLE);
				mRaidListNameTextView.setVisibility(View.GONE);
				mRecyclerView.setVisibility(View.GONE);
			} else {
				mEmptyListTextView.setVisibility(View.GONE);
				mRaidListNameTextView.setVisibility(View.VISIBLE);

				Collections.sort(raids, (raid1, raid2) -> {
					long start1 = raid1.getRaid().getRealStart().getTime();
					long start2 = raid2.getRaid().getRealStart().getTime();
					if (start1 == start2)
						return 0;
					return (start1 > start2) ? -1 : 1;
				});
				mAdapter.changeDataSet(raids);
			}
		} else {
			// TODO show error notification
		}
	}

	@Override
	public void onItemClick(@NonNull RaidWithInspectors raid) {
		mListener.onRaidSelected(raid);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
		mAdapter.clear();
		mAdapter = null;
	}
}
