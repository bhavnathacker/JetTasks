package com.bhavnathacker.jettasks.data.dao

import androidx.room.*
import com.bhavnathacker.jettasks.data.model.DBTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_tbl ORDER BY id DESC")
    fun getTasks(): Flow<List<DBTask>>

    @Query("SELECT * FROM task_tbl where id = :id")
    suspend fun getTask(id: Int?): DBTask?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: DBTask)

    @Query("DELETE FROM task_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTask(task: DBTask)
}
