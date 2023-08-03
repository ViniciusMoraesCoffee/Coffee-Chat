package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import coffee.company.coffeechat.databinding.ActCreatorPostBinding
import coffee.company.coffeechat.models.ModelPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class UserData {
    lateinit var userId: String
    lateinit var name: String
    lateinit var nickname: String
    lateinit var messageId: String
}
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
        else {
            val idUser = auth.currentUser?.uid.toString()
            db.collection("users").document(idUser).get()
                .addOnSuccessListener {document ->
                    val nickname = document.getString("nickname")
                    val name = document.getString("name")

                    binding.txtNickname.text = nickname
                    binding.txtNameuser.text = name
                }
        }

        showKeyboard()

        val maxCharacters = 392
        binding.edtMessagePost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(editable: Editable?) {
                // Aqui, você pode obter o número de caracteres atuais e atualizar a exibição, se necessário.
                val currentLength = editable?.length ?: 0
                val remainingCharacters = maxCharacters - currentLength

                // Exemplo: Atualiza um TextView para mostrar o número de caracteres restantes.
                val txtAmountChars = binding.txtAmountChars
                txtAmountChars.text = "$remainingCharacters"
            }
        })

        binding.btnEnviar.setOnClickListener {
            val text = binding.edtMessagePost.text.toString()

            val currentDate = LocalDate.now()
            val outputFormatter = DateTimeFormatter.ofPattern("d 'de' MMM 'de' yyyy", Locale("pt", "BR"))
            val formattedDate = currentDate.format(outputFormatter)


            val userData = UserData()

            Log.i("Datos", userData.toString())
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
                                formattedDate
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


