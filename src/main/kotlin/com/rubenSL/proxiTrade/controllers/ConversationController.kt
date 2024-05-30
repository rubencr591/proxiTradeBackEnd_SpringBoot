package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.exceptions.ResourceNotFoundException
import com.rubenSL.proxiTrade.exceptions.BadRequestException
import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.services.ConversationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/conversations")
class ConversationController(private val conversationService: ConversationService) {

    @GetMapping("/{id}")
    fun getConversationById(@PathVariable id: Long): Conversation {
        try {
            return conversationService.getConversationById(id)
        } catch (e: Exception) {
            throw ResourceNotFoundException("Conversation not found")
        }
    }

    @PostMapping("/create")
    fun createConversation(@RequestParam userUid1: String, @RequestParam userUid2: String): ConversationDTO {
        if (userUid1.isBlank() || userUid2.isBlank()) {
            throw BadRequestException("User UIDs must not be empty")
        }
        return conversationService.createConversation(userUid1, userUid2)
    }

    @DeleteMapping("/{id}")
    fun deleteConversation(@PathVariable id: Long) {
        try {
            conversationService.deleteConversation(id)
        } catch (e: Exception) {
            throw ResourceNotFoundException("Conversation not found")
        }
    }

    @GetMapping("/user/{uid}")
    fun getAllConversationsById(@PathVariable uid: String): List<ConversationDTO> {
        if (uid.isBlank()) {
            throw BadRequestException("User UID must not be empty")
        }
        return conversationService.getAllConversationsById(uid)
    }
}
