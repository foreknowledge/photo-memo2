package com.foreknowledge.photomemo2.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.foreknowledge.photomemo2.model.data.Memo

/**
 * Create by Yeji on 22,April,2020.
 */
@Entity
class MemoEntity (
		@PrimaryKey(autoGenerate = true)
		val id: Long,
		val title: String,
		val content: String,
		val photoPaths: String
) {
	fun toMemo() = Memo(id, title, content, photoPaths)
}