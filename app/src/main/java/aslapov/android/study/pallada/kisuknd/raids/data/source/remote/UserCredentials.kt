package aslapov.android.study.pallada.kisuknd.raids.data.source.remote

import com.google.gson.annotations.SerializedName

data class UserCredentials(
        @SerializedName("userId")
        var username: String,

        var password: String
)