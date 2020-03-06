package com.appiness.assignmentappiness.interfaces
import com.appiness.assignmentappiness.BuildConfig
import com.appiness.assignmentappiness.model.ResultResponseData
import com.appiness.assignmentappiness.model.ResultResponsePayload
import io.reactivex.Single
import retrofit2.http.*

interface RESTApi {

    @GET("json/api.json")
    fun getList(): Single<ArrayList<ResultResponseData>>


}