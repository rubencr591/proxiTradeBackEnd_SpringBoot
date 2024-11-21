package com.rubenSL.proxiTrade.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(private val firebaseService: FirebaseService) {

   @Value("\${server.ssl.key-store}")
   private lateinit var sslKeyStore: String

   @Value("\${server.ssl.key-store-password}")
   private lateinit var sslKeyStorePassword: String

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .requiresChannel { requiresChannel -> requiresChannel.anyRequest().requiresSecure() }
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(FirebaseAuthenticationFilter(firebaseService), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}

