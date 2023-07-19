package coffee.company.coffeechat.CriarPost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coffee.company.coffeechat.databinding.ActCriarPostBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase as Firebase

class CriarPostActivity : AppCompatActivity() {

    private lateinit var binding: ActCriarPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActCriarPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Firebase.firestore





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