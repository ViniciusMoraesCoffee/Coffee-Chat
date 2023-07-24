package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import coffee.company.coffeechat.databinding.ActCreatorPostBinding
import coffee.company.coffeechat.models.ModelPost
import coffee.company.coffeechat.myvariables.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatorPostActivity : AppCompatActivity() {
    private lateinit var binding: ActCreatorPostBinding
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCreatorPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore
        val colRefUsers = db.collection("users")
        val colRefMessages = db.collection("messages")

        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        showKeyboard()

        binding.btnEnviar.setOnClickListener {
            val currentDate = Date()
            val format = SimpleDateFormat("HH:mm dd/MM/yy", Locale.getDefault())
            val dateCurrentFormatted = format.format(currentDate)
            val text = binding.edtMessagePost.text.toString()
            val userData = UserData()
            userData.userId = auth.currentUser?.uid.toString()
            colRefUsers.document(userData.userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userData.name = document.getString("name").toString()
                        userData.nickname = document.getString("nickname").toString()
                    }
                }.addOnCompleteListener {
                    if (text.isNotEmpty()) {
                        colRefMessages.add(
                            ModelPost(
                                userData.userId,
                                userData.name,
                                userData.nickname,
                                text,
                                dateCurrentFormatted
                            )
                        ).addOnSuccessListener { document ->
                            userData.messageId = document.id
                        }.addOnSuccessListener {
                            colRefUsers.document(userData.userId).update("messagesId", FieldValue.arrayUnion(userData.messageId))
                        }
                            .addOnCompleteListener {
                                finish()
                            }
                    }
                }

        }
    }

//TEM QUE ARRUMAR ESSA PORRA TE FEIO
    private fun showKeyboard() {
        val editText = binding.edtMessagePost
        editText.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
