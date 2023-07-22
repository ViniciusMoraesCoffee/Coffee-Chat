package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import coffee.company.coffeechat.databinding.ActCreatorPostBinding
import coffee.company.coffeechat.models.ModelPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreatorPostActivity : AppCompatActivity() {
    private lateinit var binding: ActCreatorPostBinding
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")

    private lateinit var idMessage:String
    private lateinit var nickname:String
    private lateinit var name:String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCreatorPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }



        showKeyboard()

        binding.btnEnviar.setOnClickListener {

            }
        }

    private fun showKeyboard() {
        val editText = binding.edtMessagePost
        editText.requestFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
