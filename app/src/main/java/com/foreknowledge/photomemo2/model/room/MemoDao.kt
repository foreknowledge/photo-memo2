package com.foreknowledge.photomemo2.model.room

import androidx.room.*

/**
 * Create by Yeji on 22,April,2020.
 */
@Dao
interface MemoDao {
	@Query("SELECT * FROM MemoEntity")
	suspend fun getAllMemoEntities(): List<MemoEntity>

	@Query("SELECT * FROM MemoEntity WHERE id = :id")
	suspend fun getMemoEntity(id: Long): MemoEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addMemoEntity(memoEntity: MemoEntity)

	@Query("DELETE FROM MemoEntity WHERE id = :memoId")
	suspend fun deleteMemoEntity(memoId: Long)
}