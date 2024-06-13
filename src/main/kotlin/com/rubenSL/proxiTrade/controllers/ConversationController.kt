package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.exceptions.ResourceNotFoundException
import com.rubenSL.proxiTrade.exceptions.BadRequestException
import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.dtos.FormattedConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.ConversationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/conversations")
class ConversationController(private val conversationService: ConversationService,
                             private val firebaseService: FirebaseService) {

    @GetMapping("/{id}")
    fun getConversationById(@PathVariable id: Long): Conversation {
        try {
            return conversationService.getConversationById(id)
        } catch (e: Exception) {
            throw ResourceNotFoundException("Conversation not found")
        }
    }

    @PostMapping("/create/{targetUserId}")
    fun createConversation(@RequestHeader("Authorization") idToken:String, @PathVariable targetUserId: String): FormattedConversationDTO {
        val userUid1 = firebaseService.getUidFromToken(idToken.substring(7))
        if (userUid1.isBlank() || targetUserId.isBlank()) {
            throw BadRequestException("User UIDs must not be empty")
        }
        return conversationService.createConversation(userUid1, targetUserId)
    }

    @DeleteMapping("/{id}")
    fun deleteConversation(@PathVariable id: Long) {
        try {
            conversationService.deleteConversation(id)
        } catch (e: Exception) {
            throw ResourceNotFoundException("Conversation not found")
        }
    }

    @GetMapping("/user")
    fun getAllConversationsById(@RequestHeader("Authorization") idToken: String): List<FormattedConversationDTO> {
        val uid = firebaseService.getUidFromToken(idToken.substring(7))
        if (uid.isBlank()) {
            throw BadRequestException("User UID must not be empty")
        }

        return conversationService.getAllConversationsById(uid);
    }
}
