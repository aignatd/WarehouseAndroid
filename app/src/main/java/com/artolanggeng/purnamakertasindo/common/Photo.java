package com.artolanggeng.purnamakertasindo.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo extends AppCompatActivity
{
	private static String TAG = "[Photo]";
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private Context context;

	private String strPemasokID;
	private Integer intPekerjaanID;
	private String kodewarehouse;
	private String strNamaFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();

		intPekerjaanID = extras.getInt("PekerjaanID");
		strPemasokID = extras.getString("PemasokID");
		kodewarehouse = extras.getString("KodeWarehouse");

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (cameraIntent.resolveActivity(getPackageManager()) != null)
		{
			// Create the File where the photo should go
			File photoFile = null;

			try
			{
				photoFile = createImageFile();
			}
			catch (IOException ex)
			{
				// Error occurred while creating the File
				Log.i(TAG, "IOException");
			}
			// Continue only if the File was successfully created
			if (photoFile != null)
			{
//				Uri photoURI = FileProvider.getUriForFile(this, "com.artolanggeng.purnamakertasindo", photoFile);
//				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
		{
			boolean sukses = true;

			try
			{
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(strNamaFile));
			}
			catch(IOException e)
			{
				Log.d(TAG, "onActivityResult -> Simpan photo error");
				sukses = false;
			}

//			if(sukses)
//				ProsesUploadPhoto(FileProfile, strNamaFile, warehouse);
		}
	}

	private File createImageFile() throws IOException
	{
		// Create an image file name
		String imageFileName = kodewarehouse + "_" + strPemasokID + "_" + String.valueOf(intPekerjaanID) +
			"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		File storageDir = Fungsi.FolderAplikasi();
		File image = File.createTempFile(imageFileName,  ".jpg", storageDir);

		// Save a file: path for use with ACTION_VIEW intents
		strNamaFile = "file:" + image.getAbsolutePath();
		return image;
	}
}
