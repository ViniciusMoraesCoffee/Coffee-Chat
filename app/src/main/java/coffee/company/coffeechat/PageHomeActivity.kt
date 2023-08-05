package coffee.company.coffeechat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import coffee.company.coffeechat.adapters.AdapterPost
import coffee.company.coffeechat.databinding.ActPageHomeBinding
import coffee.company.coffeechat.models.ModelPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


class PageHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActPageHomeBinding
    private var listModelPosts: MutableList<ModelPost> = mutableListOf()
    private var listModelPostsTrue: MutableList<ModelPost> = mutableListOf()

    private val tag = "PAGE_HOME"

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActPageHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        if (auth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            finish()
        }

        binding.btnCriar.setOnClickListener {
            startActivity(Intent(this, CreatorPostActivity::class.java))
        }

        binding.rcvMessages.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_SCROLL -> {
                    binding.btnCriar.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction {
                            binding.btnCriar.visibility = View.GONE
                        }
                        .start()
                }
                MotionEvent.ACTION_UP -> {
                    binding.btnCriar.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .withStartAction {
                            binding.btnCriar.visibility = View.VISIBLE
                        }
                        .start()
                }
            }
            false
        }

        val colRefMessages = db.collection("messages")
        colRefMessages.addSnapshotListener { snapshot, e ->
            if (e != null) {
                //TRATAMENTO DE ERROS (N SEI COMO LIDAR)
                //ESTUDA QUE PORRA E ESSES LOGO DIFERENCIADO I D W
                Log.w(tag, "Listen failed.", e)
            }

            if (snapshot != null) {
                Log.d(tag, "Current data: ${snapshot.documents}")
                for (document in (snapshot.documents)) {
                    listModelPosts.add(
                        ModelPost(
                            document.getString("idAuthor").toString(),
                            document.getString("nameAuthor").toString(),
                            document.getString("nicknameAuthor").toString(),
                            document.getString("textPost").toString(),
                            document.getString("dataPost").toString()
                        )
                    )
                }
                listModelPostsTrue = listModelPosts.toSet().toList().toMutableList()
                val formatter = DateTimeFormatter.ofPattern("d 'de' MMM 'de' yyyy", Locale("pt", "BR"))
                listModelPostsTrue.sortedBy { it.dataPost }.reversed()
                if (listModelPostsTrue.isNotEmpty()) updateRecycleView()
            }
            else {
                Log.d(tag, "Current data: null")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateRecycleView() {
        val rcvMessages = binding.rcvMessages
        rcvMessages.layoutManager = LinearLayoutManager(this)
        rcvMessages.adapter = AdapterPost(this, listModelPostsTrue)
    }

//    private fun navigateToScreen() {
//        startActivity(Intent(this, SignInActivity::class.java))
//    }

}