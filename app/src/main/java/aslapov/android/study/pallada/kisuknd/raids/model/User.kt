package aslapov.android.study.pallada.kisuknd.raids.model

data class User(
        val id: String?,
        val firstName: String,
        val lastName: String?,
        val displayName: String?,
        val email: String?,
        val jobTitle: String?,
        val departments: List<String>?
)
