package com.example.newsfeedapp.presenter

import com.example.newsfeedapp.sqlite.DBHandler

class NewsFeedPresenter:NewsFeedInteractor.Presenter {

    override fun getUserName(db:DBHandler): String {
        return db.getUserName()
    }
}