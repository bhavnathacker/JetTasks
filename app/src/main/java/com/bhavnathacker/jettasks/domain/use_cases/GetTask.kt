package com.bhavnathacker.jettasks.domain.use_cases

import com.bhavnathacker.jettasks.domain.model.Task

interface GetTask {
    suspend operator fun invoke(id: Int?): Task?
}