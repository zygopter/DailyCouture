package com.example.mywardrobe

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.launch

/*
@Composable
fun QRCodeScannerScreenZXing(navController: NavController, onCodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(context), BarcodeAnalyzer { result ->
                    onCodeScanned(result)
                })
            }
    }

    LaunchedEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis
            )
        } catch (exc: Exception) {
            // Gérer l'exception
        }
    }

    AndroidView({ previewView })

    Button(onClick = { navController.popBackStack() }) {
        Text("Retour")
    }
}

class BarcodeAnalyzer(private val onBarcodeDetected: (String) -> Unit) : ImageAnalysis.Analyzer {
    private val reader = MultiFormatReader()

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)
        val source = PlanarYUVLuminanceSource(
            data, image.width, image.height, 0, 0, image.width, image.height, false
        )
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            val result = reader.decodeWithState(binaryBitmap)
            onBarcodeDetected(result.text)
        } catch (e: Exception) {
            // Gérer l'exception
        } finally {
            image.close()
        }
    }
}


@Composable
fun QRCodeScannerScreen2(navController: NavController) {
    // Affichage de l'interface utilisateur pour le scan du QR code
    // ...
    val context = LocalContext.current
    val activity = context.getActivity()

    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    // Vérifier les permissions
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        val previewView = PreviewView(context)
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            preview.setSurfaceProvider(previewView.surfaceProvider)

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview)
            } catch(exc: Exception) {
                // Handle exception
            }
        }

        // Affichage de la vue de la caméra
        AndroidView(factory = { previewView })

        // Ici, vous pouvez ajouter un DecoratedBarcodeView pour gérer le scan des QR codes
        // ...
    } else {
        // Demander la permission si elle n'est pas accordée
        // ...
    }

    // Dans votre composable
    /*val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            if (intentResult.contents != null) {
                // Gérez le QR code scanné ici
            }
        }
    }

    Button(onClick = {
        val integrator = IntentIntegrator(activity).apply {
            setPrompt("Scan a QR Code")
            setOrientationLocked(false)
            captureActivity = CaptureActivity::class.java
        }
        val intent = integrator.createScanIntent()
        activityResultLauncher.launch(intent)
    }) {
        Text("Scan QR Code")
    }*/


    Button(onClick = { navController.popBackStack() }) {
        Text("Retour")
    }
}

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

*/