package coffee.company.coffeechat

class DataRepository private constructor() {

    private var userId: String = ""
    private var userName: String = ""
    private var userNickname: String = ""
    private var userData: Any? = null

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository().also { instance = it }
            }
    }

    fun setUserInfo(id: String, name: String, nickname: String) {
        userId = id
        userName = name
        userNickname = nickname
    }

    fun getUserInfo(): Triple<String, String, String> {
        return Triple(userId, userName, userNickname)
    }

    fun setUserData(data: Any?) {
        userData = data
    }

    fun getUserData(): Any? {
        return userData
    }
}
