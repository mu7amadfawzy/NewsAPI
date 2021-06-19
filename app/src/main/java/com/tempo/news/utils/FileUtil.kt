package com.tempo.news.utils

import android.graphics.Bitmap
import android.os.Environment
import java.io.*

object FileUtil {
    private val NOT_FOUND = -1
    private val UNIX_SEPARATOR = '/'
    private val WINDOWS_SEPARATOR = '\\'

    fun isType(filePath: String?, extension: String?): Boolean {
        return if (filePath == null) {
            false
        } else getExtension(filePath).equals(
            extension,
            ignoreCase = true
        )
    }

    fun getExtension(filePath: String?): String {
        if (filePath == null) {
            return ""
        }
        val index = indexOfExtension(filePath)
        return if (index == NOT_FOUND) {
            ""
        } else {
            filePath.substring(index + 1)
        }
    }

    private fun indexOfExtension(filePath: String?): Int {
        if (filePath == null) {
            return NOT_FOUND
        }
        val extensionPos = filePath.lastIndexOf(".")
        val lastSeparator = indexOfLastSeparator(filePath)
        return if (lastSeparator > extensionPos) NOT_FOUND else extensionPos
    }

    private fun indexOfLastSeparator(filePath: String?): Int {
        if (filePath == null) {
            return NOT_FOUND
        }
        val lastUnixPos = filePath.lastIndexOf(UNIX_SEPARATOR)
        val lastWindowsPos = filePath.lastIndexOf(WINDOWS_SEPARATOR)
        return Math.max(lastUnixPos, lastWindowsPos)
    }

    fun CreateFile(filename: String?, bitmap: Bitmap?): File? {
        if (bitmap == null) return null
        val f = File(Environment.getExternalStorageDirectory(), filename)
        try {
            f.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 5 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()
        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            return f
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return f
    }
}