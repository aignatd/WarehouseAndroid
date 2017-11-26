package com.artolanggeng.purnamakertasindo.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo extends AppCompatActivity
{
	private static String TAG = "[Photo]";
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private Context context = this;

	static ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();

	private String strPemasokID;
	private Integer intPekerjaanID;
	private String kodewarehouse;
	private String strNamaFile;
	private File photoFile;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();

		intPekerjaanID = extras.getInt("PekerjaanID");
		strPemasokID = extras.getString("PemasokID");
		kodewarehouse = extras.getString("KodeWarehouse");

		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());

		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (cameraIntent.resolveActivity(getPackageManager()) != null)
		{
			// Create the File where the photo should go
			photoFile = null;

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
			if(data!=null)
/*
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

			Log.d(TAG, "ProsesUploadPhoto: " + sukses);

			if(sukses)
*/
				ProsesUploadPhoto();
		}
	}

	private File createImageFile() throws IOException
	{
		// Create an image file name
		strNamaFile = kodewarehouse + "_" + strPemasokID + "_" + String.valueOf(intPekerjaanID) +
			"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		File storageDir = Fungsi.FolderAplikasi();
		File image = File.createTempFile(strNamaFile,  ".jpg", storageDir);

		return image;
	}

	private void ProsesUploadPhoto()
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgUploadPhoto));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		RequestBody rbPemasokID = RequestBody.create(MediaType.parse("text/plain"), strPemasokID);
		RequestBody rbWarehouse = RequestBody.create(MediaType.parse("text/plain"), kodewarehouse);
		RequestBody rbPekerjaanID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intPekerjaanID));
		RequestBody rbFile = RequestBody.create(MediaType.parse("image/jpeg"), photoFile);
		MultipartBody.Part Namafile = MultipartBody.Part.createFormData("Photo", strNamaFile, rbFile);

		DataLink dataLink = Fungsi.BindingData();
		final Call<LoginPojo> ReceivePojo = dataLink.PhotoBarangService(rbPemasokID, rbWarehouse, rbPekerjaanID, Namafile);

		ReceivePojo.enqueue(new Callback<LoginPojo>()
		{
			@Override
			public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
			}

			@Override
			public void onFailure(Call<LoginPojo> call, Throwable t)
			{
				Log.d(TAG, "onFailure: " + t.toString());
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
