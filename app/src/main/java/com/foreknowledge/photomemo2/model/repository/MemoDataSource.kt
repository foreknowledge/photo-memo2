package com.foreknowledge.photomemo2.model.repository

import android.content.Context
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.model.room.DatabaseService
import com.foreknowledge.photomemo2.model.room.MemoEntity

/**
 * Create by Yeji on 22,April,2020.
 */
class MemoDataSource(context: Context) {
	private val memoDao = DatabaseService.getInstance(context).memoDao()

	suspend fun getAll(): List<Memo> = memoDao.getAllMemoEntities().map { it.toMemo() }

	suspend fun get(id: Long): Memo? = memoDao.getMemoEntity(id)?.toMemo()

	suspend fun add(memoEntity: MemoEntity) = memoDao.addMemoEntity(memoEntity)

	suspend fun delete(id: Long) = memoDao.deleteMemoEntity(id)
}