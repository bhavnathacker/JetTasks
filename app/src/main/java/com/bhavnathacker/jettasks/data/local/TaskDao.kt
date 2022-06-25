package com.bhavnathacker.jettasks.data.local

import androidx.room.*
import com.bhavnathacker.jettasks.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_tbl ORDER BY id DESC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_tbl where id = :id")
    suspend fun getTask(id: Int?): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteTask(task: Task)
}
