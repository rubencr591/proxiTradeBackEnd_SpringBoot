package com.rubenSL.proxiTrade.controllers

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.dtos.FormattedConversationDTO
import com.rubenSL.proxiTrade.model.dtos.UserResponseDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.security.FirebaseService
import com.rubenSL.proxiTrade.services.ConversationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.ResponseEntity

class ConversationControllerTest {
    private val conversationService: ConversationService = Mockito.mock(ConversationService::class.java)
    private val firebaseService: FirebaseService = Mockito.mock(FirebaseService::class.java)
    private val conversationController = ConversationController(conversationService, firebaseService)

    @Test
    fun getConversationById() {
        val id = 1L
        val expectedConversation = Conversation(id, null, null)
        Mockito.`when`(conversationService.getConversationById(id)).thenReturn(expectedConversation)

        val actualConversation = conversationController.getConversationById(id)

        assertEquals(expectedConversation, actualConversation)
    }

    @Test
    fun createConversation() {
        val idToken = "Bearer testToken"
        val targetUserId = "testTargetUserId"
        val uid = "testUid"
        val user1 = UserResponseDTO("uid1", "name1", "email1", null, "profilePicture1", 1.0)
        val user2 = UserResponseDTO("uid2", "name2", "email2", null, "profilePicture2", 2.0)
        val expectedConversation = FormattedConversationDTO(1L, user1, user2, "title", "picture")
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(conversationService.createConversation(uid, targetUserId)).thenReturn(expectedConversation)

        val actualConversation = conversationController.createConversation(idToken, targetUserId)

        assertEquals(expectedConversation, actualConversation)
    }

    @Test
    fun deleteConversation() {
        val id = 1L
        Mockito.doNothing().`when`(conversationService).deleteConversation(id)

        conversationController.deleteConversation(id)

        Mockito.verify(conversationService, Mockito.times(1)).deleteConversation(id)
    }

    @Test
    fun getAllConversationsById() {
        val idToken = "Bearer testToken"
        val uid = "testUid"
        val user1 = UserResponseDTO("uid1", "name1", "email1", null, "profilePicture1", 1.0)
        val user2 = UserResponseDTO("uid2", "name2", "email2", null, "profilePicture2", 2.0)
        val expectedConversations = listOf(FormattedConversationDTO(1L, user1, user2, "title", "picture"))
        Mockito.`when`(firebaseService.getUidFromToken(idToken.substring(7))).thenReturn(uid)
        Mockito.`when`(conversationService.getAllConversationsById(uid)).thenReturn(expectedConversations)

        val actualConversations = conversationController.getAllConversationsById(idToken)

        assertEquals(expectedConversations, actualConversations)
    }
}