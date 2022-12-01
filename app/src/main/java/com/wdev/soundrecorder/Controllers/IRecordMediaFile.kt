package com.wdev.soundrecorder.Controllers

interface IRecordMediaFile {

    fun startRecording(audioFile: String)
    fun stopRecording()
}