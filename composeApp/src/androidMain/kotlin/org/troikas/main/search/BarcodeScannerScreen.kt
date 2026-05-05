package org.troikas.main.search

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun BarcodeScannerScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember { mutableStateOf(false) }
    var scannedOnce by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (!hasCameraPermission) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Camera permission is required to scan barcodes.")
        }
        return
    }

    val previewView = remember { PreviewView(context) }

    LaunchedEffect(hasCameraPermission) {
        val cameraProvider = ProcessCameraProvider.awaitInstance(context)

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val executor = Executors.newSingleThreadExecutor()
        val barcodeScanner = BarcodeScanning.getClient()

        val imageAnalysis =
            ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

        imageAnalysis.setAnalyzer(executor) { imageProxy ->
            val mediaImage = imageProxy.image
            if (mediaImage != null && !scannedOnce) {
                val image = InputImage.fromMediaImage(
                    mediaImage, imageProxy.imageInfo.rotationDegrees
                )
                barcodeScanner.process(image).addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull {
                        it.format == Barcode.FORMAT_EAN_13 || it.format == Barcode.FORMAT_EAN_8 || it.format == Barcode.FORMAT_UPC_A
                    }?.rawValue?.let { barcode ->
                        if (!scannedOnce) {
                            scannedOnce = true
                            navController.navigate("result/$barcode") {
                                popUpTo("scanner") { inclusive = true }
                            }
                        }
                    }
                }.addOnCompleteListener { imageProxy.close() }
            } else {
                imageProxy.close()
            }
        }

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis
        )
    }

    AndroidView(
        factory = { previewView }, modifier = Modifier.fillMaxSize()
    )
}