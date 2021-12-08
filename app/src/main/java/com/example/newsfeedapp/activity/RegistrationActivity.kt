package com.example.newsfeedapp.activity

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.newsfeedapp.R
import com.example.newsfeedapp.model.User
import com.example.newsfeedapp.presenter.RegistrationInteractor
import com.example.newsfeedapp.presenter.RegistrationPresenter
import com.example.newsfeedapp.sqlite.DBHandler
import java.io.FileNotFoundException
import java.io.IOException


class RegistrationActivity : AppCompatActivity(),RegistrationInteractor.RegistrationView,View.OnClickListener {

    lateinit var db:DBHandler
    lateinit var edFirstName:EditText
    lateinit var edLastName:EditText
    lateinit var edEmail:EditText
    lateinit var edPassword:EditText
    lateinit var ivImageCapture:ImageView
    lateinit var btnRegister:Button

    lateinit var registrationPresenter: RegistrationPresenter
    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val REQUEST_ID_MULTIPLE_PERMISSIONS = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initialize()
        if(db.getUserCount() > 0){
            navigateToNewsFeedScreen()
            finish()
        }
        checkAndRequestPermissions()
    }

    private fun initialize(){
        db = DBHandler(this)
        edFirstName = findViewById(R.id.ed_first_name)
        edLastName = findViewById(R.id.ed_last_name)
        edEmail = findViewById(R.id.ed_email_address)
        edPassword = findViewById(R.id.ed_password)
        ivImageCapture = findViewById(R.id.iv_image_capture)
        btnRegister = findViewById(R.id.btn_register)
        ivImageCapture.setOnClickListener(this)
        btnRegister.setOnClickListener(this)

        registrationPresenter = RegistrationPresenter()
        registrationPresenter.setView(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_register -> {
                val isValid = registrationPresenter.validateFields(this,
                        edFirstName.text.toString(), edLastName.text.toString(),
                        edEmail.text.toString(), edPassword.text.toString())

                if (isValid) {
                    val user = User(edFirstName.text.toString(), edLastName.text.toString(),
                            edEmail.text.toString())
                    registrationPresenter.saveUserDetails(this, db, user)
                }
            }
            R.id.iv_image_capture -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photoFile = registrationPresenter.getPhotoFileUri(this, "Image123")
                if (photoFile != null) {
                    val fileProvider: Uri = FileProvider.getUriForFile(this,
                                    "com.example.newsfeedapp.fileprovider", photoFile!!)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST_CODE)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    val photoFile = registrationPresenter.getPhotoFileUri(this, "Image123")
                    if (!photoFile!!.exists())
                        return

                    if(registrationPresenter.getBitmap(this,photoFile)!=null)
                        ivImageCapture.setImageBitmap(registrationPresenter.getBitmap(this,photoFile))
                }
            }
        }
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        navigateToNewsFeedScreen()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun navigateToNewsFeedScreen(){
        val intent = Intent(this, NewsFeedActivity::class.java)
        startActivity(intent)
    }


    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
        val wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                if (grantResults.isNotEmpty()) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK(resources.getString(R.string.camera_and_storage)
                            ) { _, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                    DialogInterface.BUTTON_NEGATIVE -> {
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(this, resources.getString(R.string.enable_permission), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }
    
}