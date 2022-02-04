package com.smilearts.smileychat.main.model

data class ChatListModel(
    var chatID: String = "",
    var chatName: String = "",
    var chatPersonID: String = "",
    var chatPassword: String = "",
    var chatEncryptKey: String = "",
    var chatDecryptKey: String = "",
    var chatLastOnline: String = "",
    var chatStatus: Boolean = false,
    var chatPrivacy: Boolean = false
)