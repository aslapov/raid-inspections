package aslapov.android.study.pallada.kisuknd.raids.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "RaidInspections")
public class Raid {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Id")
    private String id;

    // Статус рейдового осмотра
    // 0 - "Входящие"/"Из сервера"/"Из центрального хранилища"
    // 1 - "Черновики"
    // 2 - "Исходящие"
    @ColumnInfo(name = "Status")
    private Integer status;

    // Отдел
    @ColumnInfo(name = "Department")
    private String department;

    // Вид транспорта
    @ColumnInfo(name = "TransportType")
    private String transportType;

    // № акта
    @ColumnInfo(name = "ActNumber")
    private String actNumber;

    // Дата акта
    @ColumnInfo(name = "ActDate")
    private Date actDate;

    // Дата (время) начала проведения рейдового осмотра
    @ColumnInfo(name = "RealStart")
    private Date realStart;

    // Дата (время) окончания проведения рейдового осмотра
    @ColumnInfo(name = "RealEnd")
    private Date realEnd;

    // Место проведения (адрес)
    @ColumnInfo(name = "PlaceAddress")
    private String placeAddress;

    // № распоряжения
    @ColumnInfo(name = "OrderNumber")
    private String orderNumber;

    // Дата распоряжения
    @ColumnInfo(name = "OrderDate")
    private Date orderDate;

    // № задания
    @ColumnInfo(name = "TaskNumber")
    private String taskNumber;

    // Дата задания
    @ColumnInfo(name = "TaskDate")
    private Date taskDate;

    // Информация о ТС (наименование, марка, модель и т.д.)
    @ColumnInfo(name = "VehicleInfo")
    private String vehicleInfo;

    // Собственник/Перевозчик
    @ColumnInfo(name = "VehicleOwner")
    private String vehicleOwner;

    // ОГРН собственника
    @ColumnInfo(name = "OwnerOgrn")
    private String ownerOgrn;

    // ИНН собственника
    @ColumnInfo(name = "OwnerInn")
    private String ownerInn;

    // Есть ли нарушения
    @ColumnInfo(name = "ViolationsPresence")
    private boolean violationsPresence;

    // Количество вынесенных предостережений
    // Каждый осмотр ТС может приводить к вынесению нескольких предостережений
    @ColumnInfo(name = "WarningCount")
    private int warningCount;

    // Дата вынесенного(ых) предостережения(й)
    @ColumnInfo(name = "WarningDate")
    private Date warningDate;

    // Часовой пояс в формате мс (+0300 = 10800000мс)
    @ColumnInfo(name = "TimeZone")
    private Integer timeZone;

    // Дата создания записи
    @ColumnInfo(name = "CreateDate")
    private Date createDate;

    // Дата изменения записи
    @ColumnInfo(name = "UpdateDate")
    private Date updateDate;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
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

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @NonNull
    public Integer getStatus() {
        return status;
    }

    public void setStatus(@NonNull Integer status) {
        this.status = status;
    }
}
