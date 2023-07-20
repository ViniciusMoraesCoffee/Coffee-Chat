package coffee.company.coffeechat.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

data class Post(
    val nameUser: String,
    val textPost: String
)