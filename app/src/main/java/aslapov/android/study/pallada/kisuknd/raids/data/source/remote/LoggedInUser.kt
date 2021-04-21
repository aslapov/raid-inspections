package aslapov.android.study.pallada.kisuknd.raids.data.source.remote

import com.google.gson.annotations.SerializedName

data class LoggedInUser(
        @SerializedName("userId")
        var username: String,

        var password: String
)
