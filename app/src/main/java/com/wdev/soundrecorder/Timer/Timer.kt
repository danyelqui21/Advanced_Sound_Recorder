package com.wdev.soundrecorder.Timer

import android.os.Looper

class Timer (listener: OnTimerTickListener){


    interface OnTimerTickListener{
        fun OnTimerTick(duration: String)
    }

    private var handler = android.os.Handler(Looper.getMainLooper())
    private lateinit var  runnable: Runnable

    var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable {
            duration -= delay
            handler.postDelayed(runnable, delay)
            listener.OnTimerTick(format())
        }
    }

    fun start(){
        handler.postDelayed(runnable, delay)
    }

    fun pause(){
        handler.removeCallbacks(runnable)
    }

    fun stop(){
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    fun format() : String {

        val millis: Long = duration % 1000
        val seconds: Long = (duration / 1000) % 60
        val minutes: Long = (duration / (1000 * 60)) % 60
        val hours: Long = (duration / (1000 * 60 * 60))

        var formatted =
            "%02d.%02d".format(seconds, millis)

        return formatted + " s"

    }


}