package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coffee.company.coffeechat.Adapter.AdapterMensagem
import coffee.company.coffeechat.databinding.ActCadastroBinding
import coffee.company.coffeechat.databinding.ActMainBinding
import coffee.company.coffeechat.model.Mensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private val listaMensagens: MutableList<Mensagem> = mutableListOf()
@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActMainBinding
private val auth = FirebaseAuth.getInstance()
@SuppressLint("StaticFieldLeak")
private val db = FirebaseFirestore.getInstance()
private lateinit var userName:String

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            auth.signOut()
            finish()
        }


        val rcv_mensagens = findViewById<RecyclerView>(R.id.rcv_mensagens)
        rcv_mensagens.layoutManager = LinearLayoutManager(this)
        rcv_mensagens.setHasFixedSize(true)
        val adapterMensagem = AdapterMensagem(this, listaMensagens)
        rcv_mensagens.adapter = adapterMensagem;

        val btn_enviar = findViewById<Button>(R.id.btn_enviar)
        val txt_mensagem = findViewById<EditText>(R.id.edit_mensagem)


        val nomeUsuario = db.collection("Usuarios").document("CafeMaster")
            .addSnapshotListener { documento, error ->
                if (documento != null) {
                    userName = documento.getString("nomeUsuario").toString()
                }
            }

        btn_enviar.setOnClickListener {

            val usuarioAtual = auth.currentUser
            val nomeUsuario = db.collection("Usuarios").document("CafeMaster")
                .addSnapshotListener { documento, error ->
                    if (documento != null) {
                        userName = documento.getString("nomeUsuario").toString()
                    }
                }


            val mensagem = txt_mensagem.text.toString()

            if (mensagem == "" || mensagem.isEmpty()) {
                Toast.makeText(this, "Falta Infomações", Toast.LENGTH_SHORT).show()
            }
            else {
                adicionar(userName, mensagem, R.drawable.capivara)
            }
            txt_mensagem.text = null
            val lastFragmentPosition: Int = adapterMensagem.getItemCount() - 1
            rcv_mensagens.scrollToPosition(lastFragmentPosition)
        }

    }
    fun adicionar(nomeUsuario: String, mensagem: String, fotoUsuario:Int) {
        listaMensagens.add(Mensagem (
            fotoUsuario,
            nomeUsuario,
            mensagem))
        val rcv_mensagens = findViewById<RecyclerView>(R.id.rcv_mensagens)
        rcv_mensagens.layoutManager = LinearLayoutManager(this)
        rcv_mensagens.setHasFixedSize(true)
        val adapterMensagem = AdapterMensagem(this, listaMensagens)
        rcv_mensagens.adapter = adapterMensagem;
    }
}



