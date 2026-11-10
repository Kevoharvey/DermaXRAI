package com.codenytra.amme.dermaxrai.features.scan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.FlipCameraAndroid
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.codenytra.amme.dermaxrai.R
import com.codenytra.amme.dermaxrai.features.scan.components.BadOverlay
import com.codenytra.amme.dermaxrai.features.scan.components.BadSheet
import com.codenytra.amme.dermaxrai.features.scan.components.FaceQualityAnalyzer
import com.codenytra.amme.dermaxrai.features.scan.components.GoodControls
import com.codenytra.amme.dermaxrai.features.scan.components.GoodOverlay
import com.codenytra.amme.dermaxrai.ui.dermaType
import com.codenytra.amme.dermaxrai.ui.theme.DermaXRAITheme
import java.io.File
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current


            var hasPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                )
            }
            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted -> hasPermission = granted }

            LaunchedEffect(Unit) {
                hasPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            }

            LaunchedEffect(Unit) {
                if (!hasPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
            }

            val controller = remember {
                LifecycleCameraController(context).apply {
                    setEnabledUseCases(
                        CameraController.IMAGE_CAPTURE or CameraController.IMAGE_ANALYSIS
                    )
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                }
            }

            var flashOn by remember { mutableStateOf(false) }
            var quality by remember { mutableStateOf(CameraQuality.BAD) }
            var lightPercent by remember { mutableIntStateOf(0) }
            var isBackCamera by remember { mutableStateOf(false) }

            // ربط الكاميرا بالـ lifecycle
            LaunchedEffect(hasPermission) {
                if (hasPermission) controller.bindToLifecycle(lifecycleOwner)
            }

            // تحليل الإضاءة + الوجه
            DisposableEffect(hasPermission) {
                if (!hasPermission) return@DisposableEffect onDispose {}
                val executor = Executors.newSingleThreadExecutor()
                val analyzer = FaceQualityAnalyzer { q, light ->
                    quality = q
                    lightPercent = light
                }
                controller.setImageAnalysisAnalyzer(executor, analyzer)
                onDispose {
                    controller.clearImageAnalysisAnalyzer()
                    analyzer.close()
                    executor.shutdown()
                }
            }

            val galleryLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                uri?.let { openResult(context, it) }
            }

            val flipCamera = {
                isBackCamera = !isBackCamera
                controller.cameraSelector =
                    if (isBackCamera) CameraSelector.DEFAULT_BACK_CAMERA
                    else CameraSelector.DEFAULT_FRONT_CAMERA
                if (!isBackCamera) {          // الأمامية مفيهاش فلاش
                    flashOn = false
                    controller.imageCaptureFlashMode = ImageCapture.FLASH_MODE_OFF
                    controller.enableTorch(false)
                }
            }

            DermaXRAITheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Column(modifier = Modifier.padding(4.dp)) {
                                    Image(
                                        painter = painterResource(R.drawable.banner),
                                        contentDescription = "DermaXRAI",
                                        modifier = Modifier.height(40.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.camera_title),
                                        style = dermaType(12, 12, FontWeight.Medium),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    finish()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            actions = {
                                if (hasPermission) {
                                    if (isBackCamera) {
                                        IconButton(onClick = {
                                            flashOn = !flashOn
                                            controller.imageCaptureFlashMode =
                                                if (flashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
                                            controller.enableTorch(flashOn)
                                        }) {
                                            Icon(
                                                if (flashOn) Icons.Rounded.FlashOn else Icons.Rounded.FlashOff,
                                                contentDescription = "Flash"
                                            )
                                        }
                                    }
                                    IconButton(onClick = flipCamera) {
                                        Icon(
                                            Icons.Rounded.FlipCameraAndroid,
                                            contentDescription = "Flip camera"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (!hasPermission) {
                        PermissionRequired(
                            modifier = Modifier.padding(innerPadding),
                            onRequest = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                            onSettings = {
                                context.startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.packageName, null)
                                    )
                                )
                            }
                        )
                    } else {
                        CameraScreen(
                            quality = quality,
                            lightPercent = lightPercent,
                            preview = {
                                AndroidView(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = { ctx ->
                                        PreviewView(ctx).apply {
                                            this.controller = controller
                                            scaleType = PreviewView.ScaleType.FILL_CENTER
                                            implementationMode =
                                                PreviewView.ImplementationMode.COMPATIBLE
                                        }
                                    }
                                )
                            },
                            onFlipCamera = {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA)
                                        CameraSelector.DEFAULT_BACK_CAMERA
                                    else CameraSelector.DEFAULT_FRONT_CAMERA
                            },
                            onCapture = { takePhoto(context, controller) },
                            onGallery = {
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            isBackCamera = isBackCamera,
                            onFlashRetry = {
                                flashOn = true
                                controller.imageCaptureFlashMode = ImageCapture.FLASH_MODE_ON
                                controller.enableTorch(true)
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}


enum class CameraQuality { GOOD, BAD }

// الـ viewport دايماً غامق (هو الكاميرا نفسها) في الـ light والـ dark
val BottomAreaHeight = 340.dp
private val ViewportTop = Color(0xFF4C382E)
private val ViewportBottom = Color(0xFF1E0E0A)
val Peach = Color(0xFFFFB59A)
val ErrorChip = Color(0xFF622A24)

/**
 * شاشة الكاميرا بحالتيها.
 *
 * @param preview هنا تحط CameraX PreviewView (AndroidView)؛ الشاشة بترسم الـ overlay فوقه.
 * @param quality GOOD = وجه معروف وإضاءة ممتازة، BAD = إضاءة ضعيفة.
 */
@Composable
fun CameraScreen(
    quality: CameraQuality,
    modifier: Modifier = Modifier,
    lightPercent: Int = 14,
    distanceCm: Int = 35,
    preview: @Composable () -> Unit = {},
    onFlipCamera: () -> Unit = {},
    onCapture: () -> Unit = {},
    onGallery: () -> Unit = {},
    onGuide: () -> Unit = {},
    isBackCamera: Boolean = false,
    onFlashRetry: () -> Unit = {}
) {
    var tipVisible by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                when (quality) {
                    CameraQuality.BAD -> MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                    else -> MaterialTheme.colorScheme.background
                }
            )
    ) {
        Box(modifier = Modifier.weight(1f)) {
            // الكاميرا + تدرج
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(ViewportTop, ViewportBottom)))
            ) { preview() }

            when (quality) {
                CameraQuality.GOOD -> GoodOverlay(onFlipCamera)
                CameraQuality.BAD -> BadOverlay(lightPercent, distanceCm)
            }
        }
        if (tipVisible && quality == CameraQuality.GOOD) {
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE2D7))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    Icons.Rounded.LightMode,
                    null,
                    tint = Color(0xFF914A2D),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = stringResource(R.string.camera_tip_stable),
                    modifier = Modifier.weight(1f),
                    style = dermaType(13, 18),
                    color = Color(0xFF29170F)
                )
                Icon(
                    Icons.Rounded.Close, null, tint = Color(0xFF53433F),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { tipVisible = false }
                )
            }
        }

        if (quality == CameraQuality.GOOD) {
            GoodControls(onCapture = onCapture, onGallery = onGallery, onGuide = onGuide)
        } else {
            BadSheet(
                showFlash = isBackCamera,
                onFlashRetry = onFlashRetry,
                onGallery = onGallery
            )
        }
    }
}


private fun takePhoto(context: Context, controller: LifecycleCameraController) {
    val file = File(context.cacheDir, "scan_${System.currentTimeMillis()}.jpg")
    val options = ImageCapture.OutputFileOptions.Builder(file).build()
    controller.takePicture(
        options,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(result: ImageCapture.OutputFileResults) {
                openResult(context, Uri.fromFile(file))
            }

            override fun onError(e: ImageCaptureException) {
                Toast.makeText(
                    context,
                    context.getString(R.string.capture_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}

private fun openResult(context: Context, uri: Uri) {
    context.startActivity(
        Intent(context, ScanResultActivity::class.java).putExtra("image_uri", uri.toString())
    )
}


@Composable
private fun PermissionRequired(modifier: Modifier, onRequest: () -> Unit, onSettings: () -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.request_cam_per))
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRequest) { Text(stringResource(R.string.allow_cam)) }
        TextButton(onClick = onSettings) { Text(stringResource(R.string.open_settings)) }
    }
}
