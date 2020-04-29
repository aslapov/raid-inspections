package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "RaidInspections")
public class RaidEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Id")
    private String id;

    // Вид транспорта
    @ColumnInfo(name = "TransportType")
    private String transportType;

    // Отдел
    @ColumnInfo(name = "Department")
    private String department;

    // № акта
    @ColumnInfo(name = "ActNumber")
    private String actNumber;

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

    // Часовой пояс в формате ±HHmm
    @ColumnInfo(name = "TimeZone")
    private String timeZone;

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
}
