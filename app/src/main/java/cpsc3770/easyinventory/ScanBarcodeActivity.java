package cpsc3770.easyinventory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanBarcodeActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    private static final int MY_PERMISSION_REQUEST_CAMERA = 2569;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // Set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        // Create toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        // Start the camera preview
        cameraPreview = (SurfaceView)findViewById(R.id.camera_preview);
        createCameraSource();
    }

    private void createCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600,1024)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ActivityCompat.checkSelfPermission(ScanBarcodeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanBarcodeActivity.this, new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSION_REQUEST_CAMERA);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
                if(barcodes.size()>0) {
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0)); // Get latest barcode from the array
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                }
            }

//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> barcodes=detections.getDetectedItems();
//                if(barcodes.size()>0) {
//                    Intent intent = new Intent(getBaseContext(),ConfirmationActivity.class);
////                    Intent intent = new Intent();
//                    intent.putExtra("barcode", barcodes.valueAt(0)); // Get latest barcode from the array
////                    setResult(CommonStatusCodes.SUCCESS, intent);
//                    startActivity(intent);
//
//                    finish();
//                }
//            }
        });
    }
}
