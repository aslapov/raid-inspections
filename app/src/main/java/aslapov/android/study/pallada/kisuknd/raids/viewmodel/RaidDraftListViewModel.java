package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RaidDraftListViewModel extends ViewModel implements BaseListViewModel {

	// Запрашиваемый статус рейдовых осмотров
	// "1" - "Черновики"
	private static final Integer sStatus = RaidStatus.DRAFT.ordinal();

	private RaidRepository mRaidRepository;

	private MutableLiveData<RaidDraftListViewModel> mViewModel = new MutableLiveData<>();
	private MutableLiveData<List<RaidWithInspectors>> mRaids = new MutableLiveData<>();
	private String mShowError;

	//@Inject
	public void setRaidRepository(RaidRepository repo) {
		if (repo == null)
			throw new IllegalArgumentException("repo");
		mRaidRepository = repo;
	}

	@NotNull
	private RaidRepository getRaidRepository() {
		if (mRaidRepository == null)
			throw new IllegalStateException();
		return mRaidRepository;
	}

	public MutableLiveData<RaidDraftListViewModel> getViewModel() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	public void getRaidList() {
		getRaidRepository()
				.queryRaids(sStatus)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						raids -> {
							mRaids.setValue(raids);
							notifyViewModelChange();
						},
						error -> {
							mShowError = error.getMessage();
							notifyViewModelChange();
						}
				);
	}

	public List<RaidWithInspectors> getRaids() {
		return mRaids.getValue();
	}

	public String getShowError() {
		return mShowError;
	}

	private void notifyViewModelChange() {
		mViewModel.setValue(this);
	}

}
