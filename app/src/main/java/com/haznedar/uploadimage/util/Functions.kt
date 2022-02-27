package com.haznedar.uploadimage.util

import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun internetCheck(): Boolean = withContext(Dispatchers.IO)
{

    val runTime = Runtime.getRuntime()

    try {

        val ipProcess = runTime.exec("/system/bin/ping -c 1 www.google.com")
        val exitValue: Int = ipProcess.waitFor()
        return@withContext (exitValue == 0)

    } catch (e: IOException) {

        e.printStackTrace()

    } catch (e: InterruptedException) {

        e.printStackTrace()
    }

    return@withContext false
}

fun ValueChecker(value:EditText) : Boolean{

    return value.text.toString().trim().isNotEmpty()

}

fun ValueChecker(value:String) : Boolean{

    return value.trim().isNotEmpty()

}