package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.pojo.WarehousePojo;
import com.artolanggeng.purnamakertasindo.popup.DaftarDevice;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.warehouse.MainProses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimbangSuper extends AppCompatActivity
{
 @BindView(R.id.tvPilihTimbangan)
  TextView tvPilihTimbangan;
  @BindView(R.id.tvWarehouse)
  TextView tvWarehouse;

  static String TAG = "[TimbanganSuper]";
	private Activity activity = this;
  private Context context = this;
  private PopupMessege popupMessege = new PopupMessege();
  static ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

	  setContentView(R.layout.lay_timbang_super);
    ButterKnife.bind(this);

    tvPilihTimbangan.setText(getString(R.string.strHeaderTimbangan, Fungsi.getStringFromSharedPref(context, Preference.prefName)));
    tvWarehouse.setText(Fungsi.getStringFromSharedPref(context, Preference.prefWarehouse));

    /* Contoh ambil data dari sharedpreference menggunakan object
    LoginPojo loginPojo = Fungsi.getObjectFromSharedPref(context, LoginPojo.class, Preference.prefUser);
    Log.d(TAG, "onCreate: " + loginPojo.getUserResponse().getName());
    Log.d(TAG, "onCreate: " + loginPojo.getUserResponse().getRememberToken());
    Log.d(TAG, "onCreate: " + loginPojo.getUserResponse().getWareHouse());
    Log.d(TAG, "onCreate: " + loginPojo.getUserResponse().getKodewarehouse());
    Log.d(TAG, "onCreate: " + loginPojo.getUserResponse().getUserID());
    */
  }

  @OnClick({R.id.rlTimbangBesar, R.id.rlTimbangKecil, R.id.rlPelangganBaru, R.id.rlPrinter, R.id.rlKeluar,
	          R.id.rlDaftarDevice, R.id.rlProfile, R.id.rlPassword, R.id.rlJualBarang})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.rlTimbangBesar:
      case R.id.rlTimbangKecil:
        Intent ProgresIntent = new Intent(TimbangSuper.this, MainProses.class);
        startActivity(ProgresIntent);
        finish();
      break;
      case R.id.rlDaftarDevice:
        ProsesDaftarDevice();
      break;
      case R.id.rlPelangganBaru:
        Intent PemasokIntent = new Intent(TimbangSuper.this, Pemasok.class);
        startActivity(PemasokIntent);
        finish();
      break;
      case R.id.rlPrinter:
        Intent PrinterIntent = new Intent(TimbangSuper.this, Printer.class);
        startActivity(PrinterIntent);
        finish();
      break;
      case R.id.rlKeluar:
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
          .setTitle(R.string.titleMessege)
          .setMessage(getResources().getString(R.string.msgLogout))
          .setIcon(android.R.drawable.ic_dialog_alert)
          .setCancelable(false)
          .setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
	            GlobalTimbang globalTimbang = new GlobalTimbang(context, activity);
	            globalTimbang.ProsesLogout();
            }
          })
          .setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface dialog, int id)
              {
                dialog.dismiss();
              }
            }
          );

        AlertDialog alert = builder.create();
        alert.show();
      break;
	    case R.id.rlPassword:
		    GlobalTimbang globalTimbang = new GlobalTimbang(context, activity);
		    globalTimbang.ProsesProfile();
	    break;
	    case R.id.rlJualBarang:
		    globalTimbang = new GlobalTimbang(context, activity);
		    globalTimbang.ProsesJualBarang();
		  break;
    }
  }

  @Override
  public void onBackPressed()
  {
    moveTaskToBack(true);
  }

  private void ProsesDaftarDevice()
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
