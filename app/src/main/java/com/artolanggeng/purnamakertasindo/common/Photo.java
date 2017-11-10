package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Nov-17
 * HP/WA : 0857 7070 6 777
 */
public class Photo extends AppCompatActivity
{
	private static String TAG = "[Proses_Adp]";
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private Bitmap mImageBitmap;
	private String mCurrentPhotoPath;
	private ImageView mImageView;
	private Context context;
	private Activity activity;
	private int intPekerjaanID;
	private String strPemasokID;

	public Photo(Context context, Activity activity, int intPekerjaanID, String strPemasokID)
	{
		this.context = context;
		this.activity = activity;
		this.intPekerjaanID = intPekerjaanID;
		this.strPemasokID = strPemasokID;
	}

	public void AmbilPhoto()
	{
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
/*
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(context.getPackageManager()) != null)
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
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
*/
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
		{
			Bitmap bp = (Bitmap) data.getExtras().get("data");
//			capturedImage.setImageBitmap(bp);
		}
	}
}
