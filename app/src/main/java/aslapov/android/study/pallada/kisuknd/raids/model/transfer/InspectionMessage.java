package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class InspectionMessage {

	@SerializedName("id")
	@Expose
	private UUID id;

	@SerializedName("parentId")
	@Expose
	private UUID parentId;

	@SerializedName("createdAt")
	@Expose
	private Date createdAt;

	@SerializedName("modifiedAt")
	@Expose
	private Date modifiedAt;

	@SerializedName("createdByUser")
	@Expose
	private User createdByUser;

	@SerializedName("modifiedByUser")
	@Expose
	private User modifiedByUser;

	@SerializedName("properties")
	@Expose
	private Inspection inspection;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public User getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(User modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
}
