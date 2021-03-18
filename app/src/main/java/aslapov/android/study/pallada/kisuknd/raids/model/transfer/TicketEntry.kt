package aslapov.android.study.pallada.kisuknd.raids.model.transfer

import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Ticket
import com.google.gson.annotations.SerializedName

data class TicketEntry(
        @SerializedName("entry")
        val ticket: Ticket
)
