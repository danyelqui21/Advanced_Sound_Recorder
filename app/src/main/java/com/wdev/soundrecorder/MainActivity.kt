package com.wdev.soundrecorder

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wdev.soundrecorder.Timer.Timer
import com.wdev.soundrecorder.VoiceRecorder.VoiceRecorder
import com.wdev.soundrecorder.VoiceRecorder.WavRecorder
import com.wdev.soundrecorder.WaveFormView.WaveFormView
import com.wdev.soundrecorder.databinding.ActivityMainBinding
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), Timer.OnTimerTickListener, OnClickListener {



   // lateinit var timer : Timer
    lateinit var voiceRecorder : VoiceRecorder



    //private lateinit var buttonRecord : FloatingActionButton
    private lateinit var buttonRecord : ImageButton
    private lateinit var tvRecord: TextView
    private lateinit var waveFormView : WaveFormView
    private lateinit var ivSettings : ImageView
    private lateinit var ivListRecordins : ImageView


    private lateinit var binding: ActivityMainBinding

    lateinit var wavRecorder: WavRecorder
    lateinit var audioFormat : String
    lateinit var activity : Activity
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("recordingData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        /*editor.putString("AudioFormat","wav")
        editor.putString("AudioChannel","stereo")
        editor.commit()*/
        activity = this



        //Permissions Area
        PermissionsManager.verifyAllPermissions(this)

        //Set Views Area
        tvRecord = binding.tvTextoGrabacion
        buttonRecord = binding.buttonRecord
        ivSettings = binding.ivSettings
        ivListRecordins = binding.ivListRecordings
        //waveFormView = binding.waveFormView

        //OnClickListener Area
        binding.btnAudioRecorded.setOnClickListener(this)
        buttonRecord.setOnClickListener(this)
        ivSettings.setOnClickListener(this)
        ivListRecordins.setOnClickListener(this)


        //Instanciate classes area
        wavRecorder = WavRecorder(applicationContext, DataInfo.getRoute(applicationContext))
        voiceRecorder = VoiceRecorder(System.currentTimeMillis().toString(),applicationContext, tvRecord, buttonRecord,
            //waveFormView,
    this)

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

                        /*var file = File(externalPath,textFileReader.answerJson)

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

                         */
                        
                    }

                }.start()

            }
        }

    }



    private fun stopRecorder(){
        //timer.stop()
    }

    override fun OnTimerTick(duration: String) {

        try {
            tvRecord.text = "Grabando... ${ if(duration.replace("s","").toFloat()>0F){ duration} else {"00.000 s"}}"
        }catch (Ex: Exception){

        }

        try {
          //  waveFormView.addAmplitude(voiceRecorder.mediaRecorder.maxAmplitude.toFloat())
        }catch (ex: Exception){

        }

    }




    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(p0: View?) {

        audioFormat = sharedPreferences.getString("AudioFormat","wav").toString()

        when(p0?.id){

            R.id.btnAudioRecorded -> {
                intent = Intent(applicationContext, AudioList::class.java)
                startActivity(intent)

            }
            R.id.button_record ->{


                if (VoiceRecorder.state == false)voiceRecorder.setAudioFormat(audioFormat)

                if(audioFormat.equals("wav")){

                    if(!wavRecorder.isRecording){
                        wavRecorder.setModeRecord()
                        wavRecorder.startRecording()
                        //binding.audiosSpinner.isEnabled = false
                        binding.tvTextoGrabacion.text = activity.getString(R.string.grabando)
                        binding.buttonRecord.setImageDrawable(activity.getDrawable(R.drawable.stop_24dp))
                        //binding.buttonRecord.text = activity.getString(R.string.textDetenerGrabacion)

                    }else{
                        wavRecorder.stopRecording()
                        //binding.audiosSpinner.isEnabled = true
                        binding.tvTextoGrabacion.text = ""
                        binding.buttonRecord.setImageDrawable(activity.getDrawable(R.drawable.fiber_manual_record_24dp))
                    }

                }else if (audioFormat.equals("mp4")){

                    if (VoiceRecorder.state == false){
                        //voiceRecorder.stopRecording()
                        voiceRecorder.startRecording()
                        //binding.audiosSpinner.isEnabled = false
                        binding.tvTextoGrabacion.text = activity.getString(R.string.grabando)
                        binding.buttonRecord.setImageDrawable(activity.getDrawable(R.drawable.stop_24dp))
                        //binding.buttonRecord.text = activity.getString(R.string.textDetenerGrabacion)
                        // timer.stop()
                        //waveFormView.clear()
                    }else{
                        //voiceRecorder.startRecording()
                        voiceRecorder.stopRecording()
                        //binding.audiosSpinner.isEnabled = true
                        binding.tvTextoGrabacion.text = ""
                        binding.buttonRecord.setImageDrawable(activity.getDrawable(R.drawable.fiber_manual_record_24dp))
                        //binding.buttonRecord.text = activity.getString(R.string.textGrabar)
                        //  timer.start()
                    }
                }else if(audioFormat.equals("3gp")){
                    Toast.makeText(applicationContext, "Not yet implemeted", Toast.LENGTH_LONG).show()
                }


            }
            R.id.ivSettings->{

                val settinggsIntent  = Intent(applicationContext, SettingsActivity::class.java)
                startActivity(settinggsIntent)
            }
            R.id.ivListRecordings->{
                val listOfRecordings = Intent(applicationContext, AudioList::class.java)
                startActivity(listOfRecordings)
            }
            else -> {
                Toast.makeText(applicationContext, "Unknow action (???)", Toast.LENGTH_LONG).show()
            }

        }
    }






}