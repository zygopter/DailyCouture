package com.example.mywardrobe

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Canvas
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QRCodeScannerMLKit(navController: NavController, onCodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    var isScanning by remember { mutableStateOf(false) }
    var scannedCode by remember { mutableStateOf<String?>(null) }
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)


    Scaffold(
        topBar = {
            TopAppBar(elevation = 4.dp,
                title = { Text("Scan barcode")},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Filled.Close, null)
                    }
                })
        },
        bottomBar = {
            BottomNavigation {
                // Add BottomNavigationItems here
            }
        }
    ) { innerPadding ->
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = {
                BottomSheetContent(scannedCode, onDismiss = { scannedCode = null })
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView({ previewView }, modifier = Modifier.matchParentSize())

                // Animation de la ligne de scan
                val infiniteTransition = rememberInfiniteTransition()
                val scanLinePosition = infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                // Animation du cadre lors du scan
                val borderColor = if (isScanning) {
                    infiniteTransition.animateColor(
                        initialValue = Color.Yellow,
                        targetValue = Color.Red,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 500, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    ).value
                } else Color.Green

                Canvas(modifier = Modifier.matchParentSize()) {
                    // Taille et position du cadre
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val squareSide =
                        canvasWidth.coerceAtMost(canvasHeight) * 0.75f // 75% de la plus petite dimension
                    val left = (canvasWidth - squareSide) / 2
                    val top = (canvasHeight - squareSide) / 2
                    val frame = Rect(left, top, left + squareSide, top + squareSide)

                    // Dessin du cadre avec l'animation de scan
                    drawRect(
                        color = borderColor,
                        topLeft = frame.topLeft,
                        size = frame.size,
                        style = Stroke(width = 4.dp.toPx())
                    )

                    // Dessin de la ligne de scan
                    if (!isScanning) {
                        val scanLineY = top + (frame.height * scanLinePosition.value)
                        drawLine(
                            color = Color.Red,
                            start = Offset(x = left, y = scanLineY),
                            end = Offset(x = left + squareSide, y = scanLineY),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }

                LaunchedEffect(scannedCode) {
                    if (scannedCode != null) {
                        modalBottomSheetState.show()
                    } else {
                        modalBottomSheetState.hide()
                    }
                }
                // Autres éléments de l'interface utilisateur...
            }
        }
    }

    // Initialisation de l'analyseur de code-barres ML Kit
    val barcodeScanner = BarcodeScanning.getClient()
    val imageAnalysis = ImageAnalysis.Builder()
        .build()
        .also {
            it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                processImageProxy(barcodeScanner, imageProxy, { code ->
                    onCodeScanned(code)
                    scannedCode = code
                    Log.w("QRCodeScannerMLKit", "on image analysis at line 110")
                    isScanning = false // Reset après le traitement
                }, { scanState ->
                    isScanning = scanState
                })
            }
        }

    // Initialisation de CameraX
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
            Log.e("CameraX", "Failed to bind camera use cases", exc)
        }
    }

    // Nettoyage, si nécessaire
    /*Button(onClick = { navController.popBackStack() }) {
        Text("Retour")
    }*/
}

@androidx.camera.core.ExperimentalGetImage
private fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy,
    onCodeScanned: (String) -> Unit,
    setIsScanning: (Boolean) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    Log.w("QRCodeScannerMLKit", "onSuccessListener at line 150")
                    setIsScanning(true)
                    val valueType = barcode.valueType
                    // See API reference for complete list of supported types
                    when (valueType) {
                        Barcode.TYPE_WIFI -> {
                            val ssid = barcode.wifi!!.ssid
                            val password = barcode.wifi!!.password
                            val type = barcode.wifi!!.encryptionType
                        }
                        Barcode.TYPE_URL -> {
                            val title = barcode.url!!.title
                            val url = barcode.url!!.url
                        }
                        Barcode.TYPE_PRODUCT -> {
                            Log.w("QRCodeScannerMLKit","Barcode of type product")
                        }
                        Barcode.TYPE_ISBN -> {
                            Log.w("QRCodeScannerMLKit","Barcode of type isbn")
                        }
                    }
                    barcode.rawValue?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1000)
                            onCodeScanned(it)
                        }
                    }
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
                //setIsScanning(false) // Fin du scan
            }
    } else {
        Log.w("QRCodeScannerMLKit", "mediaImage is null at line 159")
        imageProxy.close()
        setIsScanning(false) // Pas d'image à traiter
    }
}

@Composable
fun BottomSheetContent(code: String?, onDismiss: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(text = "Code Scanné", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = code ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDismiss) {
            Text("Fermer")
        }
    }
}