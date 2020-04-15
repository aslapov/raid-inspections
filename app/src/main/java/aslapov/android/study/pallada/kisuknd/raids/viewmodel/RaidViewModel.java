package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;

public class RaidViewModel extends ViewModel {

    private MutableLiveData<Raid> raid;
    public LiveData<Raid> getRaid(UUID raidId) {
        if (raid == null) {
            raid = new MutableLiveData<>();
            loadRaid(raidId);
        }
        return raid;
    }

    private void loadRaid(UUID raidId) {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaidById(raidId, new RaidRepository.ResponseCallback<Raid>() {
            @Override
            public void onResponse(Raid result) {
                raid.setValue(result);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
