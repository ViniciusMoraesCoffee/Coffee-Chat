package coffee.company.coffeechat.SignIn

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coffee.company.coffeechat.CreatePost.CreatePostActivity
import coffee.company.coffeechat.SignUp.SignUpActivity
import coffee.company.coffeechat.databinding.ActLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (auth.currentUser != null) {
            navegarTelaPrincipal()
        }

        binding.txtLinkTocadastro.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {view ->
            val email = binding.edtLoginEmail.text.toString()
            val senha = binding.edtLoginSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os Campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else{
                binding.edtLoginEmail.setText("")
                binding.edtLoginSenha.setText("")
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful) {
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {exception ->
                    val mensagemErro = when(exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Email Inválido!"
                        is FirebaseNetworkException -> "Falha na conexão com a internet!"
                        else -> "Erro ao logar usuário!"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                    binding.edtLoginEmail.setText("")
                    binding.edtLoginSenha.setText("")
                }
            }
        }


    }

    private fun navegarTelaPrincipal(){
        val mudarIntent = Intent(this, CreatePostActivity::class.java)
        startActivity(mudarIntent)
    }
}