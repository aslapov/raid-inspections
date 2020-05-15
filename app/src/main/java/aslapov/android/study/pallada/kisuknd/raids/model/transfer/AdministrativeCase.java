package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class AdministrativeCase {
    // Идентификатор рейдового осмотра
    @SerializedName("RaidId")
    @Expose
    private UUID raidId;

    // Номер административного дела
    @SerializedName("CaseNumber")
    @Expose
    private String caseNumber;

    // Статья КоАП
    @SerializedName("Clause")
    @Expose
    public String clause;

    // Номер протокола
    @SerializedName("ProtocolNumber")
    @Expose
    public String protocolNumber;

    // Место составления протокола
    @SerializedName("ProtocolAddress")
    @Expose
    public String protocolAddress;

    // Дата протокола
    @SerializedName("ProtocolDate")
    @Expose
    public Date protocolDate;

    // Номер постановления
    @SerializedName("RulingNumber")
    @Expose
    public String rulingNumber;

    // Место составления постановления
    @SerializedName("RulingAddress")
    @Expose
    public String rulingAddress;

    // Дата постановления
    @SerializedName("RulingDate")
    @Expose
    public Date rulingDate;

    // Номер определения
    @SerializedName("DefinitionNumber")
    @Expose
    public String definitionNumber;

    // Место составления определения
    @SerializedName("DefinitionAddress")
    @Expose
    public String definitionAddress;

    // Дата определения
    @SerializedName("DefinitionDate")
    @Expose
    public Date definitionDate;
}
