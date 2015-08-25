package com.dianapislaru.cameraview;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

/**
 * Created by dianapislaru on 24/08/15.
 */
public class WindCamera {

    private static final String TAG = "WindCamera";

    private Context mContext;
    private Activity mActivity;
    private Camera mCamera;
    private CameraPreview mPreview;
    private TakePicture mTakePicture;

    public WindCamera(Context context, RelativeLayout container, SurfaceView cameraView) {
        mContext = context;
        setUpCamera();
        mPreview = new CameraPreview(mContext, mCamera, cameraView);
        mTakePicture = new TakePicture(mContext);

        setBestLayout(container);
    }

    public void setUpCamera() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            CameraDialog dialog = new CameraDialog(mContext, mActivity, this);
            mActivity.finish();
        }
        mCamera = camera;
    }

    public void stopPreview() {
        mCamera.release();
    }

    public void takePicture() {
        mCamera.takePicture(null, null, mTakePicture);
    }

    private void setBestLayout(RelativeLayout container) {
        Log.i(TAG, "displayHeight: " + mPreview.mDisplayHeight + " " + "prev ratio: " + mPreview.mBestPreviewRatio);
        Log.i(TAG, "Layout sizes: " + (int)(mPreview.mDisplayHeight * mPreview.mBestPreviewRatio) + " " + mPreview.mDisplayHeight);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(mPreview.mDisplayHeight * mPreview.mBestPreviewRatio),mPreview.mDisplayHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        container.setLayoutParams(params);
    }

}
