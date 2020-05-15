package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @Nullable
    @SerializedName("UserName")
    @Expose
    private String userName;

    @Nullable
    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;

    @SerializedName("Succeeded")
    @Expose
    private boolean succeeded;

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    @Nullable
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(@Nullable Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
}
