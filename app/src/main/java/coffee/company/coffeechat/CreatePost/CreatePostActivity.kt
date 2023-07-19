package coffee.company.coffeechat.CreatePost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coffee.company.coffeechat.databinding.ActCriarPostBinding
import coffee.company.coffeechat.model.Post
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase as Firebase

class CreatePostActivity : AppCompatActivity() {

    private lateinit var binding: ActCriarPostBinding
    //private val listaPosts: MutableList<Post> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActCriarPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore

//        listaPosts.add(Post(
//            "Carlos",
//            "bola pato qpowien aosdjw nqwdj nadn ajjnskjdakjdb jb j bsjdsajd"
//        ))
//
//        val rcvPost = binding.rcvPosts
//        rcvPost.layoutManager = LinearLayoutManager(this)
//        rcvPost.adapter = AdapterPost(this, listaPosts)
//        rcvPost.setHasFixedSize(true)

//        binding.btnLogout.setOnClickListener {
//            auth.signOut()
//        }
//
//        binding.btnEnviar.setOnClickListener {
//
//
//        }



//        val user = hashMapOf(
//            "first" to "Ada",
//            "last" to "Lovelace",
//            "born" to 1815,
//        )
//
//// Add a new document with a generated ID
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//            }












    }
}