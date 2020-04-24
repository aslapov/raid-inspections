package aslapov.android.study.pallada.kisuknd.raids.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "RaidInspections")
public class RaidEntity {

    @PrimaryKey
    @ColumnInfo(name = "Id")
    public UUID id;

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

    // Дата создания записи
    @ColumnInfo(name = "CreateDate")
    private Date createDate;

    // Дата редактирования записи
    @ColumnInfo(name = "UpdateDate")
    private Date updateDate;
}
