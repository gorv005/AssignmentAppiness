package com.appiness.assignmentappiness.repository
import com.appiness.assignmentappiness.model.ResultResponseData
import com.appiness.assignmentappiness.model.ResultResponsePayload
import io.reactivex.Single

interface MainRepository {
    fun getList(): Single<ArrayList<ResultResponseData>>
}