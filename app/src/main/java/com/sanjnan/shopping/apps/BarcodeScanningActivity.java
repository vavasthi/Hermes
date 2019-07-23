package com.sanjnan.shopping.apps;

import android.graphics.Point;
        import android.graphics.Rect;
        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.ml.vision.FirebaseVision;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
        import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
        import com.google.firebase.ml.vision.common.FirebaseVisionImage;

        import java.util.List;

public class BarcodeScanningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}