package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.content.Context;

import aslapov.android.study.pallada.kisuknd.raids.R;

public class ResourcesProvider {
	private Context mContext;

	public ResourcesProvider(Context context) {
		mContext = context;
	}

	public String[] getDepartmentStringArray() {
		return mContext.getResources().getStringArray(R.array.department);
	}

	public String[] getTransportTypeStringArray() {
		return mContext.getResources().getStringArray(R.array.transport_type);
	}
}
