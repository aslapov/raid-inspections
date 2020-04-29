package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;

public class RaidViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    public RaidViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    public LiveData<RaidWithInspectors> getRaid(UUID raidId) {
        return mRaidRepository.queryRaidListById(raidId);
    }
}
