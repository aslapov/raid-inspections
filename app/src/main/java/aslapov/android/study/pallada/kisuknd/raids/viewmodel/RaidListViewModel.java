package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;

public class RaidListViewModel extends ViewModel {

    private MutableLiveData<List<Raid>> raids;
    public LiveData<List<Raid>> getRaids() {
        if (raids == null) {
            raids = new MutableLiveData<>();
            loadRaids();
        }
        return raids;
    }

    private void loadRaids() {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.queryRaids(new RaidRepository.ResponseCallback<List<Raid>>() {
            @Override
            public void onResponse(List<Raid> result) {
                raids.setValue(result);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
