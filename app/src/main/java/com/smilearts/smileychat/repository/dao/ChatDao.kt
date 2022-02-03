package com.smilearts.smileychat.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.smilearts.smileychat.repository.table.ChatTable

@Dao
interface ChatDao {

    @Insert
    fun insert(model: ChatTable)

    @Update
    fun update(model: ChatTable)

    @Query("SELECT * FROM ChatTable ")
    fun getChatList() : List<ChatTable>

    @Query("SELECT * FROM ChatTable WHERE chatId =:chatID")
    fun getChat(chatID: String) : ChatTable

    @Query("DELETE FROM ChatTable WHERE chatId =:chatID")
    fun clearHistory(chatID: String)

}