package com.nipunapps.tmdb.moviedetailpage.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipunapps.tmdb.core.Constants
import com.nipunapps.tmdb.core.Constants.ROOT_PATH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject

const val StatusDelay = 3500L

@HiltViewModel
class ImageFullViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _image = mutableStateOf("")
    val image: State<String> = _image

    private val _lock = mutableStateOf(false)
    val lock: State<Boolean> = _lock

    private val _lockVisibility = mutableStateOf(false)
    val lockVisibility: State<Boolean> = _lockVisibility

    private val _startDownload = mutableStateOf(false)
    val startDownload: State<Boolean> = _startDownload

    private val _downloadProgress = mutableStateOf(0f)
    val downloadProgress: State<Float> = _downloadProgress

    private val _downloadStatus = mutableStateOf(DownloadStatus.NOTHING)
    val downloadStatus: State<DownloadStatus> = _downloadStatus

    private var job: Job? = null
    private var downloadJob: Job? = null

    init {
        savedStateHandle.get<String>("image")?.let { img ->
            _image.value = "${Constants.POSTER_PATH}$img"
        }
        showLock()
    }

    fun toggleLock() {
        _lock.value = !lock.value
    }

    fun showLock() {
        if(lockVisibility.value){
            _lockVisibility.value = false
            return
        }
        _lockVisibility.value = true
        job?.cancel()
        job = viewModelScope.launch {
            delay(3000L)
            _lockVisibility.value = false
        }
    }

    fun startDownload() {
        _startDownload.value = true
        downloadImage()
    }

    fun downloadImage() {
        downloadJob?.cancel()
        if (image.value != "") {
            downloadJob = CoroutineScope(Dispatchers.IO).launch {
                if (!ROOT_PATH.exists()) ROOT_PATH.mkdir()
                val fileName = image.value.substring(image.value.lastIndexOf("/") + 1)
                val file = File(ROOT_PATH, fileName)
                if(file.exists()){
                    _downloadStatus.value = DownloadStatus.EXISTS
                    delay(StatusDelay)
                    _downloadStatus.value = DownloadStatus.NOTHING
                    return@launch
                }
                if (file.createNewFile()) file.createNewFile()
                try {
                    val url = URL(image.value)
                    val urlConnection = url.openConnection()
                    urlConnection.connect()
                    val fos = FileOutputStream(file)
                    val fis = BufferedInputStream(url.openStream(), 8192)
                    val totalSize = urlConnection.contentLength
                    var downloadSize = 0
                    val buffer = ByteArray(1024)
                    var bufferLength = fis.read(buffer)
                    var count = true
                    while (bufferLength != -1) {
                        fos.write(buffer, 0, bufferLength)
                        downloadSize += bufferLength
                        if(count){
                            _downloadStatus.value = DownloadStatus.STARTED
                            count = false
                        }
                        _downloadProgress.value = (downloadSize) / totalSize.toFloat()
                        bufferLength = fis.read(buffer)
                    }
                    fos.flush()
                    fos.close()
                    fis.close()
                    if (downloadSize >= totalSize){
                        _downloadProgress.value = 0f
                        _downloadStatus.value = DownloadStatus.FINISHED
                    }
                    _startDownload.value = false
                } catch (e: Exception) {
                    if(file.exists()) file.delete()
                    _downloadStatus.value = DownloadStatus.ERROR
                    delay(StatusDelay)
                    _downloadStatus.value = DownloadStatus.NOTHING
                    _startDownload.value = false
                }
            }
        } else {
            _downloadStatus.value = DownloadStatus.NOTHING
            _startDownload.value = false
        }
    }
}

enum class DownloadStatus {
    STARTED,
    FINISHED,
    EXISTS,
    ERROR,
    NOTHING
}