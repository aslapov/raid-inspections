package aslapov.android.study.pallada.kisuknd.raids.model

import com.google.gson.annotations.SerializedName

data class LoggedInUser(
        @SerializedName("userId")
        var username: String,

        var password: String
)
