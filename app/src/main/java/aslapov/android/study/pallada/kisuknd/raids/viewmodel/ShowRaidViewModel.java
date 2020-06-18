package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShowRaidViewModel extends ViewModel implements BaseViewModel {

	private RaidRepository mRaidRepository;

	private MutableLiveData<ShowRaidViewModel> mViewModel = new MutableLiveData<>();
	private MutableLiveData<RaidWithInspectors> mRaidWithInspectors = new MutableLiveData<>();
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

	public LiveData<ShowRaidViewModel> getViewModel() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	public void getRaid(UUID raidId) {
		getRaidRepository()
				.queryRaidById(raidId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						raid -> {
							mRaidWithInspectors.setValue(raid);
							notifyViewModelChange();
						},
						error -> {
							mShowError = error.getMessage();
							notifyViewModelChange();
						}
				);
	}

	public RaidWithInspectors getRaidWithInspectors() {
		return mRaidWithInspectors.getValue();
	}

	public String getShowError() {
		return mShowError;
	}

	private void notifyViewModelChange() {
		mViewModel.setValue(this);
	}
}
