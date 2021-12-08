package com.example.newsfeedapp.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.newsfeedapp.model.User

class DBHandler(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "NewsFeed"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "User"
        private const val COL_ID = "_Id"
        private const val COL_FIRST_NAME = "FirstName"
        private const val COL_LAST_NAME = "LastName"
        private const val COL_EMAIL_ADDRESS = "Email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FIRST_NAME + " TEXT,"
                + COL_LAST_NAME + " TEXT,"
                + COL_EMAIL_ADDRESS + " TEXT)")

        db!!.execSQL(query)
    }

    fun saveUserDetails(user:User):Boolean{
        var isSaved = true
        try {
            val db = writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COL_FIRST_NAME,user.firstName)
            contentValues.put(COL_LAST_NAME,user.lastName)
            contentValues.put(COL_EMAIL_ADDRESS,user.email)

            db.insert(TABLE_NAME,null,contentValues)
            db.close()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            isSaved = false
        }
        return isSaved
    }

    fun getUserCount():Int {
        var count = 0
        try {
            val db = readableDatabase
            val query = "SELECT COUNT(*) as Count From User"
            var cursor:Cursor = db.rawQuery(query,null)
            while (cursor.moveToNext()){
                count = cursor.getInt(cursor.getColumnIndex("Count"))
            }
            cursor.close()
            db.close()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
        return count
    }

    fun getUserName():String {
        var firstName = ""
        try {
            val db = readableDatabase
            val query = "SELECT FirstName From User"
            var cursor:Cursor = db.rawQuery(query,null)
            while (cursor.moveToNext()){
                firstName = cursor.getString(cursor.getColumnIndex("FirstName"))
            }
            cursor.close()
            db.close()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
        return firstName
    }

    fun getUserDetail():User {
        var user = User("","","")
        try {
            val db = readableDatabase
            val query = "Select FirstName,LastName,Email From User"
            var cursor:Cursor = db.rawQuery(query,null)

            while (cursor.moveToNext()){
                user = User(cursor.getString(cursor.getColumnIndex("FirstName")),
                                cursor.getString(cursor.getColumnIndex("LastName")),
                                cursor.getString(cursor.getColumnIndex("Email")))
            }
            cursor.close()
            db.close()
        }catch (e: Exception){
            e.printStackTrace()
        }

        return user
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}