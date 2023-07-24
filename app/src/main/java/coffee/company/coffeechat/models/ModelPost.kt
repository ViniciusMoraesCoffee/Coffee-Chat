package coffee.company.coffeechat.models

data class ModelPost (
    var idMessage: String,
    var idAuthor: String,
    var nameAuthor: String,
    var nicknameAuthor: String,
    var textPost: String,
    var dataPost: String
)