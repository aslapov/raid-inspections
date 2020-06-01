package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context mApplication;

    public ViewModelFactory(Context applicationContext) {
        mApplication = applicationContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class))
            return (T) new AuthViewModel(mApplication);

        if (modelClass.isAssignableFrom(RaidListViewModel.class))
            return (T) new RaidListViewModel(mApplication);

        if (modelClass.isAssignableFrom(ShowRaidViewModel.class))
            return (T) new ShowRaidViewModel(mApplication);

        if (modelClass.isAssignableFrom(CreateRaidViewModel.class))
            return (T) new CreateRaidViewModel(mApplication);

        if (modelClass.isAssignableFrom(EditRaidViewModel.class))
            return (T) new EditRaidViewModel(mApplication);

        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
