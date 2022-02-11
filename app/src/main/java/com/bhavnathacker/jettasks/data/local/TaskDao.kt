package com.bhavnathacker.jettasks.data.local

import androidx.room.*
import com.bhavnathacker.jettasks.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * from task_tbl ORDER BY id DESC")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE from task_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTask(task: Task)
}
