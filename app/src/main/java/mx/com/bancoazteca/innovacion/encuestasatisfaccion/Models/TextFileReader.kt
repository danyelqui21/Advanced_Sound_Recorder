package mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models

import android.os.Environment
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.io.*
import java.util.*

class TextFileReader {

    val sdCardDir = Environment.getExternalStorageDirectory()
    val answerJson =  "${sdCardDir.toString()+File.separator}answerSurvey.json"
    val answerTemporalJson =  "${sdCardDir.toString()+File.separator}answerSurveyTemporal.json"
    lateinit var fileanswer : File

    fun getJsonFileData(fileName: String): String? {

        var jsonResponse : String? = ""
        // Get the dir of SD Card

        // Get The Text file
        val txtFile = File(fileName)

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

    fun WriteAnswer(value: AnswerSurvey, isTemporal: Boolean){

        val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()
        val representacionJSON = gson.toJson(value)
        //String FICHERO = pathAnswer + "answer.json";
        //String FICHERO = pathAnswer + "answer.json";

        if(isTemporal){
            fileanswer = File(answerTemporalJson)
        }else{
            fileanswer = File(answerJson)
        }

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
}