package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class Inspection {

	@SerializedName("knd:inspectionBriefDescription")
	@Expose
	private String briefDescription;

	@SerializedName("knd:inspectionPhase")
	@Expose
	private String inspectionPhase;

	@SerializedName("knd:inspectionHead")
	@Expose
	private String inspectionHead;

	@SerializedName("knd:inspectionType")
	@Expose
	private String inspectionType;

	@SerializedName("knd:inspectionCompletionCode")
	@Expose
	private Integer inspectionCompletionCode;

	@SerializedName("knd:inspectionReason")
	@Expose
	private String inspectionReason;

	@SerializedName("knd:inspectionAim")
	@Expose
	private String inspectionAim;

	@SerializedName("knd:inspectionPlanStartDate")
	@Expose
	private Date inspectionPlanStartDate;

	@SerializedName("knd:inspectionRealEndDate")
	@Expose
	private Date inspectionRealEndDate;

	@SerializedName("knd:inspectionCategory")
	@Expose
	private String inspectionCategory;

	@SerializedName("knd:inspectionTransport")
	@Expose
	private String inspectionTransport;

	@SerializedName("knd:inspectionChairman")
	@Expose
	private String inspectionChairman;

	@SerializedName("knd:inspectionStatus")
	@Expose
	private Integer inspectionStatus;

	@SerializedName("knd:inspectionPlanEndDate")
	@Expose
	private Date inspectionPlanEndDate;

	@SerializedName("knd:inspectionRealStartDate")
	@Expose
	private Date inspectionRealStartDate;

	@SerializedName("knd:inspectionRFSubject")
	@Expose
	private String inspectionRFSubject;

	@SerializedName("knd:inspectionIniciator")
	@Expose
	private String inspectionIniciator;

	@SerializedName("knd:inspectionView")
	@Expose
	private Integer inspectionView;

	public String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public String getInspectionPhase() {
		return inspectionPhase;
	}

	public void setInspectionPhase(String inspectionPhase) {
		this.inspectionPhase = inspectionPhase;
	}

	public String getInspectionHead() {
		return inspectionHead;
	}

	public void setInspectionHead(String inspectionHead) {
		this.inspectionHead = inspectionHead;
	}

	public String getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public Integer getInspectionCompletionCode() {
		return inspectionCompletionCode;
	}

	public void setInspectionCompletionCode(Integer inspectionCompletionCode) {
		this.inspectionCompletionCode = inspectionCompletionCode;
	}

	public String getInspectionReason() {
		return inspectionReason;
	}

	public void setInspectionReason(String inspectionReason) {
		this.inspectionReason = inspectionReason;
	}

	public String getInspectionAim() {
		return inspectionAim;
	}

	public void setInspectionAim(String inspectionAim) {
		this.inspectionAim = inspectionAim;
	}

	public Date getInspectionPlanStartDate() {
		return inspectionPlanStartDate;
	}

	public void setInspectionPlanStartDate(Date inspectionPlanStartDate) {
		this.inspectionPlanStartDate = inspectionPlanStartDate;
	}

	public Date getInspectionRealEndDate() {
		return inspectionRealEndDate;
	}

	public void setInspectionRealEndDate(Date inspectionRealEndDate) {
		this.inspectionRealEndDate = inspectionRealEndDate;
	}

	public String getInspectionCategory() {
		return inspectionCategory;
	}

	public void setInspectionCategory(String inspectionCategory) {
		this.inspectionCategory = inspectionCategory;
	}

	public String getInspectionTransport() {
		return inspectionTransport;
	}

	public void setInspectionTransport(String inspectionTransport) {
		this.inspectionTransport = inspectionTransport;
	}

	public String getInspectionChairman() {
		return inspectionChairman;
	}

	public void setInspectionChairman(String inspectionChairman) {
		this.inspectionChairman = inspectionChairman;
	}

	public Integer getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(Integer inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public Date getInspectionPlanEndDate() {
		return inspectionPlanEndDate;
	}

	public void setInspectionPlanEndDate(Date inspectionPlanEndDate) {
		this.inspectionPlanEndDate = inspectionPlanEndDate;
	}

	public Date getInspectionRealStartDate() {
		return inspectionRealStartDate;
	}

	public void setInspectionRealStartDate(Date inspectionRealStartDate) {
		this.inspectionRealStartDate = inspectionRealStartDate;
	}

	public String getInspectionRFSubject() {
		return inspectionRFSubject;
	}

	public void setInspectionRFSubject(String inspectionRFSubject) {
		this.inspectionRFSubject = inspectionRFSubject;
	}

	public String getInspectionIniciator() {
		return inspectionIniciator;
	}

	public void setInspectionIniciator(String inspectionIniciator) {
		this.inspectionIniciator = inspectionIniciator;
	}

	public Integer getInspectionView() {
		return inspectionView;
	}

	public void setInspectionView(Integer inspectionView) {
		this.inspectionView = inspectionView;
	}
}
