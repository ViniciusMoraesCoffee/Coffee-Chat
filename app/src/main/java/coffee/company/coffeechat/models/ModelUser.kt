package coffee.company.coffeechat.models

data class ModelUser(
    val id: String,
    val name: String,
    val nickname: String,
    val messagesId: Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModelUser

        if (id != other.id) return false
        if (name != other.name) return false
        if (nickname != other.nickname) return false
        if (!messagesId.contentEquals(other.messagesId)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + messagesId.contentHashCode()
        return result
    }
}