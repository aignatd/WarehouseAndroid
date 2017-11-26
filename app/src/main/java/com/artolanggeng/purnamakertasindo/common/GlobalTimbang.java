package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.penjualan.MainJual;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.popup.GantiPassword;
import com.artolanggeng.purnamakertasindo.sending.LogoutHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
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

	public void ProsesProfile()
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
}
