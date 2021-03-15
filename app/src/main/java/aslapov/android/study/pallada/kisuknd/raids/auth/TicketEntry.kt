package aslapov.android.study.pallada.kisuknd.raids.auth

import com.google.gson.annotations.SerializedName

data class TicketEntry(
        @SerializedName("entry")
        val ticket: Ticket
)
