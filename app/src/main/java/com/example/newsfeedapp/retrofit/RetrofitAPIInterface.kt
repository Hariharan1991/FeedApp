package com.example.newsfeedapp.retrofit


import com.example.newsfeedapp.model.NewsFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPIInterface {

    @GET("/v2/top-headlines?country=us")
    fun getNewsFeedList(@Query("category")
                                cat:String, @Query("apiKey") key:String):Call<NewsFeed>
}