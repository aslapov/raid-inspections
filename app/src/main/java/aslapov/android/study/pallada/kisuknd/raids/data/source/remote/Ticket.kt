package aslapov.android.study.pallada.kisuknd.raids.data.source.remote

import com.google.gson.annotations.SerializedName

data class Ticket(val id: String, val userId: String)

data class TicketEntry(
        @SerializedName("entry")
        val ticket: Ticket
)
