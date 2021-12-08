package com.example.newsfeedapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsFeed(@SerializedName("status")
                    @Expose
                    var status:String,
                    @SerializedName("totalResults")
                    @Expose
                    var totalResults:Int,
                    @SerializedName("articles")
                    @Expose
                    var articleList: ArrayList<Articles>){
}