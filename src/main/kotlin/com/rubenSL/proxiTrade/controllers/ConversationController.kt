package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.services.ConversationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/conversations")
class ConversationController(private val conversationService: ConversationService) {

    @GetMapping("/{id}")
    fun getConversationById(@PathVariable id: Long): Conversation {
        return conversationService.getConversationById(id)
    }

    @PostMapping("/create")
    fun createConversation(@RequestParam userUid1: String, @RequestParam userUid2: String): Conversation {
        return conversationService.createConversation(userUid1, userUid2)
    }


    @DeleteMapping("/{id}")
    fun deleteConversation(@PathVariable id: Long) {
        conversationService.deleteConversation(id)
    }

    @GetMapping("/user/{uid}")
    fun getAllConversationsById(@PathVariable uid: String): List<ConversationDTO> {
        return conversationService.getAllConversationsById(uid)
    }


}