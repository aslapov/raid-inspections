package aslapov.android.study.pallada.kisuknd.raids.content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoggedInUser {

    @SerializedName("Name")
    @Expose
    private String userName;

    @SerializedName("Password")
    @Expose
    private String password;

    public LoggedInUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
