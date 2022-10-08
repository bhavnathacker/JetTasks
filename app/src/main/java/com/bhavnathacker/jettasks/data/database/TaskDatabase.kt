package com.bhavnathacker.jettasks.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bhavnathacker.jettasks.data.DateConverter
import com.bhavnathacker.jettasks.data.dao.TaskDao
import com.bhavnathacker.jettasks.data.model.DBTask
import com.bhavnathacker.jettasks.domain.entity.Task

@Database(entities = [DBTask::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}