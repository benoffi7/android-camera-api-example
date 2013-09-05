package com.coffeeandcookies.cameraexample;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	Camera camera;

	CameraSurfaceView(Context context) {
		super(context);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		SurfaceHolder holder = this.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		// The default orientation is landscape, so for a portrait app like this
		// one we need to rotate the view 90 degrees.
		camera.setDisplayOrientation(90);

		// IMPORTANT: We must call startPreview() on the camera before we take
		// any pictures
		camera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = openFrontFacingCameraGingerbread();
			this.camera.setPreviewDisplay(holder);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}

	}

	@SuppressLint("NewApi")
	private Camera openFrontFacingCameraGingerbread() {
		Camera camera;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			int numCameras = Camera.getNumberOfCameras();
			if (numCameras == 0) {
				Log.w(Configuracion.TAG, "No cameras!");
				return null;
			}

			int index = 0;
			while (index < numCameras) {
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				Camera.getCameraInfo(index, cameraInfo);
				if (cameraInfo.facing == Configuracion.TIPO_CAMERA) {
					break;
				}
				index++;
			}

			if (index < numCameras) {
				Log.i(Configuracion.TAG, "Opening camera #" + index);
				camera = Camera.open(index);
			} else {
				Log.i(Configuracion.TAG,
						"No camera facing back; returning camera #0");
				camera = Camera.open(0);
			}

		} else {
			camera = Camera.open();
		}

		return camera;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
	}

	public void takePicture(PictureCallback imageCallback) {
		camera.takePicture(null, null, imageCallback);
	}

}