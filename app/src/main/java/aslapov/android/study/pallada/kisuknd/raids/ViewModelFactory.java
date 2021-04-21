package aslapov.android.study.pallada.kisuknd.raids;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import aslapov.android.study.pallada.kisuknd.raids.createraid.CreateRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.editraid.EditRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.data.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.raid.ShowRaidViewModel;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.draft.RaidDraftListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.outgoing.RaidOutgoingListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.remote.RaidListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.raidlist.trash.RaidTrashListViewModel;
import aslapov.android.study.pallada.kisuknd.raids.util.ResourcesProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context mApplication;

    public ViewModelFactory(Context applicationContext) {
        mApplication = applicationContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RaidListViewModel.class))
            return (T) createRaidListViewModel();

        if (modelClass.isAssignableFrom(RaidDraftListViewModel.class))
            return (T) createRaidDraftListViewModel();

        if (modelClass.isAssignableFrom(RaidOutgoingListViewModel.class))
            return (T) createRaidOutgoingListViewModel();

        if (modelClass.isAssignableFrom(RaidTrashListViewModel.class))
            return (T) createRaidTrashListViewModel();

        if (modelClass.isAssignableFrom(ShowRaidViewModel.class))
            return (T) createShowRaidViewModel();

        if (modelClass.isAssignableFrom(CreateRaidViewModel.class))
            return (T) createCreateRaidViewModel();

        if (modelClass.isAssignableFrom(EditRaidViewModel.class))
            return (T) createEditRaidViewModel();

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    private RaidListViewModel createRaidListViewModel() {
        RaidListViewModel instance = new RaidListViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }

    private RaidDraftListViewModel createRaidDraftListViewModel() {
        RaidDraftListViewModel instance = new RaidDraftListViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }

    private RaidOutgoingListViewModel createRaidOutgoingListViewModel() {
        RaidOutgoingListViewModel instance = new RaidOutgoingListViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }

    private RaidTrashListViewModel createRaidTrashListViewModel() {
        RaidTrashListViewModel instance = new RaidTrashListViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }

    private ShowRaidViewModel createShowRaidViewModel() {
        ShowRaidViewModel instance = new ShowRaidViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }

    private CreateRaidViewModel createCreateRaidViewModel() {
        CreateRaidViewModel instance = new CreateRaidViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        instance.setResourcesProvider(new ResourcesProvider(mApplication));
        return instance;
    }

    private EditRaidViewModel createEditRaidViewModel() {
        EditRaidViewModel instance = new EditRaidViewModel();
        instance.setRaidRepository(RepositoryProvider.provideRaidRepository(mApplication));
        return instance;
    }
}
