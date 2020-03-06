package com.appiness.assignmentappiness.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.appiness.assignmentappiness.R
import com.appiness.assignmentappiness.base.BaseActivity
import com.appiness.assignmentappiness.interfaces.NetworkListener
import com.appiness.assignmentappiness.interfaces.OnClickItem
import com.appiness.assignmentappiness.model.ResultResponseData
import com.appiness.assignmentappiness.utils.AndroidUtils
import com.appiness.assignmentappiness.utils.CommonInt
import com.appiness.assignmentappiness.utils.UiUtils
import com.appiness.assignmentappiness.utils.Util
import com.appstreet.top_github.ui.githubList.adapter.AdapterResultList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class),
    OnClickItem<ResultResponseData>, NetworkListener {


    override fun title(): String = ""


    override fun titleColor(): Int = R.color.white

    override fun onClick(
        position: Int,
        t: ResultResponseData?
    ) {
    }

    override fun tryAgain(boolean: Int) {
        loadData()
    }

    private var adapterResultList: AdapterResultList? = null
    private var dataItems: ArrayList<ResultResponseData> = ArrayList()

    override fun layout(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rv_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        subscribeLoading()
        subscribeUI()
        loadData()
    }

    private fun loadData() {
        if (Util.isInternetAvailable(this)) {
            model.loadingData()
        } else {
            Util.onShowDialog(this, this, CommonInt.One)

        }
    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@MainActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUI() {
        model.dataList.observe(this, Observer {
            dataItems = it

            adapterResultList =
                AdapterResultList(dataItems, this@MainActivity, this@MainActivity)

            rv_list.adapter = adapterResultList
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchViewItem = menu?.findItem(R.id.action_search)
        searchViewItem?.expandActionView()
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapterResultList!!.getFilter().filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
