package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public interface BaseViewModel {

	LiveData<? extends ViewModel> getViewModel();
}
