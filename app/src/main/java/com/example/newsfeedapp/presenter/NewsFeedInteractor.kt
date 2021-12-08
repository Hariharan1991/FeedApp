package com.example.newsfeedapp.presenter

import com.example.newsfeedapp.sqlite.DBHandler

class NewsFeedInteractor {

    interface Presenter {
        fun getUserName(db: DBHandler):String
    }
}