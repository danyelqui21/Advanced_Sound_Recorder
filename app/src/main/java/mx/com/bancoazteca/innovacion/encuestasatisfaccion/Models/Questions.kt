package mx.com.bancoazteca.innovacion.encuestasatisfaccion.Models

data class Questions(

    val UrlLogo : String,
    val TipoRetiro: String,
    val Preguntas: ArrayList<preguntas>,
    val Guid: String = "",
    val GrabacionAudioActivo : Boolean,
    val TiempoParaGrabarEncuesta : Long

)

data class preguntas(
    val idQuestion : Int,
    val Question : String,
    val KindQuestion : String,
    val answers: HashMap<String, String>
)