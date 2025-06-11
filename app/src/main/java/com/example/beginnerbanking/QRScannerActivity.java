//package com.example.beginnerbanking;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.camera.view.PreviewView;
//import androidx.core.content.ContextCompat;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.mlkit.vision.barcode.BarcodeScanner;
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
//import com.google.mlkit.vision.barcode.BarcodeScanning;
//import com.google.mlkit.vision.barcode.common.Barcode;
//import com.google.mlkit.vision.common.InputImage;
//
//import java.util.concurrent.ExecutionException;
//
//public class QRScannerActivity extends AppCompatActivity {
//
//    private PreviewView previewView;
//    private ProcessCameraProvider cameraProvider;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_qrscanner);
//
////        previewView = findViewById(R.id.previewView);
//        startCamera();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        animateScanLines();
//    }
//
//    private void animateScanLines() {
//        int[] lineIds = {
//                R.id.line1, R.id.line2, R.id.line3, R.id.line4, R.id.line5
//        };
//
//        for (int i = 0; i < lineIds.length; i++) {
//            final View line = findViewById(lineIds[i]);
//
//            Animation animation = new AlphaAnimation(0f, 1f);
//            animation.setDuration(1200);
//            animation.setRepeatMode(Animation.REVERSE);
//            animation.setRepeatCount(Animation.INFINITE);
//            animation.setStartOffset(i * 150); // Staggered delay to create mirror wave
//
//            line.startAnimation(animation);
//        }
//    }
//
//
//    private void startCamera() {
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
//                ProcessCameraProvider.getInstance(this);
//
//        cameraProviderFuture.addListener(() -> {
//            try {
//                cameraProvider = cameraProviderFuture.get();
//                bindCameraUseCases();
//            } catch (ExecutionException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    private void bindCameraUseCases() {
//        Preview preview = new Preview.Builder().build();
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//        BarcodeScannerOptions options =
//                new BarcodeScannerOptions.Builder()
//                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
//                        .build();
//
//        BarcodeScanner scanner = BarcodeScanning.getClient(options);
//
//        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build();
//
//        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageProxy -> {
//            @SuppressLint("UnsafeOptInUsageError")
//            InputImage image = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());
//
//            scanner.process(image)
//                    .addOnSuccessListener(barcodes -> {
//                        for (Barcode barcode : barcodes) {
//                            String value = barcode.getRawValue();
//                            Toast.makeText(this, "Scanned: " + value, Toast.LENGTH_LONG).show();
//                            imageProxy.close();
//                            finish(); // Finish activity or return result
//                            break;
//                        }
//                    })
//                    .addOnFailureListener(Throwable::printStackTrace)
//                    .addOnCompleteListener(task -> imageProxy.close());
//        });
//
//
//        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//        cameraProvider.unbindAll();
//        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
//    }
//}
