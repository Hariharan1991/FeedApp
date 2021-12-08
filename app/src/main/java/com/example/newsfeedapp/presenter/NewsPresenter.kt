package com.example.newsfeedapp.presenter

import com.example.newsfeedapp.model.Articles
import com.example.newsfeedapp.model.NewsFeed
import com.example.newsfeedapp.retrofit.RetrofitAPIInterface
import com.example.newsfeedapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsPresenter:NewsInteractor.Presenter {

    var mView:NewsInteractor.NewsView?=null

    override fun setView(mView: NewsInteractor.NewsView) {
       this.mView = mView
    }
    override fun fetchLatestNewsInfo(apiInterface: RetrofitAPIInterface){
        mView!!.showDialog()
        val newsFeedList:ArrayList<Articles> = ArrayList()

        var call:Call<NewsFeed> = apiInterface.getNewsFeedList(Constants.CATEGORY, Constants.API_KEY)

        call.enqueue(object : Callback<NewsFeed> {
            override fun onResponse(call: Call<NewsFeed>?, response: Response<NewsFeed>?) {
                newsFeedList.addAll(response!!.body().articleList)
                mView!!.updateView(newsFeedList)
            }

            override fun onFailure(call: Call<NewsFeed>?, t: Throwable?) {

            }
        })
    }
}