package coffee.company.coffeechat.signUp


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import coffee.company.coffeechat.createPost.CreatePostActivity
import coffee.company.coffeechat.publicVar.userId
import coffee.company.coffeechat.databinding.ActCadastroBinding
import coffee.company.coffeechat.models.ModelUser
import coffee.company.coffeechat.SignIn.SignInActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActCadastroBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCadastroBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        val db = Firebase.firestore
        val currentUser = auth.currentUser

        if (currentUser != null) {
            changeTelaCriarPost()
        }

        binding.txtCadastroLinkTologin.setOnClickListener {
            val loginIntent = Intent(this, SignInActivity::class.java)
            startActivity(loginIntent)
        }

        binding.btnCadastroSignup.setOnClickListener {
            val name     = binding.edtCadastroUsername.text.toString()
            val email    = binding.edtCadastroEmail.text.toString()
            val password = binding.edtCadastroSenha.text.toString()

            if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()){

                customSnackbar("Preencha todos os Campos!", Snackbar.LENGTH_SHORT)
            }
            else if (password.length<7) {
                customSnackbar("A Senha dever 8 ou mais Caracteres", Snackbar.LENGTH_SHORT)
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("STATUSsignup", "signInWithCustomToken:success")
                            val user = auth.currentUser

                            if (user?.uid != null) {
                                userId = user.uid
                            }

                            val modelUser = ModelUser(userId!!, name, name, null)

                            db.collection("users").document(userId!!)
                                .set(modelUser)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("STATUS_SET_DADOS", "DocumentSnapshot added with ID: $documentReference")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("STATUS_SET_DADOS", "Error adding document", e)
                                }

                            changeTelaCriarPost()
                        } else {
                            Log.w("STATUS_SIGN_UP", "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                resetEditsTexts()
            }
        }
    }

    private fun resetEditsTexts() {
        binding.edtCadastroUsername.setText("")
        binding.edtCadastroEmail.setText("")
        binding.edtCadastroSenha.setText("")
    }
    private fun customSnackbar(mensagem:String, tempo:Int? = Snackbar.LENGTH_SHORT, color:Int? = Color.RED) {
        val snackbar = Snackbar.make(binding.btnCadastroSignup, mensagem, tempo!!)
        snackbar.setBackgroundTint(color!!)
        snackbar.show()
    }
    //ARRUMA ESSA FUNÇÂO DEIXA ELA MELHOR OU SE TU FOR BRABO FAZ UM PUBLIC CLASS PRA GERAL DELA
    private fun changeTelaCriarPost() {
        val intent = Intent(this, CreatePostActivity::class.java)
        startActivity(intent)
    }
}



