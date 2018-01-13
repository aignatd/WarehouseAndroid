package com.artolanggeng.purnamakertasindo.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.DataTimbangan;
import com.artolanggeng.purnamakertasindo.pojo.TimbangPojo;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.utils.RoleChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetTimbangan extends AppCompatActivity
{
  @BindView(R.id.tvHeader)
  TextView tvHeader;
  @BindView(R.id.ivMenu)
  ImageView ivMenu;

  @BindView(R.id.tvDataTimbangKecil)
  TextView tvDataTimbangKecil;
  @BindView(R.id.tvDataTimbangBesar)
  TextView tvDataTimbangBesar;
  @BindView(R.id.spDataTimbang)
  Spinner spDataTimbang;

  static String TAG = "[Setting Timbangan]";
  private Context context = this;

  private PopupMessege popupMessege = new PopupMessege();
  static ProgressDialog progressDialog;

  private List<DataTimbangan> lsDataTimbangans;

	private ArrayList<String> lst;

	private String strURLTimbang1;
  private String strURLTimbang2;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_settimbangan);
    ButterKnife.bind(this);

    tvHeader.setText(getResources().getString(R.string.titleHeaderTimbang));
    ivMenu.setVisibility(View.GONE);

    AmbilSemuaJenisTimbangan();
  }

  @OnClick({R.id.ivBackIcon, R.id.btnDataTimbangKecil, R.id.btnDataTimbangBesar, R.id.llSaveSetTimbang})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
      case R.id.btnDataTimbangKecil:
        tvDataTimbangKecil.setText(spDataTimbang.getSelectedItem().toString());
        strURLTimbang1 = lsDataTimbangans.get(spDataTimbang.getSelectedItemPosition()).getUrl();
      break;
      case R.id.btnDataTimbangBesar:
        tvDataTimbangBesar.setText(spDataTimbang.getSelectedItem().toString());
        strURLTimbang2 = lsDataTimbangans.get(spDataTimbang.getSelectedItemPosition()).getUrl();
      break;
	    case R.id.llSaveSetTimbang:
		    Fungsi.storeToSharedPref(context, strURLTimbang1, Preference.PrefUrlTimbang1);
		    Fungsi.storeToSharedPref(context, strURLTimbang2, Preference.PrefUrlTimbang2);
	    break;
    }
  }

  private void BackActivity()
  {
    RoleChecker roleChecker = new RoleChecker(SetTimbangan.this, context);
    if(roleChecker.RoleTimbangan() == 0)
      popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void AmbilSemuaJenisTimbangan()
  {
    progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
      context.getResources().getString(R.string.msgDataTimbang));
    progressDialog.setCancelable(false);

    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      return;
    }

    DataLink dataLink = Fungsi.BindingData();
    final Call<TimbangPojo> ReceivePojo = dataLink.DataTimbanganService(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));

    ReceivePojo.enqueue(new Callback<TimbangPojo>()
    {
      @Override
      public void onResponse(Call<TimbangPojo> call, Response<TimbangPojo> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode() == FixValue.intError)
            popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
          else
          {
	          lsDataTimbangans = response.body().getSetTimbangRsp();
            String[] items = new String[lsDataTimbangans.size()];

	          for(int i=0; i<lsDataTimbangans.size(); i++)
            {
	            items[i] = (i+1) + ". " + response.body().getSetTimbangRsp().get(i).getNama() +
		                      "/" + response.body().getSetTimbangRsp().get(i).getJenis() +
	                        "/" + response.body().getSetTimbangRsp().get(i).getIp() +
	                        "/" + response.body().getSetTimbangRsp().get(i).getBisnisunitkode();
            }

	          lst = new ArrayList<>(Arrays.asList(items));
	          ArrayAdapter dataAdapter = new ArrayAdapter<>(SetTimbangan.this, android.R.layout.simple_spinner_item, lst);
	          dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	          spDataTimbang.setAdapter(dataAdapter);
          }
        }
        else
          popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
      }

      @Override
      public void onFailure(Call<TimbangPojo> call, Throwable t)
      {
        progressDialog.dismiss();
        popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
      }
    });
  }
}
