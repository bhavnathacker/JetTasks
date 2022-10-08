package com.bhavnathacker.jettasks.domain.use_cases

interface EnableSortByPriority {
    suspend operator fun invoke(enable: Boolean)
}