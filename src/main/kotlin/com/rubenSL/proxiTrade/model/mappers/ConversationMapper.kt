package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConversationMapper @Autowired constructor(
    private val userMapper: UserMapper
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
}