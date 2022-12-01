package com.wdev.soundrecorder.VoiceRecorder

import android.app.Activity
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wdev.soundrecorder.Controllers.ISoundController
import com.wdev.soundrecorder.DataInfo
import com.wdev.soundrecorder.R
import com.wdev.soundrecorder.WaveFormView.WaveFormView
import okhttp3.internal.addHeaderLenient
import java.io.*

class VoiceRecorder (nameFile: String,
                     context: Context,
                     textView: TextView,
                     button: ImageButton,
                    // waveFormView: WaveFormView,
                     activity: Activity)
    : ISoundController {

    private var context : Context
    private var activity : Activity
    private var button : ImageButton
    private var textView: TextView
    //private var waveFormView : WaveFormView

    var mediaRecorder: MediaRecorder


    private var output: String?
    private var nameFile : String = "recording-"+System.currentTimeMillis()


    private  var externalPath : String


    companion object{
        var state : Boolean = false
    }


    /**
     * Factor by that the minimum buffer size is multiplied. The bigger the factor is the less
     * likely it is that samples will be dropped, but more memory will be used. The minimum buffer
     * size is determined by [AudioRecord.getMinBufferSize] and depends on the
     * recording settings.
     */


    /**
     * Size of the buffer where the audio data is stored by Android
     */



    init{

        externalPath = DataInfo.getRoute(context)
        var route = File(externalPath)

        output = externalPath +  "${this.nameFile}"
        this.textView = textView
        this.context = context
        this.activity = activity
        this.button = button
        this.textView = textView
        //this.waveFormView = waveFormView

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        //    mediaRecorder = MediaRecorder(context)
       // }else{
        mediaRecorder = MediaRecorder()
       // }








        mediaRecorder.setOnInfoListener(MediaRecorder.OnInfoListener { mr, what, extra ->

            stopRecording()
            /*mediaRecorder.stop()
            waveFormView.clear()
            state = false
            button.isEnabled = false
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE*/
        })

    }

    fun setAudioFormat(audioFormat: String){

        this.nameFile = nameFile+"."+audioFormat
        output += "."+audioFormat

        when(audioFormat.toUpperCase()){

            "mp4".toUpperCase()->{

                //CODE FOR MP4 RECORDING
                mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                mediaRecorder?.setAudioEncodingBitRate(320000)
                mediaRecorder?.setAudioSamplingRate(48000)
                mediaRecorder?.setOutputFile(output)

                //AUDIO CHANEL 1:MONO 2:STEREO
                mediaRecorder?.setAudioChannels(2)
                Log.d("TAG", "MP4 Audio record")

            }
            "ogg".toUpperCase() ->{
                //CODE FOR OGG RECORDING
                mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.OGG)
                mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                mediaRecorder?.setAudioEncodingBitRate(320000)
                mediaRecorder?.setAudioSamplingRate(48000)
                mediaRecorder?.setOutputFile(output)
                mediaRecorder?.setMaxDuration(Int.MAX_VALUE)

            }

        }

    }

    fun stopRecording(){

        if(state)
        {

            //waveFormView.clear()
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
           // button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_record))
            textView.setText("")
           // button.isEnabled = false
           // button.visibility = View.INVISIBLE
           // textView.visibility = View.INVISIBLE

        }
        else
        {
            Toast.makeText(context, R.string.SinGrabacionEnCurso, Toast.LENGTH_SHORT).show()
        }

        try {

            var audio = File(output)


            if(audio.exists()){

                Toast.makeText(context, "¡Grabacion $nameFile guardada!", Toast.LENGTH_SHORT).show()
                //audio.delete()
            }

        }catch (e: Exception){
            Log.d("EXCEPTION", e.toString())
        }


    }

    fun startRecording() {

        try
        {

            if(!state){

                mediaRecorder?.prepare()
                mediaRecorder?.start()
                state = true
                textView.setText(R.string.grabando)
                //button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stop));
               // button.isVisible = true
                //button.isEnabled = true
                //Toast.makeText(context, "¡Iniciando grabación!", Toast.LENGTH_SHORT).show()
            }

        }
        catch (e: IllegalStateException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
    }

}