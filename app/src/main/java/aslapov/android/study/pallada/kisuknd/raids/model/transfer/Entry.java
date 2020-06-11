package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entry {

	@SerializedName("entry")
	@Expose
	private InspectionMessage inspectionMessage;

	public InspectionMessage getInspectionMessage() {
		return inspectionMessage;
	}

	public void setInspectionMessage(InspectionMessage inspectionMessage) {
		this.inspectionMessage = inspectionMessage;
	}
}
