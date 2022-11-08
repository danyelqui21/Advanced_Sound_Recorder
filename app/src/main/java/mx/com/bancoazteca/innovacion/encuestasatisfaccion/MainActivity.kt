package mx.com.bancoazteca.innovacion.encuestasatisfaccion

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Controllers.MyRecyclerAdapter
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.AnswerSurvey
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.Questions
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.TextFileReader
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.preguntas
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Timer.Timer
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.VoiceRecorder.VoiceRecorder
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.WaveFormView.WaveFormView
import java.io.File
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), Timer.OnTimerTickListener {


    private lateinit var datoObtenido: Questions
    lateinit var recyclerView : RecyclerView
    lateinit var ivLogo: ImageView
    lateinit var tvEnviar: TextView
    var recyclerviewAdapter : MyRecyclerAdapter = MyRecyclerAdapter()
    lateinit var tvTipoOperacion: TextView
    val  externalPath = Environment.getExternalStorageDirectory().toString() + File.separator
    val voiceRecorder = VoiceRecorder()
    lateinit var timer : Timer


    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var buttonRecord : FloatingActionButton
    private lateinit var tvRecord: TextView
    private lateinit var waveFormView : WaveFormView
    private lateinit var textFileReader : TextFileReader
    private val MY_PERMISSIONS_RECORD_AUDIO = 1
    var AudioRecordingEnabled : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyStoragePermissions(this)
        requestAudioPermissions()

        ivLogo = findViewById(R.id.ivLogo)
        tvEnviar = findViewById(R.id.tvEnviar)
        tvTipoOperacion = findViewById(R.id.tvTipoOperacion)
        buttonRecord = findViewById(R.id.button_record)
        tvRecord = findViewById(R.id.tvTextoGrabacion)
        waveFormView = findViewById(R.id.waveFormView)


        textFileReader = TextFileReader()

        Picasso.get()
            .load(R.drawable.logobancoazteca)
            .into(ivLogo)

        tvEnviar.setOnClickListener( {
            getTextFile()
        })



        try {

            val file = File(externalPath,textFileReader.answerJson)
            if(file.exists()){
                file.delete()
            }

        }catch (ex: Exception){

        }

        try{
            val gson = Gson()

            datoObtenido = gson.fromJson<Questions>(getTextFile(), Questions::class.java)
            tvTipoOperacion.setText(datoObtenido.TipoRetiro)
            var x = ArrayList<preguntas>()

            for (i in datoObtenido.Preguntas){
                x.add(i)
            }

            AudioRecordingEnabled = datoObtenido.GrabacionAudioActivo

            setUpRecyclerView(x)

            var file2 = File(externalPath,"EncuestaConfig.json")

            if(file2.exists()){
                file2.delete()
            }

        }
        catch (ex : Exception){
            Log.d("DEBUG", "Excepción al serializar archivo de configuración "+ex)
        }

        timer = Timer(this)

        timer.duration = datoObtenido.TiempoParaGrabarEncuesta


        voiceRecorder.VoiceRecorder(applicationContext, this, buttonRecord, tvRecord, waveFormView)
        voiceRecorder.Inicialize(datoObtenido.Guid, datoObtenido.TiempoParaGrabarEncuesta.toInt())


        buttonRecord.setOnClickListener({
            if (voiceRecorder.state){
                voiceRecorder.stopRecording()
                timer.stop()
                waveFormView.clear()
            }else{

                if(AudioRecordingEnabled){
                    voiceRecorder.startRecording()
                    timer.start()
                }

            }
        })


        TimerExit()

    }


    private fun requestAudioPermissions() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG)
                    .show()

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO),
                    MY_PERMISSIONS_RECORD_AUDIO
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            == PackageManager.PERMISSION_GRANTED
        ) {

        }
    }



    fun setUpRecyclerView(r : MutableList<preguntas>){
        recyclerView = findViewById(R.id.rvEncuesta) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerviewAdapter.MyRecyclerAdapter(r, applicationContext, tvEnviar, this)
        recyclerView.adapter = recyclerviewAdapter
        recyclerviewAdapter.notifyDataSetChanged()

    }


    fun getQuestions(): MutableList<Questions>{
        var questions:MutableList<Questions> = ArrayList()
        return questions
    }

    fun getTextFile() : String? {

        val data: String? = textFileReader.getJsonFileData(externalPath+File.separator+"EncuestaConfig.json")
        Log.d("JsonData", data.toString())
        return data
    }



    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE,
            )
        }

        if (ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(activity, permissions,0)
        }
    }


    fun TimerExit(){

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            //Background work here
            handler.post {


                object: CountDownTimer(15000, 1000){
                    override fun onTick(p0: Long) {}
                    override fun onFinish() {

                        var file = File(externalPath,textFileReader.answerJson)

                        if(voiceRecorder.state == false){

                            if(!file.exists())
                            {
                                textFileReader.WriteAnswer(AnswerSurvey(0,""), false)
                                System.exit(0)
                            }
                            else
                            {
                                if(AudioRecordingEnabled){
                                    voiceRecorder.startRecording()
                                    timer.start()
                                }

                            }

                        }
                        
                    }

                }.start()

            }
        }

    }



    private fun stopRecorder(){
        timer.stop()
    }

    override fun OnTimerTick(duration: String) {

        try {
            tvRecord.setText("Grabando... ${ if(duration.replace("s","").toFloat()>0F){ duration} else {"00.000 s"}}")
        }catch (Ex: Exception){

        }

        try {
            waveFormView.addAmplitude(voiceRecorder.mediaRecorder.maxAmplitude.toFloat())
        }catch (ex: Exception){

        }

    }


}