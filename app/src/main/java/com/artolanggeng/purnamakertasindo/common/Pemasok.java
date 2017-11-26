package com.artolanggeng.purnamakertasindo.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.Customer;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.sending.CustomerHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Pemasok extends AppCompatActivity
{
  @BindView(R.id.tvHeader)
  TextView tvHeader;
  @BindView(R.id.ivMenu)
  ImageView ivMenu;
  @BindView(R.id.etTglPemasok)
  EditText etTglPemasok;
  @BindView(R.id.etEmailPemasok)
  EditText etEmailPemasok;
  @BindView(R.id.bntKirimPemasok)
  Button bntKirimPemasok;
  @BindView(R.id.etPemasok)
  EditText etPemasok;
  @BindView(R.id.etAlamatBaru)
  EditText etAlamatBaru;
  @BindView(R.id.etNoHPPemasok)
  EditText etNoHPPemasok;
  @BindView(R.id.cbPerusahaan)
  CheckBox cbPerusahaan;
  @BindView(R.id.etPerusahaan)
  EditText etPerusahaan;
  @BindView(R.id.etPanggilan)
  EditText etPanggilan;
  @BindView(R.id.etNoID)
  EditText etNoID;
  @BindView(R.id.rgSeks)
  RadioGroup rgSeks;
  @BindView(R.id.rgJenisID)
  RadioGroup rgJenisID;

  static String TAG = "[Pemasok]";
  private Context context = this;
  Calendar dateandtime;

  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  private PopupMessege popupMessege = new PopupMessege();
  static ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_pemasok);
    ButterKnife.bind(this);

    etPerusahaan.setEnabled(false);
    etPerusahaan.setCursorVisible(false);
    tvHeader.setText(getResources().getString(R.string.strPemasok));
    ivMenu.setVisibility(View.GONE);
    bntKirimPemasok.setVisibility(View.VISIBLE);
    dateandtime = Calendar.getInstance(Locale.US);

    etTglPemasok.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View v, boolean hasFocus)
      {
        if(hasFocus)
        {
          myDatePickerDialog dp = new myDatePickerDialog(context, dateandtime, new myDatePickerDialog.DatePickerListner()
          {
            @Override
            public void OnDoneButton(Dialog datedialog, Calendar c)
            {
              datedialog.dismiss();
              dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
              dateandtime.set(Calendar.MONTH, c.get(Calendar.MONTH));
              dateandtime.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
              etTglPemasok.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
              etTglPemasok.requestFocus();
            }

            @Override
            public void OnCancelButton(Dialog datedialog)
            {
              datedialog.dismiss();
              etEmailPemasok.requestFocus();
            }
          });

          dp.show();
        }
      }
    });
  }

  @OnClick({R.id.ivBackIcon, R.id.bntKirimPemasok, R.id.cbPerusahaan})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
      case R.id.bntKirimPemasok:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etPemasok);
        lstInput.add(etAlamatBaru);
        lstInput.add(etNoHPPemasok);
        lstInput.add(etPanggilan);

        lstMsg.add(getResources().getString(R.string.msgPemasok));
        lstMsg.add(getResources().getString(R.string.msgAlamatBaru));
        lstMsg.add(getResources().getString(R.string.msgNoHPPemasok));
        lstMsg.add(getResources().getString(R.string.msgPanggilan));

        if(Fungsi.CekInput(lstInput, lstMsg, context))
        {
          if(rgJenisID.getCheckedRadioButtonId() == -1)
          {
            etNoID.setText("");
            KirimDataPemasok();
          }
          else
          {
            if(etNoID.getText().toString().trim().isEmpty())
              popupMessege.ShowMessege1(context, getResources().getString(R.string.msgInputID));
            else
              KirimDataPemasok();
          }
        }
      break;
      case R.id.cbPerusahaan:
        if(cbPerusahaan.isChecked())
        {
          etPerusahaan.setEnabled(true);
          etPerusahaan.setCursorVisible(true);
        }
        else
        {
          etPerusahaan.setEnabled(false);
          etPerusahaan.setCursorVisible(false);
        }
      break;
    }
  }


  private void BackActivity()
  {
    RoleChecker roleChecker = new RoleChecker(Pemasok.this, context);
    if(roleChecker.RoleTimbangan() == 0)
      popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void KirimDataPemasok()
  {
    progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
      context.getResources().getString(R.string.msgProsesPemasok));
    progressDialog.setCancelable(false);

    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      return;
    }

    Customer customer = new Customer();
    customer.setContactPerson(etPemasok.getText().toString().trim());
    customer.setAlamat(etAlamatBaru.getText().toString().trim());
    customer.setTelpon(etNoHPPemasok.getText().toString().trim());
    customer.setEmail(etEmailPemasok.getText().toString().trim());
    customer.setTgllahir(etTglPemasok.getText().toString().trim());
    customer.setPanggilan(etPanggilan.getText().toString().trim());
    customer.setKodewarehouse(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));

    if(cbPerusahaan.isChecked())
    {
      customer.setPerusahaan(etPerusahaan.getText().toString().trim());
      customer.setJenis(2);
    }
    else
    {
      customer.setJenis(1);
      customer.setPerusahaan("Perorangan");
    }

    int idx = rgSeks.indexOfChild(findViewById(rgSeks.getCheckedRadioButtonId()));
    RadioButton rb = (RadioButton) rgSeks.getChildAt(idx);
    customer.setJeniskelamin(rb.getText().toString());

    idx = rgJenisID.indexOfChild(findViewById(rgJenisID.getCheckedRadioButtonId()));
    customer.setIdentitasid(idx+1);

    if(rgJenisID.getCheckedRadioButtonId() == -1)
      customer.setNoidentitas("");
    else
      customer.setNoidentitas(etNoID.getText().toString().trim());

    customer.setToken(Fungsi.getStringFromSharedPref(context, Preference.prefToken));

    CustomerHolder customerHolder = new CustomerHolder(customer);
    DataLink dataLink = Fungsi.BindingData();

    final Call<CustomerPojo> ReceivePojo = dataLink.KartuBaruService(customerHolder);

    ReceivePojo.enqueue(new Callback<CustomerPojo>()
    {
      @Override
      public void onResponse(Call<CustomerPojo> call, Response<CustomerPojo> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode() == FixValue.intError)
            popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
          else
          {
            RoleChecker roleChecker = new RoleChecker(Pemasok.this, context);
            if(roleChecker.RoleTimbangan() == 0)
              popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
          }
        }
        else
          popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
      }

      @Override
      public void onFailure(Call<CustomerPojo> call, Throwable t)
      {
        progressDialog.dismiss();
        popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
      }
    });
  }
}
