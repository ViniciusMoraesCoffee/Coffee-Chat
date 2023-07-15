package coffee.company.coffeechat

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import coffee.company.coffeechat.databinding.ActCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    //Falta Estudo
    private val requestGaleria =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {permission ->
            if (permission) {
                resultGaleria.launch(Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ))
            }
            else {
                showDialogPermission()
            }

        }
    private val resultGaleria =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    baseContext.contentResolver,
                    result.data?.data
                )
            } else {
                val source = ImageDecoder.createSource(
                    this.contentResolver,
                    result.data?.data!!
                )
                ImageDecoder.decodeBitmap(source)
            }

            binding.imgCadastroUsuario.setImageBitmap(bitmap)
            Log.i("bimap", bitmap.toString())
        }
    companion object {
        private val PERMISSION_GALERIA = Manifest.permission.READ_EXTERNAL_STORAGE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCadastroLinkTologin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imgCadastroUsuario.setOnClickListener {
            verificarPermissionGaleria()
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

                        val usuariosMap = hashMapOf(
                           "nomeUsuario" to username
                        )

                        db.collection("Usuarios").document(username)
                            .set(usuariosMap).addOnCompleteListener {

                            }.addOnFailureListener {

                            }
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



    private fun verificarPermission(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    private fun verificarPermissionGaleria() {
        val permissionGaleriaAceita = verificarPermission(PERMISSION_GALERIA)

        when(permissionGaleriaAceita) {
            permissionGaleriaAceita -> {
                resultGaleria.launch(Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ))
            }

            shouldShowRequestPermissionRationale(PERMISSION_GALERIA) -> showDialogPermission()

            else -> requestGaleria.launch(PERMISSION_GALERIA)
        }
    }
    private fun showDialogPermission() {
        val toast = Toast(this,)
        toast.setText("Permição Negada!")
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }


    private fun navegarTelaPrincipal(){
        var intentN = Intent(this, MainActivity::class.java)
        startActivity(intentN)
        finish()
    }
    override fun onStart() {
        super.onStart()
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null) {
            navegarTelaPrincipal()
        }
    }

}