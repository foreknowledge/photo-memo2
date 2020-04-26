package com.foreknowledge.photomemo2.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.foreknowledge.photomemo2.model.data.Memo

/**
 * Create by Yeji on 22,April,2020.
 */
@Entity
class MemoEntity (
		val title: String,
		val content: String,
		val photoPaths: String,

		@PrimaryKey(autoGenerate = true)
		val id: Long = 0L
) {
	fun toMemo() = Memo(title, content, photoPaths, id)
}