package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.dtos.FormattedConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import com.rubenSL.proxiTrade.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConversationMapper @Autowired constructor(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository
) {

        fun toConversationDTO(conversation: Conversation): ConversationDTO {
            return ConversationDTO(
                id = conversation.id,
                user1 = conversation.user1?.let { userMapper.toUserResponseDTO(it)  },
                user2 = conversation.user2?.let { userMapper.toUserResponseDTO(it)  },
            )
        }

        fun toConversation(conversationDTO: ConversationDTO): Conversation {
            return Conversation(
                id = conversationDTO.id,
                user1 = conversationDTO.user1?.let { userMapper.toUserByUserResponseDTO(it) },
                user2 = conversationDTO.user2?.let { userMapper.toUserByUserResponseDTO(it) }
            )
        }

        fun convertToFormattedConversationDTO(conversation: Conversation, currentUserId: String): FormattedConversationDTO {
            val user1 = conversation.user1?.uid?.let { userRepository.findById(it).orElse(null) }
            val user2 = conversation.user2?.uid?.let { userRepository.findById(it).orElse(null) }

            val user1Response = user1?.let { userMapper.toUserResponseDTO(it) }
            val user2Response = user2?.let { userMapper.toUserResponseDTO(it) }

            if (user1Response != null && user2Response != null) {
                val title = if (currentUserId == user1Response.uid) user2Response.name else user1Response.name
                val picture = if (currentUserId == (user1Response.uid ?: "")) user2Response.profilePicture else user1Response.profilePicture

                return FormattedConversationDTO(
                    id = conversation.id ?: 0,
                    user1 = user1Response,
                    user2 = user2Response,
                    title = title ?: "",
                    picture = picture ?: ""
                )
            } else {
                throw RuntimeException("User not found")
            }
        }

}