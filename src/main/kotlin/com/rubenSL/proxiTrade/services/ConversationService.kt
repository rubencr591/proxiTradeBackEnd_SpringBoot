package com.rubenSL.proxiTrade.services

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.model.mappers.ConversationMapper
import com.rubenSL.proxiTrade.repositories.ConversationRepository
import com.rubenSL.proxiTrade.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConversationService (@Autowired private val conversationRepository: ConversationRepository,
                           @Autowired private val userRepository: UserRepository,
                           @Autowired private val conversationMapper: ConversationMapper) {


    fun getConversationById(id: Long): Conversation {
        return conversationRepository.findById(id).orElseThrow { RuntimeException("Conversation not found with id: $id") }
    }

    fun createConversation( userUid1:String, userUid2:String): ConversationDTO {
        val user1 = userRepository.findById(userUid1).orElseThrow { RuntimeException("User not found with uid: $userUid1") }
        val user2 = userRepository.findById(userUid2).orElseThrow { RuntimeException("User not found with uid: $userUid2") }

        val conversation = Conversation(user1 = user1, user2 = user2)

        return  conversationMapper.toConversationDTO(conversationRepository.save(conversation))
    }

    fun updateConversation(id: Long, conversation: Conversation): Conversation {
        if (!conversationRepository.existsById(id)) {
            throw RuntimeException("Conversation not found with id: $id")
        }
        return conversationRepository.save(conversation)
    }

    fun deleteConversation(id: Long) {
        if (!conversationRepository.existsById(id)) {
            throw RuntimeException("Conversation not found with id: $id")
        }
        conversationRepository.deleteById(id)
    }

    fun getAllConversationsById(uid: String): List<ConversationDTO> {
        val conversations = conversationRepository.findByUser1UidOrUser2Uid(uid, uid)
        return conversations.map { conversationMapper.toConversationDTO(it) }
    }
}