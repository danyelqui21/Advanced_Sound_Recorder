package mx.com.bancoazteca.innovacion.encuestasatisfaccion.VoiceRecorder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.R
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.WaveFormView.WaveFormView
import java.io.IOException

class VoiceRecorder () {

    private lateinit var context : Context
    private lateinit var activity : Activity
    private lateinit var button : FloatingActionButton
    private lateinit var textView: TextView
    private lateinit var waveFormView : WaveFormView

    fun VoiceRecorder(context: Context, activity: Activity, button: FloatingActionButton, textView: TextView, waveFormView: WaveFormView){

        this.context = context
        this.activity = activity
        this.button = button
        this.textView = textView
        this.waveFormView = waveFormView

    }


    lateinit var mediaRecorder: MediaRecorder
    var state : Boolean = false
    private var output: String? = null


    fun CheckPermissions(){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity, permissions,0)
        }
    }

    fun Inicialize(guid: String){

        output = Environment.getExternalStorageDirectory().absolutePath + "/${guid}.mp3"
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setAudioEncodingBitRate(320000)
        mediaRecorder?.setAudioSamplingRate(48000)
        mediaRecorder?.setOutputFile(output)
        mediaRecorder?.setMaxDuration(15000)
        mediaRecorder.setOnInfoListener(MediaRecorder.OnInfoListener { mr, what, extra ->
            Toast.makeText(context, R.string.GraciasGrabacion, Toast.LENGTH_SHORT).show()
            waveFormView.clear()
            state = false
            button.isEnabled = false
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
        })

    }

    fun stopRecording(){
        if(state)
        {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_record));
            button.isEnabled = false
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            waveFormView.clear()
            Toast.makeText(context, R.string.GraciasGrabacion, Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, R.string.SinGrabacionEnCurso, Toast.LENGTH_SHORT).show()
        }
    }

     fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            textView.setText(R.string.grabando)
            button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stop));
            //Toast.makeText(context, "¡Iniciando grabación!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



}