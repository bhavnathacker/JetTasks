package com.bhavnathacker.jettasks.domain.use_cases

interface EnableSortByDeadline {
    suspend operator fun invoke(enable: Boolean)
}