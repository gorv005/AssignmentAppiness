package com.appiness.assignmentappiness.impls

import com.appiness.assignmentappiness.interfaces.RESTApi
import com.appiness.assignmentappiness.model.ResultResponseData
import com.appiness.assignmentappiness.model.ResultResponsePayload
import com.appiness.assignmentappiness.repository.MainRepository

import io.reactivex.Single

class MainImpl(private val restApi: RESTApi) : MainRepository {

    override fun getList(): Single<ArrayList<ResultResponseData>> = restApi.getList()


}