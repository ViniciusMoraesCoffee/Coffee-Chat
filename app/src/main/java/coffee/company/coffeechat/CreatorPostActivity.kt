package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import coffee.company.coffeechat.databinding.ActCreatorPostBinding
import coffee.company.coffeechat.models.ModelPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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

        val repository = DataRepository.getInstance()
        val (id, name, nickname) = repository.getUserInfo()
        val data = repository.getUserData()

        if (auth.currentUser != null) {
            showKeyboard()

            binding.txtNameuser.text = name
            binding.txtNickname.text = nickname

            //Trata isso pf ta inutil
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

                if (text.isNotEmpty()){
                    var messagesId: String? = null
                    colRefUsers.document(id).get()
                        .addOnSuccessListener {
                        }.addOnCompleteListener {
                            colRefMessages.add(
                                ModelPost(
                                    id,
                                    name,
                                    nickname,
                                    text,
                                    data as String
                                )
                            ).addOnSuccessListener { document ->
                                messagesId = document.id
                            }.addOnSuccessListener {
                                messagesId?.let { it1 ->
                                    colRefUsers.document(it1)
                                        .update("messagesId", FieldValue.arrayUnion(messagesId))
                                }
                            }.addOnCompleteListener {
                                finish()
                            }

                        }
                }
                else {
                    val toast = Toast.makeText(this, "Mensagem Vazia", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

            binding.btnClose.setOnClickListener {
                finish()
            }
        }
        else {
            startActivity(Intent(this, SignInActivity::class.java))
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


