package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RaidListViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    private MutableLiveData<RaidListViewModel> mViewModel = new MutableLiveData<>();
    private MutableLiveData<List<RaidWithInspectors>> mRaids = new MutableLiveData<>();
    private String mShowError;

    RaidListViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    public MutableLiveData<RaidListViewModel> getViewModel() {
        return mViewModel;
    }

    @SuppressLint("CheckResult")
    public void getRaidList(boolean isDraft) {
        mRaidRepository.queryRaids(isDraft)
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
