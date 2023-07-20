package coffee.company.coffeechat.CreatePost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coffee.company.coffeechat.Adapter.AdapterPost
import coffee.company.coffeechat.PublicVar.userId
import coffee.company.coffeechat.databinding.ActCriarPostBinding
import coffee.company.coffeechat.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase as Firebase

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActCriarPostBinding
    private lateinit var nameUser: String
    private val listaPosts: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActCriarPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        binding.btnLoguot.setOnClickListener {
            auth.signOut()
            finish()
        }

        db.collection("users").document("U5Vcbl1r2Yaxmnu5LurgcEXTEmE2")
            .addSnapshotListener { documento, error ->
                nameUser = if (documento != null) {
                    documento.getString("name").toString()
                } else {
                    "UserNotFind"
                }
                Log.i("NAME", userId.toString())
            }


        binding.btnEnviar.setOnClickListener {
            val postMensage = binding.edtMensagem.text.toString()
            if (!postMensage.isEmpty() || !nameUser.isEmpty()) {
                listaPosts.add(
                    Post(
                        nameUser,
                        postMensage
                    )
                )
                binding.edtMensagem.text = null
            } else {
                //
            }

            atualizarRecycleView()
        }

    }
    private fun atualizarRecycleView() {
        val rcvPost = binding.rcvPosts
        rcvPost.layoutManager = LinearLayoutManager(this)
        rcvPost.adapter = AdapterPost(this, listaPosts)
        rcvPost.setHasFixedSize(true)
    }
}