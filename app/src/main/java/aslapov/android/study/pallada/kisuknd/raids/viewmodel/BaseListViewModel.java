package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import java.util.List;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

public interface BaseListViewModel extends BaseViewModel {

	void getRaidList();
	String getShowError();
	List<RaidWithInspectors> getRaids();
}
