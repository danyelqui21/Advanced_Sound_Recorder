package mx.com.bancoazteca.innovacion.encuestasatisfaccion

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.*
import android.speech.tts.Voice
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Controllers.MyRecyclerAdapter
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.Questions
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.preguntas
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.squareup.picasso.Picasso
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Timer.Timer
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.VoiceRecorder.VoiceRecorder
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.WaveFormView.WaveFormView
import java.io.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), Timer.OnTimerTickListener {


    private lateinit var datoObtenido: Questions
    lateinit var recyclerView : RecyclerView
    lateinit var ivLogo: ImageView
    lateinit var tvEnviar: TextView
    var recyclerviewAdapter : MyRecyclerAdapter = MyRecyclerAdapter()
    lateinit var tvTipoOperacion: TextView
    private val  pathAnswer = Environment.getExternalStorageDirectory().toString() + File.separator
    val voiceRecorder = VoiceRecorder()
    private lateinit var timer : Timer
    private val answerJson = "answerSurvey.json"

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var buttonRecord : FloatingActionButton
    private lateinit var tvRecord: TextView
    private var TimeRecording : Long = 15000L
    private lateinit var waveFormView : WaveFormView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyStoragePermissions(this)

        ivLogo = findViewById(R.id.ivLogo)
        tvEnviar = findViewById(R.id.tvEnviar)
        tvTipoOperacion = findViewById(R.id.tvTipoOperacion)
        buttonRecord = findViewById(R.id.button_record)
        tvRecord = findViewById(R.id.tvTextoGrabacion)
        waveFormView = findViewById(R.id.waveFormView)

        Picasso.get()
            .load(R.drawable.logobancoazteca)
            .into(ivLogo)

        tvEnviar.setOnClickListener( {
            getTextFile()
        })

        timer = Timer(this)
        timer.duration = TimeRecording

        try {
            val file = File(pathAnswer,answerJson)
            if(file.exists()){
                file.delete()
            }
        }catch (ex: Exception){

        }

        try{
            val gson : Gson = Gson()

            datoObtenido = gson.fromJson<Questions>(getTextFile(), Questions::class.java)
            tvTipoOperacion.setText(datoObtenido.TipoRetiro)
            var x = ArrayList<preguntas>()

            for (i in datoObtenido.Preguntas){
                x.add(i)
            }


            setUpRecyclerView(x)

            var file2 = File(pathAnswer,"EncuestaConfig.json")

            if(file2.exists()){
                file2.delete()
            }

        }
        catch (ex : Exception){
            Log.d("DEBUG", "Excepción al serializar archivo de configuración "+ex)
        }


        voiceRecorder.VoiceRecorder(applicationContext, this, buttonRecord, tvRecord, waveFormView)
        voiceRecorder.Inicialize(datoObtenido.Guid)

        buttonRecord.setOnClickListener({
            if (voiceRecorder.state){
                voiceRecorder.stopRecording()
                timer.stop()
                waveFormView.clear()
            }else{
                voiceRecorder.startRecording()
                timer.start()
            }
        })


        TimerExit()

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

        val data: String? = getTextFileData(File.separator+"EncuestaConfig.json")
        Log.d("JsonData", data.toString())
        return data
    }

    fun getTextFileData(fileName: String): String? {

        var jsonResponse : String? = ""
        // Get the dir of SD Card
        val sdCardDir = Environment.getExternalStorageDirectory()
        // Get The Text file
        val txtFile = File(sdCardDir, fileName)

        if (txtFile.exists()) {

            var jsonString : String? = ""
            try {


                val br = BufferedReader(FileReader(txtFile))

                var line: String?
                while (br.readLine().also { line = it } != null) {
                    jsonString += line!!.replace("\n", "")
                }

                jsonResponse = jsonString

            } catch (e: UnsupportedEncodingException) {
                Log.i(
                    "AztecaApp - Intentos",
                    "**** EL ARCHIVO DEBE ESTAR CODIFICADO EN UTF-8 ****"+e
                )
                //e.printStackTrace();
            } catch (e: JsonSyntaxException) {
                Log.i(
                    "JsonError",
                    "**** EL ARCHIVO NO ES UN JSON VALIDO ****"+e
                )
                //e.printStackTrace();
            } catch (e: IOException) {
                Log.e("ENCUESTAS", "EXCEPCION NO CONTROLADA: " + e.message)
            } catch (e: NullPointerException){
                Log.d("VALOR NULO",e.toString())
            }
        }
        return jsonResponse


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

                        var file = File(pathAnswer,answerJson)


                        if(!file.exists()){
                            WriteAnser(MyRecyclerAdapter.SendSurvey(0))
                        }

                        if(voiceRecorder.state == false){
                            System.exit(0)
                        }

                    }

                }.start()

            }
        }

    }

    fun WriteAnser(value: MyRecyclerAdapter.SendSurvey){

        val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
        val representacionJSON = gson.toJson(value)
        //String FICHERO = pathAnswer + "answer.json";
        //String FICHERO = pathAnswer + "answer.json";
        val fileanswer = File(pathAnswer + answerJson)

        var fr: FileWriter? = null
        var br: BufferedWriter? = null
        try {
            fr = FileWriter(fileanswer)
            br = BufferedWriter(fr)
            br.write(representacionJSON)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Error answer", "Error - " + e.message)
        } finally {
            try {
                br!!.close()
                fr!!.close()
                if (fileanswer.exists()) {
                    Log.d("inicializacion", "${answerJson} creado")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }

    private fun stopRecorder(){
        timer.stop()
    }

    override fun OnTimerTick(duration: String) {

        tvRecord.setText("Grabando... ${duration} ")
        try {
            waveFormView.addAmplitude(voiceRecorder.mediaRecorder.maxAmplitude.toFloat())
        }catch (ex: Exception){

        }

    }


}