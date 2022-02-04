package com.smilearts.smileychat.repository.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatTable")
data class ChatTable(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "chatId") var chatId: String = "",
    @ColumnInfo(name = "chatName") var chatName: String = "",
    @ColumnInfo(name = "chatPersonID") var chatPersonID: String = "",
    @ColumnInfo(name = "chatPassword") var chatPassword: String = "",
    @ColumnInfo(name = "chatEncryptKey") var chatEncryptKey: String = "",
    @ColumnInfo(name = "chatDecryptKey") var chatDecryptKey: String = "",
    @ColumnInfo(name = "chatLastOnline") var chatLastOnline: String = "",
    @ColumnInfo(name = "chatStatus") var chatStatus: Boolean = false,
    @ColumnInfo(name = "chatPrivacy") var chatPrivacy: Boolean = false
)
