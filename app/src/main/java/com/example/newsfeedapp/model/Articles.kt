package com.example.newsfeedapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Articles(@SerializedName("author")
                    @Expose
                    var author:String,
                    @SerializedName("title")
                    @Expose
                    var title:String,
                    @SerializedName("url")
                    @Expose
                    var url:String,
                    @SerializedName("publishedAt")
                    @Expose
                    var publishedAt:String,
                    @SerializedName("urlToImage")
                    @Expose
                    var urlToImage:String,
                    @SerializedName("content")
                    @Expose
                    var content:String,
                    @SerializedName("description")
                    @Expose
                    var description:String,
                    @SerializedName("source")
                    @Expose
                    var source:Source)