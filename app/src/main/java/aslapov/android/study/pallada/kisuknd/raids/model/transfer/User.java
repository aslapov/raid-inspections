package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("displayName")
	@Expose
	private String displayName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
