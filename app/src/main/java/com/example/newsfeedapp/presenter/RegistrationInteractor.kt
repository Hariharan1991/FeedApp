package com.example.newsfeedapp.presenter

import android.content.Context
import com.example.newsfeedapp.model.User
import com.example.newsfeedapp.sqlite.DBHandler

class RegistrationInteractor{
    interface RegistrationView {
        fun onSuccess(message:String)
        fun onError(message:String)
    }
    interface Presenter {
        fun setView(mView:RegistrationInteractor.RegistrationView)
        fun validateFields(context: Context,firstName:String,lastName:String,email:String,password:String):Boolean
        fun validateEmail(email:String):Boolean
        fun saveUserDetails(context: Context,db:DBHandler,user:User)
    }
}