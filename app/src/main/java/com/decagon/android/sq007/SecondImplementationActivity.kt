package com.decagon.android.sq007

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SecondImplementationActivity : AppCompatActivity() {
    lateinit var selectImage: Button
    lateinit var uploadImage: Button
    var REQUEST_CODE = 100
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView
    var selectedImage:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_implementation)
        val imageView = findViewById<ImageView>(R.id.imageView)
        // declaring views
        selectImage = findViewById(R.id.select_btn)
        uploadImage = findViewById(R.id.upload_btn)
        // setting selectimage button on clicklistener
        selectImage.setOnClickListener {
            if (isPermissionAllowed()) {
                selectImage()
            } else {
                askForPermission()
            }
        }

        uploadImage.setOnClickListener {
            uploadImageFunction()
        }
    }

    private fun uploadImageFunction() {
        val file = File(FileUtil.getPath(selectedImage!!, this)!!)
        val requestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name,requestBody)
        RetrofitClient.getImage().uploadImage(multipartBody).enqueue(object: Callback<ImageClass>{
            override fun onFailure(call: Call<ImageClass>, t: Throwable) {
                t.message.let { Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show() }
            }

            override fun onResponse(call: Call<ImageClass>, response: Response<ImageClass>) {
                if (response.isSuccessful){
                    response.body().let {
                        if (it != null) {
                            Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
    }

    // function to ask for permission to access phone storage
    private fun askForPermission(): Boolean {
        if (!isPermissionAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this as Activity,
                    READ_EXTERNAL_STORAGE
                )
            ) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(
                    this as Activity,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    REQUEST_CODE
                )
            }
            return false
        }
        return true
    }
    // creating permission alert dialog
    private fun showPermissionDeniedDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Permission Denied")
        alertDialog.setMessage("Please Allow Permission")
        alertDialog.setPositiveButton("USER PERMISSION") {
            dialog, which ->
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        alertDialog.setNegativeButton("CANCEL") {
            dialog, which ->
            dialog.dismiss()
        }
        alertDialog.create()
        alertDialog.show()
    }
    // function checks if permission is already allowed
    private fun isPermissionAllowed(): Boolean {
        return ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
    private fun selectImage() {
        // added media store incase of crash, easy debug, remove
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    // function to dispatch the image result to the imageview
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            imageView = findViewById(R.id.imageView)
            imageView.setImageBitmap(bitmap)
        }
    }
}
