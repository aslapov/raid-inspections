package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RaidOutgoingListViewModel extends ViewModel implements BaseListViewModel {

	// Запрашиваемый статус рейдовых осмотров
	// "2" - "Исходящие"
	private static final Integer sStatus = RaidStatus.OUTGOING.ordinal();

	private RaidRepository mRaidRepository;

	private MutableLiveData<RaidOutgoingListViewModel> mViewModel = new MutableLiveData<>();
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

	@Override
	public MutableLiveData<RaidOutgoingListViewModel> getViewModel() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	@Override
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

	@Override
	public List<RaidWithInspectors> getRaids() {
		return mRaids.getValue();
	}

	@Override
	public String getShowError() {
		return mShowError;
	}

	public void cancelSending() {
		updateWithRaidsStatus(RaidStatus.DRAFT);
	}

	private void notifyViewModelChange() {
		mViewModel.setValue(this);
	}

	private void updateWithRaidsStatus(RaidStatus status) {
		List<RaidWithInspectors> raidInspections = getRaids();

		for (RaidWithInspectors raidInspection : raidInspections) {
			Raid raid = raidInspection.getRaid();
			raid.setStatus(status.ordinal());
			raidInspection.setRaid(raid);
		}

		Completable.fromAction(() -> mRaidRepository.updateRaids(raidInspections))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe(new CompletableObserver() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {
					}

					@Override
					public void onComplete() {
					}

					@Override
					public void onError(@NonNull Throwable e) {
						mShowError = e.getMessage();
						notifyViewModelChange();
					}
				});
	}
}
