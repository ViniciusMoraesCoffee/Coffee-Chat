package coffee.company.coffeechat.createPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coffee.company.coffeechat.adapter.AdapterPost
import coffee.company.coffeechat.databinding.ActCriarPostBinding
import coffee.company.coffeechat.models.Post
import coffee.company.coffeechat.publicVar.userId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase as Firebase

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActCriarPostBinding
    private lateinit var nameUser: String
    private val listaPosts: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCriarPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        if (auth.currentUser == null) finish()
        binding.btnLoguot.setOnClickListener {
            auth.signOut()
            finish()
        }

//        db.collection("users").document(userId.toString())
//            .addSnapshotListener { documento, error ->
//                nameUser = if (documento != null) {
//                    documento.getString("name").toString()
//                } else {
//                    "UserNotFind"
//                }
//                Log.i("NAME", userId.toString())
//            }


        binding.btnEnviar.setOnClickListener {
            val postMensage = binding.edtMensagem.text.toString()
            if (postMensage.isEmpty() || nameUser.isEmpty()) {
                //
            } else {
                listaPosts.add(
                    Post(
//                        nameUser,
                        "Usuario",
                        postMensage
                    )
                )
                binding.edtMensagem.text = null
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