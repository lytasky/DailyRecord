package com.lytasky.dailyrecord.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Build
import android.os.Environment
import java.io.*


object FileUtils {
    fun saveBitmap2File(bitmap: Bitmap, filePath: String): Boolean {
        if (bitmap == null || bitmap.isRecycled) {
            return false
        }
        if (!createFileByDeleteOldFile(filePath)) {
            return false
        }
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(filePath))
            ret = bitmap.compress(CompressFormat.PNG, 100, os)
            if (!bitmap.isRecycled) bitmap.recycle()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ret
    }

    private fun createFileByDeleteOldFile(filePath: String): Boolean {
        var file: File = File(filePath) ?: return false;
        if (file.exists() && !file.delete()) return false
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }
}