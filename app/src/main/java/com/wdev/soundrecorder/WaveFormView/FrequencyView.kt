package com.wdev.soundrecorder.WaveFormView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class FrequencyView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private var paint = Paint()
    private var amplitudes = ArrayList<Float>()
    private var spikes = ArrayList<RectF>()

    private var radius = 6f
    private var w = 9f
    private var d = 6f

    private var sw = 0f
    private var sh = 80f

    private var maxSpikes = 0

    init {
        paint.color = Color.rgb(244,81,30)

        //sw = resources.displayMetrics.widthPixels.toFloat()

        //maxSpikes = (sw / (w+d)).toInt()

        for (i in 1..100){

        }
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        spikes.forEach{
            canvas?.drawRoundRect(it,radius,radius, paint)
        }

    }


}
