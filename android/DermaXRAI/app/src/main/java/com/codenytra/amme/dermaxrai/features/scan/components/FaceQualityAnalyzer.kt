package com.codenytra.amme.dermaxrai.features.scan.components

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.codenytra.amme.dermaxrai.features.scan.CameraQuality
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceQualityAnalyzer(
    private val onResult: (CameraQuality, Int) -> Unit
) : ImageAnalysis.Analyzer {

    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.4f)
            .build()
    )
    private val mainHandler = Handler(Looper.getMainLooper())
    private var lastRun = 0L

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val now = SystemClock.elapsedRealtime()
        val media = imageProxy.image
        // تحليل كل 300ms بس عشان البطارية
        if (media == null || now - lastRun < 300) {
            imageProxy.close(); return
        }
        lastRun = now

        // متوسط سطوع من قناة Y (luma)
        val luma = averageLuma(imageProxy)
        val lightPercent = (luma / 255f * 100).toInt()

        val input = InputImage.fromMediaImage(media, imageProxy.imageInfo.rotationDegrees)
        detector.process(input)
            .addOnSuccessListener { faces ->
                val good = faces.size == 1 && lightPercent >= 35
                mainHandler.post {
                    onResult(if (good) CameraQuality.GOOD else CameraQuality.BAD, lightPercent)
                }
            }
            .addOnCompleteListener { imageProxy.close() }
    }

    private fun averageLuma(image: ImageProxy): Float {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining()).also { buffer.get(it) }
        var sum = 0L
        var count = 0
        var i = 0
        while (i < data.size) {          // نعمل sampling كل 10 بكسل
            sum += data[i].toInt() and 0xFF
            count++; i += 10
        }
        buffer.rewind()
        return if (count == 0) 0f else sum.toFloat() / count
    }

    fun close() = detector.close()
}