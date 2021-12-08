package com.example.newsfeedapp.presenter

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.User
import com.example.newsfeedapp.sqlite.DBHandler
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrationPresenter: BasePresenter(),RegistrationInteractor.Presenter {

    var mView:RegistrationInteractor.RegistrationView?=null

    override fun setView(mView: RegistrationInteractor.RegistrationView) {
        this.mView = mView
    }

    override fun validateFields(context: Context, firstName: String, lastName: String, email: String, password: String): Boolean {
        if(firstName.equals("", ignoreCase = true)){
            mView!!.onError(context.resources.getString(R.string.empty_first_name))
            return false
        }else if(lastName.equals("", ignoreCase = true)){
            mView!!.onError(context.resources.getString(R.string.empty_last_name))
            return false;
        }else if(email.equals("", ignoreCase = true)){
            mView!!.onError(context.resources.getString(R.string.empty_email_address))
            return false;
        }else if(!validateEmail(email)){
            mView!!.onError(context.resources.getString(R.string.valid_email_address))
            return false;
        }else if(password.equals("", ignoreCase = true)){
            mView!!.onError(context.resources.getString(R.string.empty_password))
            return false;
        }else if(password.length < 5){
            mView!!.onError(context.resources.getString(R.string.valid_password))
            return false;
        }
        return true
    }
    override fun saveUserDetails(context: Context, db: DBHandler, user: User) {
        var isSaved = db.saveUserDetails(user)
        if(isSaved){
            mView!!.onSuccess(context.resources.getString(R.string.success))
        }
        else {
            mView!!.onSuccess(context.resources.getString(R.string.failure))
        }
    }

    override fun validateEmail(email: String):Boolean{
        val regex = "^(.+)@(.+)$"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email)

        return matcher.matches()
    }

}