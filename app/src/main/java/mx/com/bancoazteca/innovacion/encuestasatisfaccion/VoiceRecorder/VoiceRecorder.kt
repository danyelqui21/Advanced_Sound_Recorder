package mx.com.bancoazteca.innovacion.encuestasatisfaccion.VoiceRecorder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.AnswerSurvey
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.TextFileReader
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.R
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.WaveFormView.WaveFormView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class VoiceRecorder () {

    private lateinit var context : Context
    private lateinit var activity : Activity
    private lateinit var button : FloatingActionButton
    private lateinit var textView: TextView
    private lateinit var waveFormView : WaveFormView
    val externalPath = Environment.getExternalStorageDirectory().toString() + File.separator
    private lateinit var guid : String

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

    fun Inicialize(guid: String, time: Int){

        this.guid = guid
        output = externalPath + "/${guid}.mp3"
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setAudioEncodingBitRate(320000)
        mediaRecorder?.setAudioSamplingRate(48000)
        mediaRecorder?.setOutputFile(output)
        mediaRecorder?.setMaxDuration(time)
        mediaRecorder.setOnInfoListener(MediaRecorder.OnInfoListener { mr, what, extra ->
            Toast.makeText(context, R.string.GraciasGrabacion, Toast.LENGTH_SHORT).show()
            stopRecording()
            /*mediaRecorder.stop()
            waveFormView.clear()
            state = false
            button.isEnabled = false
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE*/
        })

    }

    fun stopRecording(){

        if(state)
        {

            waveFormView.clear()
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_record));
            button.isEnabled = false
            button.visibility = View.INVISIBLE
            textView.visibility = View.INVISIBLE
            Toast.makeText(context, R.string.GraciasGrabacion, Toast.LENGTH_SHORT).show()


        }
        else
        {
            Toast.makeText(context, R.string.SinGrabacionEnCurso, Toast.LENGTH_SHORT).show()
        }

        try
        {
            Log.d("File", "Creando archivo de respuesta")
            var textFileReader = TextFileReader()
            val file = File(textFileReader.answerTemporalJson)
            val gson = Gson()

            if(file.exists()){
                Log.d("File","Archivo existe, lo vamos a leer y le vamos insertar el audio")


                var textFromFile = textFileReader.getJsonFileData(textFileReader.answerTemporalJson)
                var datoObtenido = gson.fromJson<AnswerSurvey>(textFromFile, AnswerSurvey::class.java)

                datoObtenido.audioAnswer = encodeAudio(output.toString()).replace("\n", "")
                textFileReader.WriteAnswer(datoObtenido, false)

            }

        }
        catch (ex: Exception)
        {

            Log.d("Exception", ex.toString())
        }

        try {

            var audio = File(externalPath + "/${guid}.mp3")

            if(audio.exists()){
                audio.delete()
            }

        }catch (e: Exception){
            Log.d("EXCEPTION", e.toString())
        }


    }

    private fun encodeAudio(selectedPath: String) : String {
        val audioBytes: ByteArray
        try {

            // Just to check file size.. Its is correct i-e; Not Zero
            val audioFile = File(selectedPath)
            val fileSize = audioFile.length()
            val baos = ByteArrayOutputStream()
            val fis = FileInputStream(File(selectedPath))
            val buf = ByteArray(1024)
            var n: Int
            while (-1 != fis.read(buf).also { n = it }) baos.write(buf, 0, n)
            audioBytes = baos.toByteArray()

            // Here goes the Base64 string
            return  Base64.encodeToString(audioBytes, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            Log.d("Exception ", e.toString())
        }

        return ""
    }

    fun startRecording() {

        try
        {

            if(!state){

                mediaRecorder?.prepare()
                mediaRecorder?.start()
                state = true
                textView.setText(R.string.grabando)
                button.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_stop));
                button.isVisible = true
                button.isEnabled = true
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