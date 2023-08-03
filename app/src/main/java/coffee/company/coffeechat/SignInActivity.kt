package coffee.company.coffeechat

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import coffee.company.coffeechat.databinding.ActSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActSignInBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (auth.currentUser != null) {
            navigateToScreenMain()
        }

        binding.txtLinkToRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {view ->
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginSenha.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                newSnackbar(view,"Preencha todos os Campos!")
            }
            else{
                binding.edtLoginEmail.setText("")
                binding.edtLoginSenha.setText("")

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { altentication ->
                    if (altentication.isSuccessful) {
                        navigateToScreenMain()
                    }
                }.addOnFailureListener {exception ->
                    val messageError = when(exception) {
                        is FirebaseAuthInvalidCredentialsException -> "Email Inválido!"
                        is FirebaseNetworkException -> "Falha na conexão com a internet!"
                        else -> "Erro ao logar usuário!"
                    }
                    newSnackbar(view,messageError)
                }
            }
        }
    }

    private fun navigateToScreenMain(){
        val changeIntent = Intent(this, PageHomeActivity::class.java)
        startActivity(changeIntent)
    }

    private fun newSnackbar(view:View, textMsg:String) {
        val snackbar = Snackbar.make(view, textMsg, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }
}