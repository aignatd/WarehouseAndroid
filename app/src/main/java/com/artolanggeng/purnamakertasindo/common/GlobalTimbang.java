package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.koreksi.MainKoreksi;
import com.artolanggeng.purnamakertasindo.penjualan.MainJual;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.pojo.WarehousePojo;
import com.artolanggeng.purnamakertasindo.popup.DaftarDevice;
import com.artolanggeng.purnamakertasindo.popup.GantiPassword;
import com.artolanggeng.purnamakertasindo.sending.LogoutHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.timbanganKecil.formTimbanganKecil;
import com.artolanggeng.purnamakertasindo.timbangbesar.FormBesar;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.warehouse.MainProses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Nov-17
 * HP/WA : 0857 7070 6 777
 */
public class GlobalTimbang
{
	static String TAG = "[GlobalTimbang]";
	private Context context;
	private Activity activity;
	private PopupMessege popupMessege = new PopupMessege();
	static ProgressDialog progressDialog;

	public GlobalTimbang(Context context, Activity activity)
	{
		this.context = context;
		this.activity = activity;
	}

	public void ProsesLogout()
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgProsesLogout));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		User user = new User();
		user.setToken(Fungsi.getStringFromSharedPref(context, Preference.prefToken));

		LogoutHolder logoutHolder = new LogoutHolder(user);
		DataLink dataLink = Fungsi.BindingData();

		final Call<LoginPojo> ReceivePojo = dataLink.LogoutService(logoutHolder);

		ReceivePojo.enqueue(new Callback<LoginPojo>()
		{
			@Override
			public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						Fungsi.storeToSharedPref(context, "", Preference.prefToken);

						Intent LogoutIntent = new Intent(activity, Login.class);
						context.startActivity(LogoutIntent);
						activity.finish();
					}
				}
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

	public void ProsesPassword()
	{
		GantiPassword cdMenuHome = new GantiPassword(activity);
		cdMenuHome.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		cdMenuHome.show();
	}

	public void ProsesTimbangBesar()
	{
		Intent ProgresIntent = new Intent(activity, MainProses.class);
		context.startActivity(ProgresIntent);
		activity.finish();
	}

	public void ProsesJualBarang()
	{
		Intent JualBarangIntent = new Intent(activity, MainJual.class);
		context.startActivity(JualBarangIntent);
		activity.finish();
	}

	public void ProsesTimbangKecil()
	{
		Intent TimbangKecilIntent = new Intent(activity, formTimbanganKecil.class);
		context.startActivity(TimbangKecilIntent);
		activity.finish();
	}

	public void ProsesPageSelected(Integer position, Button btnbawah1, Button btnbawah2, Button btnbawah3)
	{
		if(position == 0)
		{
			btnbawah1.setBackgroundResource(R.color.appleGreen);

			if(btnbawah2 != null)
				btnbawah2.setBackgroundResource(R.color.whiteThree);

			btnbawah3.setBackgroundResource(R.color.whiteThree);
		}
		else
		if(position == 1)
		{
			btnbawah1.setBackgroundResource(R.color.whiteThree);

			if(btnbawah2 != null)
				btnbawah2.setBackgroundResource(R.color.appleGreen);

			if(btnbawah2 == null)
				btnbawah3.setBackgroundResource(R.color.appleGreen);
			else
				btnbawah3.setBackgroundResource(R.color.whiteThree);
		}
		else
		if(position == 2)
		{
			btnbawah1.setBackgroundResource(R.color.whiteThree);

			if(btnbawah2 != null)
				btnbawah2.setBackgroundResource(R.color.whiteThree);

			btnbawah3.setBackgroundResource(R.color.appleGreen);
		}
	}

	public void ProsesPemasokManual(Integer intTimbang, String strHistory, String strJual,
	                                final Class<?> clsTimbang)
	{
		Intent PemasokManualIntent = new Intent(activity, clsTimbang);
		PemasokManualIntent.putExtra("KodePemasok", Fungsi.getStringFromSharedPref(context, Preference.PrefScanQR));
		PemasokManualIntent.putExtra("Timbang", intTimbang);
		PemasokManualIntent.putExtra("History", strHistory);
		PemasokManualIntent.putExtra("Jual", strJual);
		PemasokManualIntent.putExtra("PekerjaanID", Fungsi.getStringFromSharedPref(context, Preference.PrefAntrianPemasok));
		PemasokManualIntent.putExtra("Pemasok", 0);
		activity.startActivity(PemasokManualIntent);
	}

	public void ProsesUpdateProfile()
	{
		Intent UpdateProfileIntent = new Intent(activity, UserProfile.class);
		context.startActivity(UpdateProfileIntent);
		activity.finish();
	}

	public void ProsesKoreksi()
	{
		Intent KoreksiIntent = new Intent(activity, MainKoreksi.class);
		context.startActivity(KoreksiIntent);
		activity.finish();
	}

	public void ProsesDaftarDevice()
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgDataWarehouse));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		DataLink dataLink = Fungsi.BindingData();
		final Call<WarehousePojo> ReceivePojo = dataLink.DataWarehouseService();

		ReceivePojo.enqueue(new Callback<WarehousePojo>()
		{
			@Override
			public void onResponse(Call<WarehousePojo> call, Response<WarehousePojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						DaftarDevice daftarDevice = new DaftarDevice(activity, response.body().getWarehouseRsps());
						daftarDevice.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						daftarDevice.show();
					}
				}
				else
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<WarehousePojo> call, Throwable t)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
