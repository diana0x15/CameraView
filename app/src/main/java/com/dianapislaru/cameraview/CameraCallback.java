package com.dianapislaru.cameraview;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by dianapislaru on 25/08/15.
 */
public class CameraCallback implements SurfaceHolder.Callback {

    private static final String TAG = "CameraCallback";

    public int mBestPreviewWidth;
    public int mBestPreviewHeight;
    public int mBestPictureWidth;
    public int mBestPictureHeight;

    private Camera mCamera;

    public CameraCallback(Camera camera, int previewWidth, int previewHeight, int pictureWidth, int pictureHeight) {
        mCamera = camera;
        mBestPreviewWidth = previewWidth;
        mBestPreviewHeight = previewHeight;
        mBestPictureWidth = pictureWidth;
        mBestPictureHeight = pictureHeight;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters p = mCamera.getParameters();
            p.setPreviewSize(mBestPreviewHeight, mBestPreviewWidth);
            p.setPictureSize(mBestPictureHeight, mBestPictureWidth);
            mCamera.setParameters(p);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { }
}
