package aslapov.android.study.pallada.kisuknd.raids.raidlist.outgoing;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.raidlist.BaseListViewModel;
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

public class RaidOutgoingListViewModel extends ViewModel implements BaseListViewModel {

	// Запрашиваемый статус рейдовых осмотров
	// "2" - "Исходящие"
	private static final Integer sStatus = RaidStatus.OUTGOING.ordinal();

	private RaidRepository mRaidRepository;

	private MutableLiveData<RaidOutgoingListViewModel> mViewModel = new MutableLiveData<>();
	private MutableLiveData<List<RaidWithInspectors>> mRaids = new MutableLiveData<>();
	private String mShowError;

	// Выбранный для отображения элемент списка. -1 - элемент не выбран
	private int mSelectedItemPosition = 0;

	// Наступило ли событие изменения количества осмотров ТС в списке.
	// Нужно для логичной работы представления: например, если количество изменилось,
	// необходимо для 2хпанельного представления показать следующий фрагмент осмотра ТС
	// и снять выделение выбранного элемента списка.
	// Если количество не изменилось, выбрать первый или ранее выбранный элемент списка
	private boolean mIsRaidListSizeChanged = false;

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
	public MutableLiveData<RaidOutgoingListViewModel> getViewModelObserver() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	@Override
	public void getRaids() {
		getRaidRepository()
				.queryRaids(sStatus)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						raids -> {
							mIsRaidListSizeChanged = mRaids.getValue() != null && mRaids.getValue().size() != raids.size();

							Collections.sort(raids, (raid1, raid2) -> {
								long start1 = raid1.getRaid().getRealStart().getTime();
								long start2 = raid2.getRaid().getRealStart().getTime();
								if (start1 == start2)
									return 0;
								return (start1 > start2) ? -1 : 1;
							});

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
	public List<RaidWithInspectors> getRaidList() {
		return mRaids.getValue();
	}

	@Override
	public int getSelectedItemPosition() {
		return mSelectedItemPosition;
	}

	@Override
	public void setSelectedItemPosition(int value) {
		mSelectedItemPosition = value;
	}

	@Override
	public boolean isRaidListSizeChanged() {
		return mIsRaidListSizeChanged;
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
		List<RaidWithInspectors> raidInspections = getRaidList();

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
