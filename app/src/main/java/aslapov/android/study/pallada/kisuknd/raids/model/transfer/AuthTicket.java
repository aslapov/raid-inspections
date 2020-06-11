package aslapov.android.study.pallada.kisuknd.raids.model.transfer;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthTicket {

    public class AuthData {
        @SerializedName("ticket")
        @Expose
        private String ticket;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(@Nullable String ticket) {
            this.ticket = ticket;
        }
    }

    @SerializedName("data")
    @Expose
    private AuthData data;

    public AuthData getData() {
        return data;
    }

    public void setData(AuthData data) {
        this.data = data;
    }
}
