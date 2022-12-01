package com.wdev.soundrecorder

import android.content.Context
import android.content.res.Resources
import android.os.Environment
import android.provider.Settings.System.getString
import java.io.File


class DataInfo() {



    companion object{

        fun getRoute(myContext: Context) : String{
            val savePath = Environment.getExternalStorageDirectory().toString() + File.separator + myContext.getString(R.string.SaveFolder) +File.separator

            val file = File(savePath)
            if(!file.exists()) file.mkdir()
            return savePath
        }

    }



}