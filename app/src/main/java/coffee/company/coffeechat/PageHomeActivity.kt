package coffee.company.coffeechat

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coffee.company.coffeechat.SignInActivity
import coffee.company.coffeechat.adapters.AdapterPost
import coffee.company.coffeechat.databinding.ActPageHomeBinding
import coffee.company.coffeechat.models.ModelPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class PageHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActPageHomeBinding
    private val listaModelPosts: MutableList<ModelPost> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActPageHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        if (auth.currentUser == null) {
            navigateToScreen()
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            finish()
        }


        val colRefMessages = db.collection("messages")
        colRefMessages.addSnapshotListener { snapshot, e ->
            if (e != null) {
                //TRATAMENTO DE ERROS (N SEI COMO LIDAR)
                Log.w("STATUS_COL_MESSAGES", "Listen failed.", e)
            }

            if (snapshot != null) {
                Log.d("STATUS_COL_MESSAGES", "Current data: ${snapshot.documents}")
                for (document in snapshot.documents) {

                    listaModelPosts.add(
                        ModelPost(
                            document.getString("idAuthor").toString(),
                            document.getString("nameAuthor").toString(),
                            document.getString("nicknameAuthor").toString(),
                            document.getString("textPost").toString(),
                            document.getString("dataPost").toString()
                        )
                    )
                }

                updateRecycleView()
            }
            else {
                Log.d("STATUS_COL_MESSAGES", "Current data: null")
            }

        }

    }

    private fun updateRecycleView() {
        val rcvMessages = binding.rcvMessages
        rcvMessages.layoutManager = LinearLayoutManager(this)
        rcvMessages.adapter = AdapterPost(this, listaModelPosts)
    }

    private fun navigateToScreen() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

}