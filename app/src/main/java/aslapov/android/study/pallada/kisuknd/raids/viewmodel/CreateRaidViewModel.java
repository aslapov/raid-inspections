package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

public class CreateRaidViewModel extends ViewModel {

    private RaidRepository mRaidRepository;

    public CreateRaidViewModel(Context applicationContext) {
        mRaidRepository = RepositoryProvider.provideRaidRepository(applicationContext);
    }

    public void createRaid(RaidWithInspectors raid) {
        mRaidRepository.addRaid(raid);
    }
}
