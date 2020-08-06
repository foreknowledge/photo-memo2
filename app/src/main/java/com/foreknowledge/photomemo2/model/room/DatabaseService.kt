package com.foreknowledge.photomemo2.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Yeji on 22,April,2020.
 */
@Database(entities = [MemoEntity::class], version = 1, exportSchema = false)
abstract class DatabaseService : RoomDatabase() {
	abstract fun memoDao(): MemoDao

	companion object {
		private const val DATABASE_NAME = "photoMemo.db"

		private var instance: DatabaseService? = null

		fun getInstance(context: Context): DatabaseService =
			(instance ?: create(context)).also { instance = it }

		private fun create(context: Context): DatabaseService =
			Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
				.fallbackToDestructiveMigration()
				.build()
	}
}