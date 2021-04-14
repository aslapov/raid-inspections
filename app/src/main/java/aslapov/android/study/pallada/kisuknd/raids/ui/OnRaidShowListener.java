package aslapov.android.study.pallada.kisuknd.raids.ui;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;

public interface OnRaidShowListener {
	void onRaidSelected(RaidWithInspectors raid);

	void onRaidEmpty();
}
