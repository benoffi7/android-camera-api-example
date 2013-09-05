package com.coffeeandcookies.cameraexample;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.os.Environment;

@SuppressLint("InlinedApi")
public class Configuracion
{
	public static int TIPO_CAMERA = Camera.CameraInfo.CAMERA_FACING_FRONT;
//	public static int TIPO_CAMERA = Camera.CameraInfo.CAMERA_FACING_BACK;
	public static String DIRECTORIO = "MiCamaraApp";
	public static String DIRECTORIO_RAIZ = Environment.DIRECTORY_PICTURES;
//	public static String DIRECTORIO_RAIZ = Environment.DIRECTORY_DCIM;
//	public static String DIRECTORIO_RAIZ = Environment.DIRECTORY_DOWNLOADS;
	public static String TAG ="MiCamaraApp";

}
