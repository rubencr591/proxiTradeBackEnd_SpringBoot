package com.rubenSL.proxiTrade.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(username: String): String {
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
            .signWith(key)
            .compact()
    }

    fun getUsernameFromToken(token: String): String {
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun validateToken(token: String, username: String): Boolean {
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject == username && claims.expiration.after(Date())
    }
}