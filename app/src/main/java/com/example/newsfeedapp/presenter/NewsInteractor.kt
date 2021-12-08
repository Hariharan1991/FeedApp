package com.example.newsfeedapp.presenter

import com.example.newsfeedapp.model.Articles
import com.example.newsfeedapp.retrofit.RetrofitAPIInterface

class NewsInteractor {
    interface NewsView {
        fun updateView(articleList:ArrayList<Articles>)
        fun showDialog()
        fun hideDialog()
    }
    interface Presenter {
        fun setView(mView:NewsView)
        fun fetchLatestNewsInfo(apiInterface: RetrofitAPIInterface)
    }
}