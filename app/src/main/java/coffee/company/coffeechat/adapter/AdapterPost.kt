package coffee.company.coffeechat.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coffee.company.coffeechat.R
import coffee.company.coffeechat.models.Post


class AdapterPost(
    private val context: Context,
    private val postList: MutableList<Post>
) : RecyclerView.Adapter<AdapterPost.MyViewHolder>() {

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.frg_post, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtUserName.text = postList[position].nameUser
        holder.txtMessage.text = postList[position].textPost
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName: TextView = itemView.findViewById<TextView>(R.id.txtUserNamePost)
        val txtMessage: TextView = itemView.findViewById<TextView>(R.id.txtMensagemPost)
    }
}
