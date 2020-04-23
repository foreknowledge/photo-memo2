package com.foreknowledge.photomemo2.model.repository

import com.foreknowledge.photomemo2.GlobalApplication
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.room.MemoEntity

/**
 * Create by Yeji on 22,April,2020.
 */
object MemoRepository {
	private val memoDataSource = MemoDataSource(GlobalApplication.getContext())

	suspend fun getAllMemos(): List<Memo> = memoDataSource.getAll()

	suspend fun getMemo(id: Long): Memo? = memoDataSource.get(id)

	suspend fun addMemo(memo: Memo) = memoDataSource.add(MemoEntity(memo.title, memo.content, memo.photoPaths))

	suspend fun deleteMemo(memoId: Long) = memoDataSource.delete(memoId)
}