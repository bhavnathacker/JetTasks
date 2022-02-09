package com.bhavnathacker.jettasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bhavnathacker.jettasks.data.model.Task
import com.bhavnathacker.jettasks.util.DateConverter

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}