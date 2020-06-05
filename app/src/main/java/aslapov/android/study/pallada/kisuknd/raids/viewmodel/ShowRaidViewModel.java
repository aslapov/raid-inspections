package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShowRaidViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    private MutableLiveData<ShowRaidViewModel> mViewModel = new MutableLiveData<>();
    private MutableLiveData<RaidWithInspectors> mRaidWithInspectors = new MutableLiveData<>();
    private String mShowError;

    ShowRaidViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    public LiveData<ShowRaidViewModel> getViewModel() {
        return mViewModel;
    }

    @SuppressLint("CheckResult")
    public void getRaid(UUID raidId) {
        mRaidRepository.queryRaidById(raidId)
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
