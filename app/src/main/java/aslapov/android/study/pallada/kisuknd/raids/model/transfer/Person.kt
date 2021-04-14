package aslapov.android.study.pallada.kisuknd.raids.model.transfer

data class PersonEntry(
        val entry: Person
)

data class Company(
        val organization: String?,
        val address1: String?,
        val address2: String?,
        val address3: String?,
        val postcode: String?,
        val telephone: String?,
        val fax: String?,
        val email: String?
)

data class Person(
        val id: String?,
        val firstName: String,
        val lastName: String?,
        val displayName: String?,
        val email: String?,
        val emailNotificationsEnabled: Boolean?,
        val enabled: Boolean,
        val company: Company?,
        val description: String?,
        val jobTitle: String?,
        val location: String?,
        val mobile: String?,
        val telephone: String?,
        val statusUpdatedAt: String?,
        val userStatus: String?,
        val aspectNames: List<String>?,
        val capabilities: Any,
        val properties: Any
)

