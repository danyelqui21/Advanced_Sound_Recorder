package mx.com.bancoazteca.innovacion.encuestasatisfaccion.Controllers


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.MainActivity
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models.preguntas
import mx.com.bancoazteca.innovacion.encuestasatisfaccion.R

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

        val Question = view.findViewById(R.id.tvQuestion) as TextView
        val LinearStars = view.findViewById(R.id.LinearLayoutStars) as LinearLayout
        val LinearBool = view.findViewById<LinearLayout>(R.id.LinearLayoutBool) as LinearLayout
        val LinearHapiness = view.findViewById<LinearLayout>(R.id.LinearLayoutHapiness) as LinearLayout

        //Se setean las estrellas con findbiewbyid
        val ivStar1 = view.findViewById<ImageView>(R.id.ivStar1)
        val ivStar2 = view.findViewById<ImageView>(R.id.ivStar2)
        val ivStar3 = view.findViewById<ImageView>(R.id.ivStar3)
        val ivStar4 = view.findViewById<ImageView>(R.id.ivStar4)
        val ivStar5 = view.findViewById<ImageView>(R.id.ivStar5)


        //Se setean los radioButton
        val radioButton1 = view.findViewById<RadioButton>(R.id.rButton1)
        val radioButton2 = view.findViewById<RadioButton>(R.id.rButton2)

        //Se setean las imagenes de emojis
        val ivSad = view.findViewById(R.id.ivCaraMuyEnojado) as ImageView
        val ivNeutro = view.findViewById(R.id.ivCaraNeutro) as ImageView
        val ivGood = view.findViewById(R.id.ivCaraChido) as ImageView


        fun bind(question: preguntas, context: Context, tvEnviar: TextView){
            Question.text = question.Question

            if(question.KindQuestion.equals("Stars")){

                LinearStars.visibility = View.VISIBLE

                val layoutParams: RelativeLayout.LayoutParams =
                    LinearStars.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT
                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
                LinearStars.setLayoutParams(layoutParams)


                //Setando imagenes a los ivStar
                ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))
                ivStar1.setOnClickListener(View.OnClickListener {

                    ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                })

                ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))
                ivStar2.setOnClickListener(View.OnClickListener {

                    ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                })


                ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))
                ivStar3.setOnClickListener(View.OnClickListener {

                    ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                    ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                })

                //Se setea el valor de la estrella 4
                ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))
                ivStar4.setOnClickListener(View.OnClickListener {

                    ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))

                })

                //Se setea el valor de la estrella 5
                ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GrayStarCoconut))
                ivStar5.setOnClickListener(View.OnClickListener {

                    ivStar1.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar1.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar2.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar2.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar3.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar3.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar4.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar4.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                    ivStar5.setImageResource(androidx.appcompat.R.drawable.abc_star_black_48dp)
                    ivStar5.setColorFilter(ContextCompat.getColor(context, R.color.GoldenFreezer))

                })

            }
            else if (question.KindQuestion.equals("BoolCheckBox"))
            {

                LinearBool.visibility = View.VISIBLE

                val layoutParams: RelativeLayout.LayoutParams =
                    LinearBool.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT
                layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT
                LinearBool.setLayoutParams(layoutParams)

                radioButton1.setText(question.answers.get("0"))
                radioButton2.setText(question.answers.get("1"))



            }
            else if(question.KindQuestion.equals("HapinessIndicator")){

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
                        val touch = SendSurvey(
                            1
                        )

                        activity.WriteAnser(touch)
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

                    val touch = SendSurvey(
                        2
                    )

                    activity.WriteAnser(touch)
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

                    val touch = SendSurvey(
                        3
                    )

                    activity.WriteAnser(touch)
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

    public class SendSurvey(
        var userAnswer: Int,
    )

}