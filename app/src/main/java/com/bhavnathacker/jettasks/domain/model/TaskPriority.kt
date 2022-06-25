package com.bhavnathacker.jettasks.domain.model

enum class TaskPriority {
    HIGH, MEDIUM, LOW;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.name
            }
        }
    }
}