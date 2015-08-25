package com.dianapislaru.cameraview;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by dianapislaru on 24/08/15.
 */
public class CameraPreview {

    private static final String TAG = "CameraPreview";

    private List<Camera.Size> mPreviewSizes;
    private List<Camera.Size> mPictureSizes;
    public float mDisplayRatio;
    public float mBestPreviewRatio = 0;
    public float mBestPictureRatio = 0;
    public int mDisplayWidth = 0;
    public int mDisplayHeight = 0;
    public int mBestPictureWidth = 0;
    public int mBestPictureHeight = 0;
    public int mBestPreviewWidth = 0;
    public int mBestPreviewHeight = 0;

    private Context mContext;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private CameraCallback mCallback;

    public CameraPreview(Context context, Camera camera, SurfaceView containerView) {
        mContext = context;
        mCamera = camera;

        mDisplayRatio = getDisplayRatio();
        mPreviewSizes = getPreviewSizes();
        mPictureSizes = getPictureSizes();
        getBestPreviewSize();
        getBestPictureSize();

        Log.i(TAG, "Global best sizes: " + mBestPreviewWidth + " " + mBestPreviewHeight);
        Log.i(TAG, "Global best sizes: " + mBestPictureWidth + " " + mBestPictureHeight);

        mHolder = containerView.getHolder();
        mCallback = new CameraCallback(mCamera, mBestPreviewWidth, mBestPreviewHeight, mBestPictureWidth, mBestPictureHeight);
        mHolder.addCallback(mCallback);
    }

    private void getBestPreviewSize() {
        int bestWidth = 0;
        int bestHeight = 0;
        float minDif = 9999999;

        for (Camera.Size s : mPreviewSizes) {
            int height = s.width;
            int width = s.height;
            float ratio = (float) height/width;
            float dif = Math.abs(mDisplayRatio - ratio);
            if ( (dif < minDif) || (areEqual(dif, minDif) && height > bestHeight) ) {
                minDif = dif;
                bestWidth = width;
                bestHeight = height;
            }
        }

        mBestPreviewWidth = bestWidth;
        mBestPreviewHeight = bestHeight;
        mBestPreviewRatio = (float) bestWidth/bestHeight;
    }

    private void getBestPictureSize() {
        int bestWidth = 0;
        int bestHeight = 0;
        float minDif = 9999999;

        for (Camera.Size s : mPictureSizes) {
            int height = s.width;
            int width = s.height;
            float ratio = (float) height/width;
            float dif = Math.abs(mDisplayRatio - ratio);
            if ( (dif < minDif) || (areEqual(dif, minDif) && height > bestHeight) ) {
                Log.i(TAG, width + " " + height + " " + ratio + " Dif: " + dif + " NEW");
                minDif = dif;
                bestWidth = width;
                bestHeight = height;
            } else {
                Log.i(TAG, width + " " + height + " " + ratio + " Dif: " + dif);
            }
        }
        Log.i(TAG, "Final best sizes: " + bestWidth + " " + bestHeight + " " + (float) bestHeight/bestWidth);

        mBestPictureWidth = bestWidth;
        mBestPictureHeight = bestHeight;
        mBestPictureRatio = (float) bestWidth/bestHeight;
    }

    private List<Camera.Size> getPreviewSizes() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getSupportedPreviewSizes();
    }

    private List<Camera.Size> getPictureSizes() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getSupportedPictureSizes();
    }

    private float getDisplayRatio() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mDisplayWidth = display.getWidth();
        mDisplayHeight = display.getHeight();

        Log.i(TAG, "Screen: " + mDisplayWidth + " " + mDisplayHeight + " " + (float) mDisplayHeight/mDisplayWidth);

        return (float) mDisplayHeight/mDisplayWidth;
    }

    private boolean areEqual(float x, float y) {
        float epsilon = 0.0000001f;
        return ( Math.abs(x - y) < epsilon );
    }
}
