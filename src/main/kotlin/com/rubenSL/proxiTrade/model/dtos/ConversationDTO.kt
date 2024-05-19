package com.rubenSL.proxiTrade.model.dtos


data class ConversationDTO(
    var id: Long? = null,
    var user1: UserDTO? = null,
    var user2: UserDTO? = null
)