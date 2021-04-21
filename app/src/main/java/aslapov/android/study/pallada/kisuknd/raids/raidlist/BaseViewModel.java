package aslapov.android.study.pallada.kisuknd.raids.raidlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public interface BaseViewModel {

	LiveData<? extends ViewModel> getViewModelObserver();
}
