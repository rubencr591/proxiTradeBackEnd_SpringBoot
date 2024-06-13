package com.rubenSL.proxiTrade.model.dtos

data class FormattedConversationDTO(
    val id: Long,
    val user1: UserResponseDTO,
    val user2: UserResponseDTO,
    val title: String,
    val picture: String
)
