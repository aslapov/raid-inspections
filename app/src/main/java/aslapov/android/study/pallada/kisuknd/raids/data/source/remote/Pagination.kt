package aslapov.android.study.pallada.kisuknd.raids.data.source.remote

data class Pagination(
        val count: Number,
        val hasMoreItems: Boolean,
        val totalItems: Number,
        val skipCount: Number,
        val maxItems: Number
)
