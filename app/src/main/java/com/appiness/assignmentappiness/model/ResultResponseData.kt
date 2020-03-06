package com.appiness.assignmentappiness.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultResponseData(@SerializedName("country")
                              val country: String = "",
                              @SerializedName("end.time")
                              val endTime: String = "",
                              @SerializedName("s.no")
                              val sNo: Int = 0,
                              @SerializedName("num.backers")
                              val numBackers: String = "",
                              @SerializedName("blurb")
                              val blurb: String = "",
                              @SerializedName("title")
                              val title: String = "",
                              @SerializedName("type")
                              val type: String = "",
                              @SerializedName("amt.pledged")
                              val amtPledged: Int = 0,
                              @SerializedName("url")
                              val url: String = "",
                              @SerializedName("by")
                              val by: String = "",
                              @SerializedName("currency")
                              val currency: String = "",
                              @SerializedName("location")
                              val location: String = "",
                              @SerializedName("state")
                              val state: String = "",
                              @SerializedName("percentage.funded")
                              val percentageFunded: Int = 0): Parcelable