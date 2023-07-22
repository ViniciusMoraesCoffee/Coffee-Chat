package coffee.company.coffeechat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coffee.company.coffeechat.R
import coffee.company.coffeechat.models.ModelPost

class AdapterPost (
    private val context: Context,
    private val modelPostList: MutableList<ModelPost>
) : RecyclerView.Adapter<AdapterPost.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.frg_post, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = modelPostList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtName.text = modelPostList[position].nameAuthor
        holder.txtNickname.text = modelPostList[position].nicknameAuthor
        holder.txtMensagemPost.text = modelPostList[position].textPost
        holder.txtData.text = modelPostList[position].dataPost
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMensagemPost: TextView = itemView.findViewById<TextView>(R.id.txtMensagemPost)
        val txtData: TextView = itemView.findViewById<TextView>(R.id.txtData)
        val txtName: TextView = itemView.findViewById<TextView>(R.id.txtName)
        val txtNickname: TextView = itemView.findViewById<TextView>(R.id.txtNickname)
    }
}