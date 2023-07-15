package coffee.company.coffeechat

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coffee.company.coffeechat.databinding.ActCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.ktx.Firebase

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCadastroLinkTologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCadastroSignup.setOnClickListener {view ->

            val username = binding.edtCadastroUsername.text.toString()
            val email = binding.edtCadastroEmail.text.toString()
            val senha = binding.edtCadastroSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty() || username.isEmpty() || senha.length < 8) {
                if (email.length < 8) {
                    val snackbar = Snackbar.make(view, "A Senha deve ter 8 ou Mais Caracteres", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
                else {
                    val snackbar = Snackbar.make(view, "Preencha todos os Campos", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
            else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                    if (cadastro.isSuccessful) {
                        val snackbar = Snackbar.make(view, "Sucesso ao cadastrar!", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.BLUE)
                        snackbar.show()
                        binding.edtCadastroEmail.setText("")
                        binding.edtCadastroSenha.setText("")
                        binding.edtCadastroUsername.setText("")
                    }
                }.addOnFailureListener { exception ->
                    val mensagemErro = when(exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Email Inválido!"
                        is FirebaseAuthUserCollisionException -> "Essa conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Falha na conexão com a internet!"
                        else -> "Erro ao cadastrar usuário!"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                    binding.edtCadastroEmail.setText("")
                    binding.edtCadastroSenha.setText("")
                    binding.edtCadastroUsername.setText("")
                }
            }
        }
    }
}