package aslapov.android.study.pallada.kisuknd.raids.model.transfer

data class Pagination(
        val count: Number,
        val hasMoreItems: Boolean,
        val totalItems: Number,
        val skipCount: Number,
        val maxItems: Number
)
