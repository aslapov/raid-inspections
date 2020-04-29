package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;

public class RaidListViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    public RaidListViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    private MutableLiveData<List<RaidWithInspectors>> raids;
    public LiveData<List<RaidWithInspectors>> getRaids() {
        if (raids == null) {
            raids = new MutableLiveData<>();
            raids.postValue(mRaidRepository.queryRaids());
        }
        return raids;
    }
}
