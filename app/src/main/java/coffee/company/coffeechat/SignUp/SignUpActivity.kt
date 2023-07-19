package coffee.company.coffeechat.SignUp


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import coffee.company.coffeechat.CreatePost.CreatePostActivity
import coffee.company.coffeechat.PublicVar.userUiu
import coffee.company.coffeechat.databinding.ActCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActCadastroBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCadastroBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            mudarTelaCriarPost()
        }

        binding.btnCadastroSignup.setOnClickListener {

            val name  = binding.edtCadastroUsername.text.toString()
            val email = binding.edtCadastroEmail.text.toString()
            val password = binding.edtCadastroSenha.text.toString()


            if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty()){

                snackbar("Preencha todos os Campos!", Snackbar.LENGTH_SHORT)



            }
            else if (password.length<7) {

                snackbar("A Senha dever 8 ou mais Caracteres", Snackbar.LENGTH_SHORT)
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("StatusCONTA", "signInWithCustomToken:success")
                            val user = auth.currentUser
                            userUiu = user?.uid
                            resetarEditsTexts()
                            mudarTelaCriarPost()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Status CONTA", "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            resetarEditsTexts()

                        }
                    }
            }
        }
    }

    fun resetarEditsTexts() {
        binding.edtCadastroUsername.setText("")
        binding.edtCadastroEmail.setText("")
        binding.edtCadastroSenha.setText("")
    }
    fun snackbar(mensagem:String, tempo:Int? = Snackbar.LENGTH_SHORT, color:Int? = Color.RED) {
        val snackbar = Snackbar.make(binding.btnCadastroSignup, mensagem, tempo!!)
        snackbar.setBackgroundTint(color!!)
        snackbar.show()
    }
    fun mudarTelaCriarPost() {
        val intent = Intent(this, CreatePostActivity::class.java)
        startActivity(intent)
    }
}


