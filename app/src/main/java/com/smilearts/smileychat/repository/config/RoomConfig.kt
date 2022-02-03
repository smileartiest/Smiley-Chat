package com.smilearts.smileychat.repository.config

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.smilearts.smileychat.repository.dao.ChatDao
import com.smilearts.smileychat.repository.table.ChatTable
import com.smilearts.smileychat.utils.Constant
import kotlinx.coroutines.CoroutineScope

@Database( entities = [
         ChatTable::class] , version = 1 , exportSchema = false)
abstract class RoomConfig : RoomDatabase() {

    abstract fun chatDao() : ChatDao

    companion object{
        const val TAG = "RoomConfig"
        private var INSTANCE : RoomConfig? = null
        fun getInstance( context: Context,scope: CoroutineScope) : RoomConfig? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomConfig::class.java,
                    Constant.dataBaseName
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { _ ->
                    Log.d(TAG , "Data Base Created")
                }
            }
        }

    }

}
