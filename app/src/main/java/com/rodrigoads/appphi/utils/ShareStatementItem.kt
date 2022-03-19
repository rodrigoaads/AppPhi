package com.rodrigoads.appphi.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ShareStatementItem {

    fun getScreenshot(view: View, context: Context): File {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return saveScreenshot(bitmap, context)
    }

    @Suppress("TooGenericExceptionCaught")
    private fun saveScreenshot(bitmap: Bitmap, context: Context): File {
        val date = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date)

        val file = File(context.externalCacheDir, "$date.png")
        try {
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, SCR_QUALITY, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            print(e)
        }
        return file
    }

    companion object {
        private const val SCR_QUALITY = 100
    }
}