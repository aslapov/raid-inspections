package aslapov.android.study.pallada.kisuknd.raids.raidlist;

import aslapov.android.study.pallada.kisuknd.raids.data.source.local.RaidWithInspectors;

public interface OnRaidShowListener {
	void onRaidSelected(RaidWithInspectors raid);

	void onRaidEmpty();
}
