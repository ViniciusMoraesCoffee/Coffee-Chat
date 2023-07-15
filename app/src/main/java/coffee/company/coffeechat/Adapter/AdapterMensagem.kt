package coffee.company.coffeechat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coffee.company.coffeechat.R
import coffee.company.coffeechat.model.Mensagem

class AdapterMensagem(private val context: Context, private  val mensagens: MutableList<Mensagem>): RecyclerView.Adapter<AdapterMensagem.MensagemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensagemViewHolder {
        val itemLista = LayoutInflater.from(context).inflate(R.layout.frg_mensagem, parent, false)
        val holder = MensagemViewHolder(itemLista)
        return holder
    }

    override fun onBindViewHolder(holder: MensagemViewHolder, position: Int) {
        holder.foto.setImageResource(mensagens[position].foto)
        holder.usuario_name.text = mensagens[position].name_usuario
        holder.mensagem.text = mensagens[position].mensagem_texto
    }

    override fun getItemCount(): Int = mensagens.size



    inner class MensagemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto = itemView.findViewById<ImageView>(R.id.img_usuario)
        val usuario_name = itemView.findViewById<TextView>(R.id.txt_name_usuario)
        val mensagem = itemView.findViewById<TextView>(R.id.txt_mensagem)
    }
}