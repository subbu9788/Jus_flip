package com.be.positive

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.be.positive.utils.ImageUtils
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.image_viewer.*
import java.io.File


class ProfileImageView : AppCompatActivity() {

    var imagePath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val bundle = intent
        title = bundle.getStringExtra("title")
        setContentView(R.layout.image_viewer)
        try {

            val imageType = bundle.getStringExtra("imageType")
            if (bundle.hasExtra("imageLogo")) {
                imagePath = bundle.getStringExtra("imageLogo")
            }
            Log.d("imageViewTrans", "$imageType: $imagePath")
            if (imageType == "local") {
                if (imagePath.isNotEmpty()) {
                    //ImageUtils.setImage(imProfilePic, imagePath, this@ProfileImageView)
                    imProfilePic.setImageURI(Uri.fromFile(File(imagePath)))
                }
            } else {
                if (imagePath.isNotEmpty()) {
                    ImageUtils.setImageLiveRealSize(imProfilePic, imagePath, this@ProfileImageView)
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}