package com.dianapislaru.cameraview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by dianapislaru on 25/08/15.
 */
public class CameraDialog extends AlertDialog.Builder {

    private static final String TAG = "CameraDialog";

    private Context mContext;
    private Activity mActivity;
    private AlertDialog mAlertDialog;
    private WindCamera mCamera;

    public CameraDialog(Context context, Activity activity, WindCamera camera) {
        super(context);
        mContext = context;
        mCamera = camera;
        mActivity = activity;

        setMessage("Camera is not available!");
        setCancelable(false);
        setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCamera.setUpCamera();
                dialog.cancel();
            }
        });
        setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mActivity.finish();
            }
        });

        mAlertDialog = create();
        mAlertDialog.show();
    }

}
