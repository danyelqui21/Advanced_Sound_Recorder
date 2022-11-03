package mx.com.bancoazteca.innovacion.encuestasatisfaccion.Controllers


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.MainActivity
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.AnswerSurvey
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.TextFileReader
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.preguntas
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.R
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.VoiceRecorder.VoiceRecorder

class MyRecyclerAdapter () : RecyclerView.Adapter<MyRecyclerAdapter.MyItemViewHolder>() {


    lateinit var questions : MutableList<preguntas>
    lateinit var context: Context
    lateinit var tvEnviar : TextView
    lateinit var actividad: MainActivity


    fun MyRecyclerAdapter(
        questions: MutableList<preguntas>,
        context: Context,
        tvEnviar: TextView,
        activity: MainActivity
    ){

        this.questions = questions
        this.context = context
        this.tvEnviar = tvEnviar
        this.actividad = activity
    }

    class MyItemViewHolder(view: View, activity: MainActivity) : RecyclerView.ViewHolder(view){

        val activity : MainActivity = activity
        val AudioRecordingEnabled : Boolean = activity.AudioRecordingEnabled


        val Question = view.findViewById(R.id.tvQuestion) as TextView
        val LinearHapiness = view.findViewById<LinearLayout>(R.id.LinearLayoutHapiness) as LinearLayout


        //Se setean las imagenes de emojis
        val ivSad = view.findViewById(R.id.ivCaraMuyEnojado) as ImageView
        val ivNeutro = view.findViewById(R.id.ivCaraNeutro) as ImageView
        val ivGood = view.findViewById(R.id.ivCaraChido) as ImageView

        var textFileReader = TextFileReader()

        fun bind(question: preguntas, context: Context, tvEnviar: TextView){
            Question.text = question.Question

           if(question.KindQuestion.equals("HapinessIndicator")){

                LinearHapiness.visibility = View.VISIBLE
                val layoutparams : RelativeLayout.LayoutParams =
                    LinearHapiness.getLayoutParams() as RelativeLayout.LayoutParams
                layoutparams.width = RelativeLayout.LayoutParams.MATCH_PARENT
                layoutparams.height = RelativeLayout.LayoutParams.WRAP_CONTENT


                ivSad.setOnClickListener(

                    View.OnClickListener {

                        ivSad.layoutParams.height = dpToPx(100,context)
                        ivSad.layoutParams.width = dpToPx(100,context)
                        ivSad.requestLayout()
                        ivSad.setImageResource(R.drawable.sad)

                        ivNeutro.layoutParams.width = dpToPx(80,context)
                        ivNeutro.layoutParams.height = dpToPx(80,context)
                        ivNeutro.requestLayout()
                        ivNeutro.setImageResource(R.drawable.neutrogray)

                        ivGood.layoutParams.width = dpToPx(80,context)
                        ivGood.layoutParams.height = dpToPx(80,context)
                        ivGood.requestLayout()
                        ivGood.setImageResource(R.drawable.happygray)

                        tvEnviar.visibility = View.VISIBLE
                        val touch = AnswerSurvey(
                            1,
                            ""
                        )

                        if(AudioRecordingEnabled){
                            textFileReader.WriteAnswer(touch, true)
                            if(!activity.voiceRecorder.state){
                                activity.voiceRecorder.startRecording()
                                activity.timer.start()
                            }
                        }else{
                            textFileReader.WriteAnswer(touch, false)
                        }



                    }

                )

                ivNeutro.setOnClickListener(View.OnClickListener {

                    ivSad.layoutParams.width = dpToPx(80,context)
                    ivSad.layoutParams.height = dpToPx(80,context)
                    ivSad.requestLayout()
                    ivSad.setImageResource(R.drawable.sadgray)

                    ivNeutro.layoutParams.width = dpToPx(100,context)
                    ivNeutro.layoutParams.height = dpToPx(100,context)
                    ivNeutro.requestLayout()
                    ivNeutro.setImageResource(R.drawable.neutro)


                    ivGood.layoutParams.width = dpToPx(80,context)
                    ivGood.layoutParams.height = dpToPx(80,context)
                    ivGood.requestLayout()
                    ivGood.setImageResource(R.drawable.happygray)

                    tvEnviar.visibility = View.VISIBLE

                    val touch = AnswerSurvey(
                        2,
                        ""
                    )

                    if(AudioRecordingEnabled){
                        textFileReader.WriteAnswer(touch, true)
                        if(!activity.voiceRecorder.state){
                            activity.voiceRecorder.startRecording()
                            activity.timer.start()
                        }
                    }else{
                        textFileReader.WriteAnswer(touch, false)
                    }

                })

                ivGood.setOnClickListener(View.OnClickListener {

                    ivSad.layoutParams.width = dpToPx(80,context)
                    ivSad.layoutParams.height = dpToPx(80,context)
                    ivSad.requestLayout()
                    ivSad.setImageResource(R.drawable.sadgray)

                    ivNeutro.layoutParams.width = dpToPx(80,context)
                    ivNeutro.layoutParams.height = dpToPx(80,context)
                    ivNeutro.requestLayout()
                    ivNeutro.setImageResource(R.drawable.neutrogray)


                    ivGood.layoutParams.width = dpToPx(100,context)
                    ivGood.layoutParams.height = dpToPx(100,context)
                    ivGood.requestLayout()
                    ivGood.setImageResource(R.drawable.happy)

                    tvEnviar.visibility = View.VISIBLE

                    val touch = AnswerSurvey(
                        3,
                        ""
                    )

                    if(AudioRecordingEnabled){
                        textFileReader.WriteAnswer(touch, true)
                        if(!activity.voiceRecorder.state){
                            activity.voiceRecorder.startRecording()
                            activity.timer.start()
                        }
                    }else{
                        textFileReader.WriteAnswer(touch, false)
                    }

                })

            }

        }


        fun dpToPx(dp: Int, context: Context): Int {
            val density: Float =context.resources
                .getDisplayMetrics().density
            return Math.round(dp.toFloat() * density)
        }

    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val item = questions.get(position)
        holder.bind(item, context, tvEnviar)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyItemViewHolder(
            layoutInflater.inflate(
                R.layout.item_question_list,
                parent,
                false
            ),
            actividad
        )
    }

}