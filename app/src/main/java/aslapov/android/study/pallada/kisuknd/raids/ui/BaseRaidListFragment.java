package aslapov.android.study.pallada.kisuknd.raids.ui;

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
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.BaseListViewModel;

public abstract class BaseRaidListFragment extends Fragment implements BaseAdapter.OnItemClickListener<RaidWithInspectors> {

	private OnRaidShowListener mListener;

	private RaidAdapter mAdapter;
	private ProgressBar mLoading;
	private TextView mEmptyListTextView;
	private EmptyRecyclerView mRecyclerView;

	protected abstract BaseListViewModel getViewModel();

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		try {
			mListener = (OnRaidShowListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement OnRaidShowListener");
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
		viewModel.getViewModelObserver().observe(getViewLifecycleOwner(), this::showRaids);
		viewModel.getRaids();
	}

	@Override
	public void onStart() {
		super.onStart();

		// Снимать выделение выбранного элемента при однопанельном представлении
		if (getActivity().findViewById(R.id.detail_fragment_container) == null) {
			mAdapter.setSelectedPosition(-1);
			getViewModel().setSelectedItemPosition(-1);
		}
	}

	private void showRaids(ViewModel viewModel) {
		mLoading.setVisibility(View.GONE);

		BaseListViewModel listViewModel = (BaseListViewModel) viewModel;

		if (listViewModel.getShowError() == null) {
			List<RaidWithInspectors> raids = listViewModel.getRaidList();

			if (raids.isEmpty()) {
				mEmptyListTextView.setVisibility(View.VISIBLE);
				mRecyclerView.setVisibility(View.GONE);

				mListener.onRaidEmpty();
			} else {
				mEmptyListTextView.setVisibility(View.GONE);
				mAdapter.changeDataSet(raids);


				if (getActivity().findViewById(R.id.detail_fragment_container) != null) {
					int position = listViewModel.getSelectedItemPosition();
					if (listViewModel.isRaidListSizeChanged()) {
						if (position >= raids.size()) {
							mAdapter.setSelectedPosition(-1);
							getViewModel().setSelectedItemPosition(-1);
							mListener.onRaidEmpty();
						} else {
							mAdapter.unSelectPosition(position);
							mListener.onRaidSelected(raids.get(position));
						}
					} else {
						if (position == -1) {
							position += 1;
							listViewModel.setSelectedItemPosition(position);
						}

						// TODO понять почему данный метод может вызываться несколько раз
						// количество вызовов увеличивается в зависимости от количества повороов экрана
						mAdapter.setSelectedPosition(position);
						mListener.onRaidSelected(raids.get(position));
					}
				}
			}
		} else {
			// TODO show error notification
		}
	}

	@Override
	public void onItemClick(@NonNull RaidWithInspectors raid) {
		int position = mAdapter.getSelectedPosition();
		getViewModel().setSelectedItemPosition(position);

		// Необходимо подсвечивать выбранный элемент только для 2-хпанельного представления
		if (getActivity().findViewById(R.id.detail_fragment_container) != null)
			mAdapter.setSelectedPosition(position);

		mListener.onRaidSelected(raid);
	}

	public boolean isItemSelected() {
		return getViewModel().getSelectedItemPosition() != -1;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
		mAdapter.clear();
		mAdapter = null;
	}
}
