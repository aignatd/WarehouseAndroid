package com.artolanggeng.purnamakertasindo.utils;

//Author by Ignat.

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.EditText;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.google.gson.Gson;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class Fungsi extends AppCompatActivity
{
	//-------------------------------------------------------------------------------------------->>> Fungsi enkripsi
	public static String ProsesEnkrip(String RawData)
	{
		return wrap(RawData, keygen());
	}
	//--------------------------------------------------------------------------------------------

	//-------------------------------------------------------------------------------------------->>> Keygen
	public static String DataAcak()
	{
		String gkey="", lCode, rHead = "", charArray;
		int i=0, length;

		charArray = "zyxwvutsrqponmlkjihgfedcba0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		length = random_int(10, 15);
		lCode = Integer.toHexString(length);

		for(i=0; i<=4; i++)
			rHead = rHead + charArray.charAt(random_int(0, 61));

		for(i=0; i<=length-1; i++)
			gkey = gkey + charArray.charAt(random_int(0, 61));

		gkey = lCode+rHead+gkey;
		return gkey;
	}

  public static String keygen()
  {
    String gkey="", lCode, rHead = "", charArray;
    int i=0, length;

    charArray = "zyxwvutsrqponmlkjihgfedcba0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";

    length = random_int(10, 15);
    lCode = Integer.toHexString(length);

    for(i=0; i<=4; i++)
      rHead = rHead + charArray.charAt(random_int(0, 71));

    for(i=0; i<=length-1; i++)
      gkey = gkey + charArray.charAt(random_int(0, 71));

    gkey = lCode+rHead+gkey;
    return gkey;
  }

	public static int random_int(int Min, int Max)
	{
		return (int) (Math.random()*(Max-Min))+Min;
	}
	//------------------------------------------------------------------------------------------------------

	//----------------------------------------------------------------------------->>>>	AES, EncryptByteArray
	public static String EncryptByteArray(byte[] array, byte[] key) throws Exception
	{
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    String encryptedString = new String(Base64.encode(cipher.doFinal(array), Base64.DEFAULT));
	    return encryptedString;
	}
	//--------------------------------------------------------------------------------------------------------

	//---------------------------------------------------------------------------->>>>>> AES, decryptByteArray
	public static byte[] decryptByteArray(String strToDecrypt, byte[] key) throws Exception
	{
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return cipher.doFinal(Base64.decode(strToDecrypt.getBytes(), Base64.DEFAULT));
	}
	//---------------------------------------------------------------------------------------------------------

	//---------------------------------------------------------------------------->>>>
	public static String wrap(String data, String vKey)
	{
		//---------------------Wrap Declare-------------------------
		int intLen;
		String strTrueKey, strEncrypted, strWrapped = "", strvKey2, s;
		String strWrapped64 = "";
		byte[] bytesOfMessage, thedigest;
		MessageDigest mdMD5;
		char c;
		//----------------------------------------------------------

		strvKey2 = vKey;
		c = vKey.charAt(0);
		s = Character.toString(c);
		vKey = s;

		try
		{
			intLen = Integer.parseInt(vKey, 16);
			strTrueKey = strvKey2.substring(6, 6 + intLen);
			mdMD5 = MessageDigest.getInstance("MD5");
			bytesOfMessage = strTrueKey.getBytes("UTF-8");
			thedigest = mdMD5.digest(bytesOfMessage);

			strEncrypted = EncryptByteArray(data.getBytes("UTF-8"), thedigest);
    	strWrapped = strvKey2 + strEncrypted;
    	strWrapped64 = new String(Base64.encode(strWrapped.getBytes(), Base64.DEFAULT));
		}
		catch(Exception e)
		{
		}

		return strWrapped64;
	}

	//---------------------------------------------------------------------------------------------------------
	public static String unwrap(String data)
	{
		//---------------------Wrap Declare-------------------------
		int intLen;
		String strTrueKey, s, data2;
		String strUnWrapped64="";
		byte[] bytesOfMessage, thedigest;
		MessageDigest mdMD5;
		char c;

		String strBase64 = new String(Base64.decode(data.getBytes(), Base64.DEFAULT));

		data2 = strBase64;
		byte[] bytesOfDecrypted;
		c = strBase64.charAt(0);
		s = Character.toString(c);

		try
		{
    	intLen = Integer.parseInt(s, 16);
    	strTrueKey = strBase64.substring(6, 6 + intLen);
    	mdMD5 = MessageDigest.getInstance("MD5");
    	bytesOfMessage = strTrueKey.getBytes("UTF-8");
    	thedigest = mdMD5.digest(bytesOfMessage);

    	String datanya = data2.substring(6+intLen);

    	bytesOfDecrypted = decryptByteArray(datanya, thedigest);
			strUnWrapped64 = new String(bytesOfDecrypted, "UTF-8");
		}
		catch(Exception e)
		{
		}

		return strUnWrapped64;
	}
/*
	//************************** Bitmap to ByteArray **************************
	public static byte[] bitmapToByteArr(Bitmap bitmap)
	{
		int bytes = bitmap.getByteCount();
		ByteBuffer buffer = ByteBuffer.allocate(bytes);
		bitmap.copyPixelsToBuffer(buffer);
		byte[] array = buffer.array();
		return array;
	}

	//************************** ByteArray to Bitmap **************************
	public static Bitmap byteArrToBitmap(byte[] bytes)
	{
		InputStream inputStream = new ByteArrayInputStream(bytes);
		BitmapFactory.Options o = new BitmapFactory.Options();
		return BitmapFactory.decodeStream(inputStream, null, o);
	}
*/
  public static String DeviceTipe(Context context)
  {
    String strJenisDevice;

    if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE)
      strJenisDevice = "Android Tablet";
    else
      strJenisDevice = "Android Smartphone";

    return strJenisDevice;
  }

	public static String DeviceInfo(Context context, int iKeperluan)
	{
		String tmDevice, androidId, tmSerial;

		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    try
    {
      tmDevice = "" + telephonyManager.getDeviceId();
    }
    catch (Exception e)
    {
//      tmDevice = DataAcak();
      tmDevice = "dwmG9k743Mlm23tpnj7";
    }

		try
		{
			androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		}
		catch (Exception e)
		{
//      androidId = DataAcak();
      androidId = "bbe0-8fc5-5555-ww1234zyzyzy";
		}

		try
		{
			UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmDevice.hashCode());
			tmSerial = deviceUuid.toString();
		}
		catch (Exception e)
		{
//      tmSerial = DataAcak();
      tmSerial = "ffffffff-aae0-8fc5-73eb-cc8773ebcc87";
		}

    if(iKeperluan == 0)
		  return tmDevice + "#" + androidId + "#" + tmSerial;
    else
    if(iKeperluan == 1)
      return tmDevice;
    else
    if(iKeperluan == 2)
      return androidId;
    else
      return tmSerial;
	}

  public static String AndroidVersion()
  {
    String release = Build.VERSION.RELEASE;
    int sdkVersion = Build.VERSION.SDK_INT;
    return "Android SDK: " + sdkVersion + " (" + release +")";
  }

	public static String DeviceName()
	{
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;

		if (model.startsWith(manufacturer))
			return capitalize(model);
		else
			return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String s)
	{
		if (s == null || s.length() == 0)
			return "";

		char first = s.charAt(0);

		if (Character.isUpperCase(first))
			return s;
		else
			return Character.toUpperCase(first) + s.substring(1);
	}

	public static void storeToSharedPref(final Context context, String value, String key)
	{
		SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamaPref, Context.MODE_PRIVATE).edit();
    editor.putString(key, value).commit();
  }

  public static void storeToSharedPref(final Context context, long value, String key)
  {
    SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamaPref, Context.MODE_PRIVATE).edit();
    editor.putLong(key, value).commit();
  }

	public static void storeToSharedPref(final Context context, boolean value, String key)
	{
    SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamaPref, Context.MODE_PRIVATE).edit();
    editor.putBoolean(key, value).commit();
	}

	public static void storeToSharedPref(final Context context, int value, String key)
	{
    SharedPreferences.Editor editor = context.getSharedPreferences(FixValue.strNamaPref, Context.MODE_PRIVATE).edit();
    editor.putInt(key, value).commit();
	}

	public static void storeObjectToSharedPref(final Context context, Object object, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(FixValue.strNamaPref, 0);
		SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		final Gson gson = new Gson();
		String serializedObject = gson.toJson(object);
		sharedPreferencesEditor.putString(key, serializedObject);
		sharedPreferencesEditor.apply();
	}

	public static <GenericClass> GenericClass getObjectFromSharedPref(Context context, Class<GenericClass> classType, String key)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(FixValue.strNamaPref, 0);
		if (sharedPreferences.contains(key))
		{
			final Gson gson = new Gson();
			return gson.fromJson(sharedPreferences.getString(key, ""), classType);
		}

		return null;
	}

	public static String getStringFromSharedPref(final Context context, String key)
	{
    SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamaPref,  Context.MODE_PRIVATE);
    return prefs.getString(key, "");
  }

  public static long getLongFromSharedPref(final Context context, String key)
  {
    SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamaPref,  Context.MODE_PRIVATE);
    return prefs.getLong(key, 99);
  }

	public static boolean getBooleanFromSharedPref(final Context context, String key)
	{
    SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamaPref,  Context.MODE_PRIVATE);
    return prefs.getBoolean(key, true);
	}

	public static int getIntFromSharedPref(final Context context, String key)
	{
    SharedPreferences prefs = context.getSharedPreferences(FixValue.strNamaPref,  Context.MODE_PRIVATE);
    return prefs.getInt(key, 0);
	}

	public static void ClearIsiPref(final Context context)
	{
		context.getSharedPreferences(FixValue.strNamaPref, 0).edit().clear().commit();
	}

	public static Bitmap ReducedImageSize(String strNamafile)
	{
		int intTargetWidth = 441;
		int intTargetHeight = 557;

		BitmapFactory.Options bfResult = new BitmapFactory.Options();
		bfResult.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(strNamafile, bfResult);

		int intCameraWidth = bfResult.outWidth;
		int intCameraHeight = bfResult.outHeight;

		int scaleFactor = Math.min(intCameraWidth/intTargetWidth, intCameraHeight/intTargetHeight);
		bfResult.inSampleSize = scaleFactor;
		bfResult.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(strNamafile, bfResult);
	}

	public static Bitmap SetRotateImage(Bitmap bpData, String strNamafile)
	{
		Bitmap bpHasil;
		ExifInterface eiData = null;

		try
		{
			eiData = new ExifInterface(strNamafile);
		}
		catch(IOException e)
		{
			return null;
		}

		Matrix matrix = new Matrix();
		int orient = eiData.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

		switch(orient)
		{
			case ExifInterface.ORIENTATION_NORMAL :
				matrix.setRotate(0);
			break;
			case ExifInterface.ORIENTATION_ROTATE_90 :
				matrix.setRotate(90);
			break;
			case ExifInterface.ORIENTATION_ROTATE_180 :
				matrix.setRotate(180);
			break;
			case ExifInterface.ORIENTATION_ROTATE_270 :
				matrix.setRotate(270);
			break;
			case ExifInterface.ORIENTATION_UNDEFINED :
				matrix.setRotate(-90);
				break;
			default :
		}

		bpHasil = Bitmap.createBitmap(bpData, 0, 0, bpData.getWidth(), bpData.getHeight(), matrix, true);
		File file = new File(strNamafile);

		try
		{
			file.createNewFile();

			FileOutputStream out = new FileOutputStream(file);
			bpHasil.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			return null;
		}

		return BukaGambar(file);
	}

	public static Bitmap BukaGambar(File fopen)
	{
		return BitmapFactory.decodeFile(fopen.getAbsolutePath());
	}

	public static Bitmap SimpanGambar(Bitmap bmpGambar, String strNamafile)
	{
    File file = new File(strNamafile);

		try
		{
      Log.d("", "SimpanGambar: ");

      file.createNewFile();

			FileOutputStream out = new FileOutputStream(file);
			bmpGambar.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
      return null;
		}

    return BukaGambar(file);
	}

	public static boolean CekInput(List<EditText> lstEditText, List<String> lstMsg, Context context)
	{
		PesanPopup pesan = new PesanPopup();

		for(int a=0; a<lstEditText.size(); a++)
		{
			EditText etTemp = lstEditText.get(a);

			if(etTemp.getText().toString().matches(""))
			{
				pesan.TampilPesan1(context, lstMsg.get(a));
				etTemp.requestFocus();
				return false;
			}
		}

		return true;
	}

	//public static void setCameraParameter(Camera camera, int prevwidth, int prevheight, int picwidth, int picheight, String strOrie)
	public static void setCameraParameter(Camera camera, int picwidth, int picheight, String strOrie)
	{
		Camera.Parameters parameters = camera.getParameters();
		parameters.set("orientation", strOrie);
		parameters.set("jpeg-quality", 100);
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setPictureSize(picwidth, picheight);
		camera.setParameters(parameters);
	}

	public static void setCameraDisplayOrientation(Camera camera, Context context, int FacingCamera)
	{
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int rotation = display.getRotation();
		int degrees = 0;

		switch (rotation)
		{
			case Surface.ROTATION_0:
				degrees = 0;
			break;
			case Surface.ROTATION_90:
				degrees = 90;
			break;
			case Surface.ROTATION_180:
				degrees = 180;
			break;
			case Surface.ROTATION_270:
				degrees = 270;
			break;
		}

		int result;
		Camera.CameraInfo camInfo = new Camera.CameraInfo();
		Camera.getCameraInfo(getFacingCameraId(FacingCamera), camInfo);

		if (camInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
		{
			result = (camInfo.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		}
		else
			result = (camInfo.orientation - degrees + 360) % 360;

		camera.setDisplayOrientation(result);
	}

	public static int getFacingCameraId(int FacingCamera)
	{
		int cameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();

		for (int i =0; i<numberOfCameras; i++)
		{
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(i, info);

			if (info.facing == FacingCamera)
			{
				cameraId = i;
				break;
			}
		}

		return cameraId;
	}

	public static Point getBestPreviewSize(Camera camera)
	{
		int MIN_PREV_PIXELS = 480 * 320; // normal screen
		int MAX_PREV_PIXELS = 1920 * 1080; // more than large/HD screen

		Camera.Parameters p = camera.getParameters();
		List<Camera.Size> lstSize = new ArrayList<Camera.Size>(p.getSupportedPreviewSizes());
		Collections.sort(lstSize, new Comparator<Camera.Size>()
		{
			@Override
			public int compare(Camera.Size a, Camera.Size b) {
				int aPixels = a.height * a.width;
				int bPixels = b.height * b.width;

				if (bPixels < aPixels)
					return -1;

				if (bPixels > aPixels)
					return 1;

				return 0;
			}
		});

		List<Point> lstHasil = new ArrayList<Point>();
		lstHasil.clear();

		for (Camera.Size size : lstSize)
		{
			int realWidth = size.width;
			int realHeight = size.height;
			int pixels = realWidth * realHeight;

			if(pixels < MIN_PREV_PIXELS || pixels > MAX_PREV_PIXELS)
				continue;

			Point pTemp = new Point(realWidth, realHeight);
			lstHasil.add(pTemp);
			Log.d("[fungsi]", "Preview Screen -> " + pTemp);
		}

		return lstHasil.get(0);
	}

	public static boolean ValidEmail(CharSequence target)
	{
		return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	public static int isNetworkAvailable(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork)
		{
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return FixValue.TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return FixValue.TYPE_MOBILE;
		}

		return FixValue.TYPE_NONE;
	}

	public static DataLink BindingData()
	{
		OkHttpClient okClient = new OkHttpClient();

		okClient.newBuilder().connectTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
			readTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).
			writeTimeout(FixValue.TimeoutConnection, TimeUnit.SECONDS).build();

		Retrofit retBindingData = new Retrofit.Builder().baseUrl(FixValue.Hostname).
			addConverterFactory(GsonConverterFactory.create()).
			client(okClient).build();

		return retBindingData.create(DataLink.class);
	}

	public static boolean CheckPermission(Activity activity, Context context)
	{
		List<String> listPermissionsNeeded = new ArrayList<>();

		int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.INTERNET);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.LOCATION_HARDWARE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.LOCATION_HARDWARE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.VIBRATE);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.CAMERA);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.BLUETOOTH);

		result = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN);
		if (result != PackageManager.PERMISSION_GRANTED)
			listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);

		if (!listPermissionsNeeded.isEmpty())
		{
			ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
			return true;
		}

		return true;
	}

	public static String FormatDesimal(Integer intSource)
	{
		DecimalFormat fmt = new DecimalFormat();
		DecimalFormatSymbols fmts = new DecimalFormatSymbols();

		fmts.setGroupingSeparator('.');
		fmt.setGroupingSize(3);
		fmt.setGroupingUsed(true);
		fmt.setDecimalFormatSymbols(fmts);
		return fmt.format(intSource);
	}

	public static Integer sendCommandStar(Context context, String portName, String portSettings, ArrayList<byte[]> byteList)
	{
		StarIOPort port = null;

		try
		{
			/*
			 * using StarIOPort3.1.jar (support USB Port) Android OS Version: upper 2.2
			 */
			port = StarIOPort.getPort(portName, portSettings, 10000, context);
			/*
			 * using StarIOPort.jar Android OS Version: under 2.1 port = StarIOPort.getPort(portName, portSettings, 10000);
			 */
			try { Thread.sleep(500); }
			catch (InterruptedException e) {return -1;}

			/*
			 * Using Begin / End Checked Block method When sending large amounts of raster data,
			 * adjust the value in the timeout in the "StarIOPort.getPort" in order to prevent
			 * "timeout" of the "endCheckedBlock method" while a printing.
			 *
			 * If receipt print is success but timeout error occurs(Show message which is "There
			 * was no response of the printer within the timeout period." ), need to change value
			 * of timeout more longer in "StarIOPort.getPort" method.
			 * (e.g.) 10000 -> 30000
			 */
			StarPrinterStatus status = port.beginCheckedBlock();

			if (status.offline) return -2;

			byte[] commandToSendToPrinter = convertFromListByteArrayTobyteArray(byteList);
			port.writePort(commandToSendToPrinter, 0, commandToSendToPrinter.length);

			port.setEndCheckedBlockTimeoutMillis(10000);// Change the timeout time of endCheckedBlock method.

			status = port.endCheckedBlock();

			if (status.coverOpen == true)
			{
				return -3;
//				throw new StarIOPortException("Printer cover is open");
			}
			else if (status.receiptPaperEmpty == true)
			{
				return -4;
//				throw new StarIOPortException("Receipt paper is empty");
			}
			else if (status.offline == true)
			{
				return -2;
//				throw new StarIOPortException("Printer is offline");
			}
		}
		catch (StarIOPortException e)
		{
			return -5;
		}
		finally
		{
			if (port != null)
			{
				try { StarIOPort.releasePort(port); } catch (StarIOPortException e) {return -6;}
			}
		}

		return 0;
	}

	private static byte[] convertFromListByteArrayTobyteArray(List<byte[]> ByteArray)
	{
		int dataLength = 0;

		for (int i = 0; i < ByteArray.size(); i++)
		{
			dataLength += ByteArray.get(i).length;
		}

		int distPosition = 0;
		byte[] byteArray = new byte[dataLength];
		for (int i = 0; i < ByteArray.size(); i++)
		{
			System.arraycopy(ByteArray.get(i), 0, byteArray, distPosition, ByteArray.get(i).length);
			distPosition += ByteArray.get(i).length;
		}

		return byteArray;
	}

/*
	public static void PrintQrCode(Context context, String portName, String portSettings, CorrectionLevelOption correctionLevel, Model model, byte cellSize, byte[] barCodeData) {
		ArrayList<byte[]> commands = new ArrayList<byte[]>();

		byte[] modelCommand = new byte[] { 0x1b, 0x1d, 0x79, 0x53, 0x30, 0x00 };
		switch (model) {
			case Model1:
				modelCommand[5] = 1;
				break;
			case Model2:
				modelCommand[5] = 2;
				break;
		}

		commands.add(modelCommand);

		byte[] correctionLevelCommand = new byte[] { 0x1b, 0x1d, 0x79, 0x53, 0x31, 0x00 };
		switch (correctionLevel) {
			case Low:
				correctionLevelCommand[5] = 0;
				break;
			case Middle:
				correctionLevelCommand[5] = 1;
				break;
			case Q:
				correctionLevelCommand[5] = 2;
				break;
			case High:
				correctionLevelCommand[5] = 3;
				break;
		}
		commands.add(correctionLevelCommand);

		commands.add(new byte[] { 0x1b, 0x1d, 0x79, 0x53, 0x32, cellSize });

		// Add BarCode data
		commands.add(new byte[] { 0x1b, 0x1d, 0x79, 0x44, 0x31, 0x00, (byte) (barCodeData.length % 256), (byte) (barCodeData.length / 256) });
		commands.add(barCodeData);
		commands.add(new byte[] { 0x1b, 0x1d, 0x79, 0x50 } );

		sendCommand(context, portName, portSettings, commands);
	}
*/
}
