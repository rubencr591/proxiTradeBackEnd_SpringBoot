package com.rubenSL.proxiTrade.model.mappers

import com.rubenSL.proxiTrade.model.dtos.ConversationDTO
import com.rubenSL.proxiTrade.model.dtos.LocationDTO
import com.rubenSL.proxiTrade.model.dtos.UserDTO
import com.rubenSL.proxiTrade.model.entities.Conversation
import java.util.*

class ConversationMapper {
    companion object{
        fun toConversationDTO(conversation: Conversation): ConversationDTO {
            val user1DTO = conversation.user1?.let { UserMapper.toUserDTO(it) }

            val user2DTO = conversation.user2?.let { UserMapper.toUserDTO(it) }

            return ConversationDTO(
                id = conversation.id,
                user1 = user1DTO,
                user2 = user2DTO
            )
        }
    }
}