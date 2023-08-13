package coffee.company.coffeechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_loading)

        val tag = "LOADINGLOG"

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        if (auth.currentUser != null) {
            Log.d(tag, auth.currentUser.toString())
            val colRefUsers = db.collection("users")
            val repository  = DataRepository.getInstance()
            val id = auth.currentUser?.uid.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("HH:mm dd MMM yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            colRefUsers.document(id).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name").toString()
                        val nickname = document.getString("nickname").toString()

                        repository.setUserInfo(id, name, nickname)
                        repository.setUserData(formattedDate)
                    }
                }
                .addOnCompleteListener {
                    val (userId, userName, userNickname) = repository.getUserInfo()
                    Log.i(tag, userId)
                    Log.i(tag, userName)
                    Log.i(tag, userNickname)
                    Log.i(tag, repository.getUserData().toString())
                    startActivity(Intent(this, PageHomeActivity::class.java))
                    finish()
                }
        }
        else {
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }
}

