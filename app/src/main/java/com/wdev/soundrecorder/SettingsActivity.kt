package com.wdev.soundrecorder

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.wdev.soundrecorder.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity(), OnClickListener, OnItemSelectedListener  {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var toolbar: Toolbar
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("recordingData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        //editor.putString("AudioFormat","wav")
        //editor.putString("AudioChannel","stereo")
        //editor.commit()


        //Set views
        toolbar = binding.ToolbarS
        val spinnerFormat = binding.audiosSpinner
        val spinnerMode = binding.modeSpinner


        //Spinner area

        val valores = arrayOf("wav", "mp4", "3gp")
        val modeValue = arrayOf("Mono", "Stereo")
        var indexFormat : Int = -2
        var indexChannel : Int = 2


        //Obtener formato de grabación desde SharedPreferences
        var audioFormat : String = sharedPreferences.getString("AudioFormat","wav").toString()
        indexFormat = if( valores.indexOf(audioFormat) > -1)  valores.indexOf(audioFormat) else -2

        //Obtener canal de grabación dessde SharedPreferences
        var channelConfig : String = sharedPreferences.getString("AudioChannel","Stereo").toString()
        indexChannel = if( modeValue.indexOf(channelConfig) > -1)  modeValue.indexOf(channelConfig) else -2

        //Se seteaan valoreas a spinner de formato de grabación
        spinnerFormat.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, valores)
        if(indexFormat>=0)spinnerFormat.setSelection(indexFormat)

        //Se setean valores a spinner de canal de grabación
        spinnerMode.adapter = ArrayAdapter(this, R.layout.simple_spinner_item, modeValue)
        if(indexChannel>=0)spinnerMode.setSelection(indexChannel)


        spinnerFormat.onItemSelectedListener = this
        spinnerMode.onItemSelectedListener = this


        toolbar.setNavigationOnClickListener {
            finish()
        }
        //setContentView(R.layout.activity_settings)
    }

    override fun onClick(p0: View?) {
        /*when(p0?.id){

            R.id.ivBack->{
                println("Settings touched")
                 finish()
            }
            else ->{
                println("Uknown component")
            }
        }*/


    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id){
            com.wdev.soundrecorder.R.id.audios_spinner->{

                //editor = p
                editor.putString("AudioFormat",p0.getItemAtPosition(p2).toString())
               // Toast.makeText(applicationContext, p0.getItemAtPosition(p2).toString(), Toast.LENGTH_SHORT).show()
            }
            com.wdev.soundrecorder.R.id.mode_spinner->{
            //var    modeRecoding = p0.selectedItemPosition+1 //
                editor.putString("AudioChannel",p0.getItemAtPosition(p2).toString())
                //Toast.makeText(applicationContext, "The mode is  $modeRecoding", Toast.LENGTH_SHORT).show()
            }
            else->{
                Log.d("TAG","Uknown spìnner!!!")
            }
        }

        editor.commit()

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}