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

public class RaidListViewModel extends ViewModel implements BaseListViewModel {

	// Запрашиваемый статус рейдовых осмотров
	// "0" - "Входящие"/"Из сервера"/"Из центрального хранилища"
	private static final Integer sStatus = RaidStatus.FROMSERVER.ordinal();

	private RaidRepository mRaidRepository;

	private MutableLiveData<RaidListViewModel> mViewModel = new MutableLiveData<>();
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
	public MutableLiveData<RaidListViewModel> getViewModel() {
		return mViewModel;
	}

	@SuppressLint("CheckResult")
	@Override
	public void getRaidList() {
		//mRaidRepository.getRaid(UUID.fromString("e1650b76-56f9-43de-b005-a2baa3142ba1"));

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

	private void notifyViewModelChange() {
		mViewModel.setValue(this);
	}
}
