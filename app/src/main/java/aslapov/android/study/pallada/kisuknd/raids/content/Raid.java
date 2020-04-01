package aslapov.android.study.pallada.kisuknd.raids.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Raid {
    // Идентификатор рейдового осмотра
    @SerializedName("Id")
    @Expose
    private UUID id;

    // Вид транспорта
    @SerializedName("TransportType")
    @Expose
    private String transportType;

    // Отдел
    @SerializedName("Department")
    @Expose
    private String department;

    // № акта
    @SerializedName("ActNumber")
    @Expose
    private String actNumber;

    // Дата (время) начала проведения рейдового осмотра
    @SerializedName("RealStart")
    @Expose
    private Date realStart;

    // Дата (время) окончания проведения рейдового осмотра
    @SerializedName("RealEnd")
    @Expose
    private Date realEnd;

    // Место проведения (адрес)
    @SerializedName("PlaceAddress")
    @Expose
    private String placeAddress;

    // № распоряжения
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;

    // Дата распоряжения
    @SerializedName("OrderDate")
    @Expose
    private Date orderDate;

    // № задания
    @SerializedName("TaskNumber")
    @Expose
    private String taskNumber;

    // Дата задания
    @SerializedName("TaskDate")
    @Expose
    private Date taskDate;

    // Информация о ТС (наименование, марка, модель и т.д.)
    @SerializedName("VehicleInfo")
    @Expose
    private String vehicleInfo;

    // Собственник/Перевозчик
    @SerializedName("VehicleOwner")
    @Expose
    private String vehicleOwner;

    // ОГРН собственника
    @SerializedName("OwnerOgrn")
    @Expose
    private String ownerOgrn;

    // ИНН собственника
    @SerializedName("OwnerInn")
    @Expose
    private String ownerInn;

    // Есть ли нарушения
    @SerializedName("ViolationsPresence")
    @Expose
    private boolean violationsPresence;

    // Количество вынесенных предостережений
    // Каждый осмотр ТС может приводить к вынесению нескольких предостережений
    @SerializedName("WarningCount")
    @Expose
    private int warningCount;

    // Дата вынесенного(ых) предостережения(й)
    @SerializedName("WarningDate")
    @Expose
    private Date warningDate;

    // Часовой пояс в формате ±HHmm
    @SerializedName("TimeZone")
    @Expose
    private String timeZone;

    // Уполномоченные на проведение проверки
    @SerializedName("Inspectors")
    @Expose
    private List<String> inspectors;

    // Вложенные файлы
    //public List<WebAttachment> Attachments { get; set; }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getActNumber() {
        return actNumber;
    }

    public void setActNumber(String actNumber) {
        this.actNumber = actNumber;
    }

    public Date getRealStart() {
        return realStart;
    }

    public void setRealStart(Date realStart) {
        this.realStart = realStart;
    }

    public Date getRealEnd() {
        return realEnd;
    }

    public void setRealEnd(Date realEnd) {
        this.realEnd = realEnd;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public String getVehicleOwner() {
        return vehicleOwner;
    }

    public void setVehicleOwner(String vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    public String getOwnerOgrn() {
        return ownerOgrn;
    }

    public void setOwnerOgrn(String ownerOgrn) {
        this.ownerOgrn = ownerOgrn;
    }

    public String getOwnerInn() {
        return ownerInn;
    }

    public void setOwnerInn(String ownerInn) {
        this.ownerInn = ownerInn;
    }

    public boolean isViolationsPresence() {
        return violationsPresence;
    }

    public void setViolationsPresence(boolean violationsPresence) {
        this.violationsPresence = violationsPresence;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public Date getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(Date warningDate) {
        this.warningDate = warningDate;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<String> getInspectors() {
        return inspectors;
    }

    public void setInspectors(List<String> inspectors) {
        this.inspectors = inspectors;
    }
}
