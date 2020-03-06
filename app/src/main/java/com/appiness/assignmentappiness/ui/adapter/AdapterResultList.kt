package com.appstreet.top_github.ui.githubList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appiness.assignmentappiness.R
import com.appiness.assignmentappiness.interfaces.OnClickItem
import com.appiness.assignmentappiness.model.ResultResponseData
import kotlinx.android.synthetic.main.data_item.view.*


class AdapterResultList(var items: ArrayList<ResultResponseData>,
                        private val onClickItem: OnClickItem<ResultResponseData>,
                        val context: Context) :
    RecyclerView.Adapter<AdapterResultList.ViewHolder>(), Filterable {
    internal var mfilter: NewFilter
    var mFilteredList: ArrayList<ResultResponseData> = ArrayList()

    override fun getFilter(): Filter {
        return mfilter
    }
    init {
        mFilteredList=items
        mfilter = NewFilter(this@AdapterResultList)
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.data_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txt_title?.text = items?.get(position)?.title
        holder?.txt_by?.text = items?.get(position)?.by
        holder?.txt_backers?.text = items?.get(position)?.numBackers.toString()

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        internal var txt_title: TextView
        internal var txt_by: TextView
        internal var txt_backers: TextView

        init {

            txt_title = view.txt_title
            txt_by = view.txt_by
            txt_backers = view.txt_backers

        }
    }
    inner class NewFilter(var mAdapter: AdapterResultList) : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            if (charSequence.length == 0) {
                items=mFilteredList
            } else {
                 var filterItems: ArrayList<ResultResponseData> = ArrayList()

                val filterPattern = charSequence.toString().toLowerCase().trim { it <= ' ' }
                for (resultItem in mFilteredList!!) {
                    if (resultItem.title.toLowerCase().contains(filterPattern)) {
                        filterItems!!.add(resultItem)
                    }
                }
                items=filterItems
            }
            val results = FilterResults()

            results.values = items
            results.count = items!!.size
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            this.mAdapter.notifyDataSetChanged()
        }

    }
}


