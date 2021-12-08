package com.example.newsfeedapp.presenter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

open class BasePresenter {

    fun getPhotoFileUri(context: Context, fileName: String): File? {
        val mediaStorageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "APP_TAG")

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("APP_TAG", "failed to create directory")
        }

        return File(mediaStorageDir.path + File.separator.toString() + fileName)
    }
     fun getBitmap(context: Context,photoFile:File): Bitmap? {

         val uri = Uri.fromFile(photoFile)
         var bitmap: Bitmap?=null
         try {
             bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
         } catch (e: FileNotFoundException) {
             e.printStackTrace()
         } catch (e: IOException) {
             e.printStackTrace()
         }
         return bitmap
     }
}