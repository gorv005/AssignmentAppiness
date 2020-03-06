package com.appiness.assignmentappiness.ui

import androidx.lifecycle.MutableLiveData
import com.appiness.assignmentappiness.base.AbstractViewModel
import com.appiness.assignmentappiness.interfaces.SchedulerProvider
import com.appiness.assignmentappiness.model.ResultResponseData
import com.appiness.assignmentappiness.model.ResultResponsePayload
import com.appiness.assignmentappiness.model.SearchEvent
import com.appiness.assignmentappiness.repository.MainRepository


class MainViewModel(
    private val mainRepo: MainRepository,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {

    val dataList = MutableLiveData<ArrayList<ResultResponseData>>()
    val searchEvent = MutableLiveData<SearchEvent>()


    fun loadingData() {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            mainRepo.getList()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .doAfterTerminate {
                    searchEvent.value = SearchEvent(isLoading = false, isSuccess = true)
                }
                .subscribe({
                    dataList.value = it
                }, {
                    searchEvent.value = SearchEvent(isLoading = false, isSuccess = false)

                })
        }
    }


}