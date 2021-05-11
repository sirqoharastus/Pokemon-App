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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class SecondImplementationActivity : AppCompatActivity() {
    lateinit var selectImage: Button
    lateinit var uploadImage: Button
    var REQUEST_CODE = 100
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_implementation)
        val imageView = findViewById<ImageView>(R.id.imageView)

        selectImage = findViewById(R.id.select_btn)
        uploadImage = findViewById(R.id.upload_btn)

        selectImage.setOnClickListener {
            if (isPermissionAllowed()) {
                selectImage()
            } else {
                askForPermission()
            }
            // selectImage()
        }
    }

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

    private fun isPermissionAllowed(): Boolean {
        return ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
    private fun selectImage() {
        // added media store incase of crash, easy debug, remove
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val path: Uri? = data.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
            imageView = findViewById(R.id.imageView)
            imageView.setImageBitmap(bitmap)
        }
    }
}
