package com.rubenSL.proxiTrade.model.dtos


data class ConversationDTO(
    var id: Long? = null,
    var user1: UserResponseDTO? = null,
    var user2: UserResponseDTO? = null
)