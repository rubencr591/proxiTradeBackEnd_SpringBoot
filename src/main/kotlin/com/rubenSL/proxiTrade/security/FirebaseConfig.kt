package com.rubenSL.proxiTrade.security

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @PostConstruct
    fun initializeFirebase() {
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(
                FirebaseConfig::class.java.getResourceAsStream("/proxitrade-9b26e-firebase-adminsdk-750ob-f6159d495f.json")
            ))
            .build()

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }
}

