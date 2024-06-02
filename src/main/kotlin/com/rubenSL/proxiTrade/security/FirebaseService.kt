package com.rubenSL.proxiTrade.security

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.stereotype.Service

@Service
class FirebaseService {
    fun verifyToken(idToken: String): FirebaseToken {
        return FirebaseAuth.getInstance().verifyIdToken(idToken)
    }
    fun getUidFromToken(idToken: String): String {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        return decodedToken.uid
    }
}