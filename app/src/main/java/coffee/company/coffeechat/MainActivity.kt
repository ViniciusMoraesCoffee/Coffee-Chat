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
import coffee.company.coffeechat.model.Mensagem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

private val listaMensagens: MutableList<Mensagem> = mutableListOf()

class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        val rcv_mensagens = findViewById<RecyclerView>(R.id.rcv_mensagens)
        rcv_mensagens.layoutManager = LinearLayoutManager(this)
        rcv_mensagens.setHasFixedSize(true)
        val adapterMensagem = AdapterMensagem(this, listaMensagens)
        rcv_mensagens.adapter = adapterMensagem;

        val btn_enviar = findViewById<Button>(R.id.btn_enviar)
        val txt_mensagem = findViewById<EditText>(R.id.edit_mensagem)


        btn_enviar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val mensagem = txt_mensagem.text.toString()
            if (mensagem == "" || mensagem == null) {
                Toast.makeText(this, "Falta Infomações", Toast.LENGTH_SHORT).show()
            }
            else {
                adicionar("Capivaria", mensagem, R.drawable.capivara)
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



