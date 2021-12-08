package com.example.newsfeedapp.presenter

import com.example.newsfeedapp.model.User
import com.example.newsfeedapp.sqlite.DBHandler

class ProfilePresenter:BasePresenter() {

    fun getUserDetail(dbHandler: DBHandler):User{
        return dbHandler.getUserDetail()
    }
}