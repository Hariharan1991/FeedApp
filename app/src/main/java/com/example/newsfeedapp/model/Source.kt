package com.example.newsfeedapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Source(@SerializedName("id")
                  @Expose
                  var id:String,
                  @SerializedName("Name")
                  @Expose
                  var name:String)