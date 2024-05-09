package com.rubenSL.proxiTrade.repositories

import com.rubenSL.proxiTrade.model.entities.Conversation
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationRepository : JpaRepository<Conversation, Long> {

    fun findByUser1UidOrUser2Uid(user1Uid: String, user2Uid: String): List<Conversation>

}