package com.haznedar.uploadimage.presentation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.haznedar.uploadimage.databinding.ActivityMainBinding
import com.haznedar.uploadimage.presentation.viewmodel.MainViewModel
import com.haznedar.uploadimage.util.FileUtil
import com.haznedar.uploadimage.util.Status
import com.haznedar.uploadimage.util.SuccessBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import kotlin.math.pow

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val imagePathList = ArrayList<File?>()
    private val compressedImagePathList = ArrayList<File?>()

    companion object {
        private val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvImage.setOnClickListener {

            chooseImageGallery()

        }

        binding.btnUpload.setOnClickListener {

            if (imagePathList.size > 0){

                setCompressedImage()

            }else{

                Toast.makeText(this@MainActivity, "Didn't choose image!", Toast.LENGTH_LONG).show()

            }

        }

    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    //The compressing process is starting here.
    private fun setCompressedImage() {

        binding.pbLoading.visibility = View.VISIBLE

        var firstJob: Job? = null

        try {

            for (i in imagePathList) {

                if (i != null) {

                    var file: File?

                    firstJob = lifecycleScope.launch {

                        file = Compressor.compress(
                            this@MainActivity,
                            i
                        ) {
                            //resolution(1280, 720)
                            quality(70)
                            format(Bitmap.CompressFormat.JPEG)
                            size(2_097_152) // 2 MB
                        }

                        if (file != null) {
                            compressedImagePathList.add(file!!)
                        }
                    }
                }
            }

            firstJob?.invokeOnCompletion {

                startUpload()

            }

        } catch (e: Exception) {

            Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_LONG).show()

        }

    }

    private fun startUpload() {

        for (c in compressedImagePathList) {

            c?.let { i ->

                val requestBody = RequestBody.create(MediaType.parse("image/*"), i)
                val filePart = MultipartBody.Part.createFormData("file", i.name, requestBody)

                uploadPhotoObserveLiveData(filePart, returnSquare(i))

                Log.e("Image Name", i.name)
                Log.e("Image Path", c.path)
                Log.e("Image Size(Kb/Mb)", String.format("Size : %s", getReadableFileSize(i.length())))

            } ?: Log.e("Empty", "List")


        }

    }

    private fun uploadPhotoObserveLiveData(filePart: MultipartBody.Part, type: String) = with(viewModel) {

            uploadImageLiveData.removeObservers(this@MainActivity)

            viewModel.uploadImage(
                "1",
                type,
                "private key for web service",
                "private code for web service",
                filePart
            )

         uploadImageLiveData.observe(this@MainActivity) { data ->

                data?.let {

                    when (it.status) {

                        Status.SUCCESS -> {

                            when (it.data?.success) {

                                0 -> {

                                    binding.pbLoading.visibility = View.GONE
                                    Toast.makeText(this@MainActivity, it.data.message, Toast.LENGTH_LONG).show()

                                }

                                1 -> {

                                    binding.pbLoading.visibility = View.GONE

                                    val cikisSheet = SuccessBottomSheet
                                    cikisSheet.newInstance("").show(this@MainActivity.supportFragmentManager, "ad")

                                }
                            }
                        }


                        Status.LOADING -> {



                        }


                        Status.ERROR -> {

                            binding.pbLoading.visibility = View.GONE

                            Toast.makeText(this@MainActivity, it.data?.message ?: "Error", Toast.LENGTH_LONG).show()

                        }
                        Status.INTERNET -> {

                            Toast.makeText(this@MainActivity, "Internet Error!", Toast.LENGTH_LONG).show()

                        }
                    }
                }
            }

        }

    //You can know size of image this function.(200 KB, 750 Kb, 5 MB etc)
    private fun getReadableFileSize(size: Long): String {

        if (size <= 0) {
            return "0"
        }

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
    }

    //If the size of ımage is bigger than 10 MB(5, 15, 20 etc MB) You can block choose an image.
    private fun imageSize(file:File) : Boolean{

        val type = getReadableFileSize(file.length()).substringAfter(" ")

        val size = getReadableFileSize(file.length()).substringBefore(" ").replace(",",".")

        when(type){

            "B" -> {return false}

            "KB" -> {return true}

            "MB" -> {

                return size.toDouble() < 10

            }

            "GB" -> {return false}

            "TB" -> {return false}

        }

        return if (type == "MB"){

            size.toDouble() < 10.0


        }else{

            true

        }

    }

    //You can control type of image(Squre or Other)
    private fun returnSquare(file: File): String {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(File(file.path).absolutePath, options)
        val imageHeight = options.outHeight
        val imageWidth = options.outWidth

        return if (imageWidth / imageHeight == 1)
            "Square"
        else
            "Other"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            try {

                if (data != null) {

                    val file = data.data?.let { FileUtil.from(this, it) }

                    //If the size of the image is bigger than 10 Mb then the code doesn't add it to the list.
                    //If You don't wanna control the size of the image, You should use it without when structure.
                    when(imageSize(file!!)){

                        true -> {

                            imagePathList.add(file)
                            binding.ivImage.setImageURI(data.data)

                        }

                        false ->{

                            Toast.makeText(this, "Image size mustn't big than 10 MB..", Toast.LENGTH_LONG).show()

                        }
                    }

                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseImageGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}

private const val REQUEST_CODE = 13
