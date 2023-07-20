package coffee.company.coffeechat.PublicVar

import com.google.firebase.auth.FirebaseAuth

val auth = FirebaseAuth.getInstance()
public var userId = auth.currentUser?.uid