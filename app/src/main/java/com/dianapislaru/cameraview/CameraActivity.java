package com.dianapislaru.cameraview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = "CameraActivity";

    private WindCamera mWindCamera;
    private RelativeLayout mCameraContainer;
    private SurfaceView mCameraView;
    private ImageButton mCaptureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        mCameraContainer = (RelativeLayout) findViewById(R.id.camera_preview_container);
        mCameraView = (SurfaceView) findViewById(R.id.camera_preview_view);
        mCaptureButton = (ImageButton) findViewById(R.id.capture_button);
        mWindCamera = new WindCamera(this, mCameraContainer, mCameraView);

        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindCamera.takePicture();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWindCamera.stopPreview();
    }
}
