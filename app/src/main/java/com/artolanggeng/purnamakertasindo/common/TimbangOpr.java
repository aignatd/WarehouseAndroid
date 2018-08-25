package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;

public class TimbangOpr extends AppCompatActivity
{
 @BindView(R.id.tvPilihTimbangan)
  TextView tvPilihTimbangan;
  @BindView(R.id.tvWarehouse)
  TextView tvWarehouse;

  static String TAG = "[TimbanganOpr]";
	private Activity activity = this;
  private Context context = this;
  private PopupMessege popupMessege = new PopupMessege();
  static ProgressDialog progressDialog;

  private GlobalTimbang globalTimbang;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);


	  setContentView(R.layout.lay_timbang_opr);
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

  @OnClick({R.id.rlTimbangBesar, R.id.rlTimbangKecil, R.id.rlKeluar, R.id.rlJualBarang, R.id.rlProfile,
            R.id.rlPassword, R.id.rlKoreksi, R.id.rlBantuan})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.rlTimbangKecil:
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesTimbangKecil();
      break;
      case R.id.rlTimbangBesar:
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesTimbangBesar();
      break;
      case R.id.rlPrinter:
        Intent PrinterIntent = new Intent(TimbangOpr.this, Printer.class);
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
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesPassword();
      break;
      case R.id.rlJualBarang:
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesJualBarang();
      break;
      case R.id.rlProfile:
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesUpdateProfile();
      break;
      case R.id.rlKoreksi:
        globalTimbang = new GlobalTimbang(context, activity);
        globalTimbang.ProsesKoreksi();
      break;
      case R.id.rlBantuan:
        Intent browserX = new Intent(Intent.ACTION_VIEW, Uri.parse("http://artolanggeng.ip-dynamic.com:40080/warehouse"));
        startActivity(browserX);
      break;
    }
  }

  @Override
  public void onBackPressed()
  {
    moveTaskToBack(true);
  }
}
