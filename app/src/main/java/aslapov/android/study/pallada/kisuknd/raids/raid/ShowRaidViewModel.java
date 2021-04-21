package aslapov.android.study.pallada.kisuknd.raids.raid;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseViewModel;
import aslapov.android.study.pallada.kisuknd.raids.data.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.Raid;
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.RaidStatus;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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

	public LiveData<ShowRaidViewModel> getViewModelObserver() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	public void requestRaid(UUID raidId) {
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

	public boolean isRaidReceived() {
		return mRaidWithInspectors.getValue() != null;
	}

	public boolean isDraft() {
		return getRaid().getRaid().getStatus() == 1;
	}

	public RaidWithInspectors getRaid() {
		RaidWithInspectors raidInspection = mRaidWithInspectors.getValue();
		if (raidInspection == null)
			throw new IllegalStateException();
		return raidInspection;
	}

	public RaidStatus getRaidStatus() {
		RaidWithInspectors raidInspection = getRaid();
		int status = raidInspection.getRaid().getStatus();
		return RaidStatus.values()[status];
	}

	public void sendRaidDraft(RaidWithInspectors raidInspection) {
		Raid raid = raidInspection.getRaid();
		raid.setStatus(RaidStatus.OUTGOING.ordinal());
		raidInspection.setRaid(raid);

		Completable.fromAction(() -> mRaidRepository.updateRaid(raidInspection))
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

	public String getShowError() {
		return mShowError;
	}

	public void send() {
		updateWithRaidsStatus(RaidStatus.OUTGOING);
	}

	public void cancelSending() {
		updateWithRaidsStatus(RaidStatus.DRAFT);
	}

	public void moveToTrash() {
		updateWithRaidsStatus(RaidStatus.TRASH);
	}

	public void restore() {
		updateWithRaidsStatus(RaidStatus.DRAFT);
	}

	private void notifyViewModelChange() {
		mViewModel.setValue(this);
	}

	private void updateWithRaidsStatus(RaidStatus status) {
		RaidWithInspectors raidInspection = getRaid();

		Raid raid = raidInspection.getRaid();
		raid.setStatus(status.ordinal());
		raidInspection.setRaid(raid);

		Completable.fromAction(() -> mRaidRepository.updateRaid(raidInspection))
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
