package aslapov.android.study.pallada.kisuknd.raids.model.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attachment {

    // Имя файла
    @SerializedName("FileName")
    @Expose
    public String fileName;

    // Размер файла
    @SerializedName("Size")
    @Expose
    public int size;
}
