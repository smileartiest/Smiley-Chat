package com.smilearts.smileychat.repository.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatTable")
data class ChatTable(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "chatId") var chatId: String = "",
    @ColumnInfo(name = "chatPassword") var chatPassword: String = "",
    @ColumnInfo(name = "chatMessage") var chatMessage: String = "",
    @ColumnInfo(name = "chatPrivacy") var chatPrivacy: Boolean = false
)
