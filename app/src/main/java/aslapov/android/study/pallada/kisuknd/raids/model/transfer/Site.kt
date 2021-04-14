package aslapov.android.study.pallada.kisuknd.raids.model.transfer

data class SiteEntry(
        val entry: Site
)

data class SiteRoleEntry(
        val entry: SiteRole
)

data class SiteRole(
        val id: String,
        val guid: String,
        val role: String,
        val site: Site
)

data class Site(
        val id: String,
        val guid: String,
        val title: String,
        val description: String,
        val visibility: String,
        val preset: String?,
        val role: String?
)

data class SiteRolePaging(
        val list: SiteRolePagingList
)

data class SiteRolePagingList(
        val pagination: Pagination,
        val entries: List<SiteRoleEntry>
)

data class SitePaging(
        val list: SitePagingList
)

data class SitePagingList(
        val pagination: Pagination,
        val entries: List<SiteEntry>
)

data class SiteContainerPaging(
        val list: SiteContainerPagingList
)

data class SiteContainerPagingList(
        val pagination: Pagination,
        val entries: List<SiteContainerEntry>
)

data class SiteContainerEntry(
        val entry: SiteContainer
)

data class SiteContainer(
        val id: String,
        val folderId: String
)

data class SiteMemberPaging(
        val list: SiteMemberPagingList
)

data class SiteMemberPagingList(
        val pagination: Pagination,
        val entries: List<SiteMemberEntry>
)

data class SiteMemberEntry(
        val entry: SiteMember
)

data class SiteMember(
        val id: String,
        val person: Person,
        val role: String
)