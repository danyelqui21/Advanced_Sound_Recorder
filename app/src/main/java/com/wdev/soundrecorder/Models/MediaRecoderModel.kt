package com.wdev.soundrecorder.Models

import android.hardware.camera2.params.OisSample
import android.media.AudioFormat
import android.media.MediaRecorder
import com.wdev.soundrecorder.PlayAudio

data class MediaRecoderModel(

    val audioSouce : MediaRecorder.AudioSource,
    val audioOutput: MediaRecorder.OutputFormat,
    val audioEncoder: MediaRecorder.AudioEncoder,
    val encodingSample: Int,
    val audioSamping: Int,
    val outputFile : String

)
