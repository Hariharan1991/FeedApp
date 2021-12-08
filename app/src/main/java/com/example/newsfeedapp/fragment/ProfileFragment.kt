package com.example.newsfeedapp.fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newsfeedapp.R
import com.example.newsfeedapp.presenter.ProfilePresenter
import com.example.newsfeedapp.sqlite.DBHandler
import java.io.FileNotFoundException
import java.io.IOException

class ProfileFragment: Fragment() {

    lateinit var tvFirstName:TextView
    lateinit var tvLastName:TextView
    lateinit var tvEmailAddress:TextView
    lateinit var ivProfileImage:ImageView
    lateinit var profilePresenter:ProfilePresenter
    lateinit var db:DBHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.fragment_profile,null)

        initialize(view)
        setUpProfileImage()
        return view
    }

    private fun initialize(view:View){

        db = DBHandler(requireActivity())

        tvFirstName = view.findViewById(R.id.tv_FirstName)
        tvLastName = view.findViewById(R.id.tv_LastName)
        tvEmailAddress = view.findViewById(R.id.tv_EmailAddress)
        ivProfileImage = view.findViewById(R.id.iv_profile_image)

        var user = db.getUserDetail()
        profilePresenter = ProfilePresenter()
        tvFirstName.text = "FirstName : " +user.firstName
        tvLastName.text = "LastName : " +user.lastName
        tvEmailAddress.text = "Email : " + user.email
    }

    private fun setUpProfileImage(){
        var photoFile = profilePresenter.getPhotoFileUri(requireActivity(), "Image123")
        if(!photoFile!!.exists())
            return

        if(profilePresenter.getBitmap(requireContext(),photoFile)!=null)
            ivProfileImage.setImageBitmap(profilePresenter.getBitmap(requireContext(),photoFile))
    }


}