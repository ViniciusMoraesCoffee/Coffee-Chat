package coffee.company.coffeechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var InittuserId: String
private lateinit var Inittname:String
private lateinit var Inittnickname:String

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_loading)

//        val auth = FirebaseAuth.getInstance()
//        val db = Firebase.firestore
//        val colRefUsers = db.collection("users")
//        val userData = UserData()
//        if (auth.currentUser?.uid != null) {
//            InittuserId = auth.currentUser?.uid.toString()
//            colRefUsers.document(userData.userId).get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//                        Inittname = document.getString("name").toString()
//                        Inittnickname = document.getString("nickname").toString()
//
//                        userData.userId = InittuserId
//                        userData.name = Inittname
//                        userData.nickname = Inittnickname
//
//                    }
//                }
//
//        }
//        else {
//            startActivity(Intent(this, SignUpActivity::class.java))
//        }
        startActivity(Intent(this, SignInActivity::class.java))
    }
}

