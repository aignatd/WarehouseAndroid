package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Nov-17
 * HP/WA : 0857 7070 6 777
 */
public class Photo extends AppCompatActivity
{
	private static String TAG = "[Photo]";
	static final int REQUEST_IMAGE_CAPTURE = 1;

	private Context context;
	private Activity activity;
	private int intPekerjaanID;
	private String strPemasokID;

	static ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();

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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		Log.d(TAG, "onActivityResult -> " + requestCode);
		Log.d(TAG, "onActivityResult -> " + resultCode);

		if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
		{
			boolean sukses = true;
			String warehouse = Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse);

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);

			String strNamaFile = warehouse + "_" + strPemasokID + "_" + String.valueOf(intPekerjaanID) +
													 "_" + df.format(calendar.getTime()) + ".jpg";

			Log.d(TAG, "onActivityResult -> " + strNamaFile);

			File image = Fungsi.FolderAplikasi();
			File FileProfile = new File(image.getAbsolutePath() + "/" + strNamaFile);

			Log.d(TAG, "onActivityResult -> " + image.getAbsolutePath() + "/" + strNamaFile);

			try
			{
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
				Fungsi.SimpanGambar(bitmap, FileProfile);
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

	private void ProsesUploadPhoto(File FileProfile, String strNamaFile, String warehouse)
	{
		progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgUploadPhoto));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		RequestBody rbPemasokID = RequestBody.create(MediaType.parse("text/plain"), strPemasokID);
		RequestBody rbWarehouse = RequestBody.create(MediaType.parse("text/plain"), warehouse);
		RequestBody rbPekerjaanID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(intPekerjaanID));
		RequestBody rbFile = RequestBody.create(MediaType.parse("image/jpeg"), FileProfile);
		MultipartBody.Part Namafile = MultipartBody.Part.createFormData("Photo", strNamaFile, rbFile);

		DataLink dataLink = Fungsi.BindingData();
		final Call<LoginPojo> ReceivePojo = dataLink.PhotoBarangService(rbPemasokID, rbWarehouse, rbPekerjaanID, Namafile);

		ReceivePojo.enqueue(new Callback<LoginPojo>()
		{
			@Override
			public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
					popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
				else
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<LoginPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
