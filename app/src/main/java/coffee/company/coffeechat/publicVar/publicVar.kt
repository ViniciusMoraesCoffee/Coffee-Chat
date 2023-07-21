package coffee.company.coffeechat.publicVar

import com.google.firebase.auth.FirebaseAuth

val auth = FirebaseAuth.getInstance()
public var userId: String? = auth.currentUser?.uid