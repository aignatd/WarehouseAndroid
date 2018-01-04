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
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.pojo.ProfilePojo;
import com.artolanggeng.purnamakertasindo.sending.CustomerHolder;
import com.artolanggeng.purnamakertasindo.sending.LoginHolder;
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

public class UserProfile extends AppCompatActivity
{
  @BindView(R.id.tvHeader)
  TextView tvHeader;
  @BindView(R.id.ivMenu)
  ImageView ivMenu;

  @BindView(R.id.etProfile)
  EditText etProfile;
  @BindView(R.id.etAlamatProfile)
  EditText etAlamatProfile;
  @BindView(R.id.etHPProfile)
  EditText etHPProfile;
  @BindView(R.id.etEmailProfile)
  EditText etEmailProfile;
  @BindView(R.id.etTmpProfile)
  EditText etTmpProfile;
  @BindView(R.id.etTglProfile)
  EditText etTglProfile;
  @BindView(R.id.rgSeks)
  RadioGroup rgSeks;

  static String TAG = "[User Profile]";
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
    setContentView(R.layout.lay_userprofile);
    ButterKnife.bind(this);

    tvHeader.setText(getResources().getString(R.string.TitleProfile));
    ivMenu.setVisibility(View.GONE);
    dateandtime = Calendar.getInstance(Locale.US);

    etTglProfile.setOnFocusChangeListener(new View.OnFocusChangeListener()
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
              etTglProfile.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
              etTglProfile.requestFocus();
            }

            @Override
            public void OnCancelButton(Dialog datedialog)
            {
              datedialog.dismiss();
              etTglProfile.requestFocus();
            }
          });

          dp.show();
        }
      }
    });

    AmbilDataProfile();
  }

  @OnClick({R.id.ivBackIcon, R.id.btnUpdateProfile})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
      case R.id.btnUpdateProfile:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etProfile);
        lstInput.add(etAlamatProfile);
        lstInput.add(etHPProfile);

        lstMsg.add(getResources().getString(R.string.msgPemasok));
        lstMsg.add(getResources().getString(R.string.msgAlamatBaru));
        lstMsg.add(getResources().getString(R.string.msgNoHPPemasok));

        if(Fungsi.CekInput(lstInput, lstMsg, context))
          UpdateDataProfile();
      break;
    }
  }


  private void BackActivity()
  {
    RoleChecker roleChecker = new RoleChecker(UserProfile.this, context);
    if(roleChecker.RoleTimbangan() == 0)
      popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void UpdateDataProfile()
  {
    progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
      context.getResources().getString(R.string.msgProsesProfile));
    progressDialog.setCancelable(false);

    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      return;
    }

    Customer customer = new Customer();
    customer.setContactPerson(etProfile.getText().toString().trim());
    customer.setAlamat(etAlamatProfile.getText().toString().trim());
    customer.setTelpon(etHPProfile.getText().toString().trim());
    customer.setEmail(etEmailProfile.getText().toString().trim());
    customer.setTmplahir(etTmpProfile.getText().toString().trim());
    customer.setTgllahir(etTglProfile.getText().toString().trim());
    customer.setKodewarehouse(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));

    int idx = rgSeks.indexOfChild(findViewById(rgSeks.getCheckedRadioButtonId()));
    RadioButton rb = (RadioButton) rgSeks.getChildAt(idx);
    customer.setJeniskelamin(rb.getText().toString());

    customer.setToken(Fungsi.getStringFromSharedPref(context, Preference.prefToken));

    CustomerHolder customerHolder = new CustomerHolder(customer);
    DataLink dataLink = Fungsi.BindingData();

    final Call<CustomerPojo> ReceivePojo = dataLink.UpdateProfileService(customerHolder);

    ReceivePojo.enqueue(new Callback<CustomerPojo>()
    {
      @Override
      public void onResponse(Call<CustomerPojo> call, Response<CustomerPojo> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
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

  private void AmbilDataProfile()
  {
    progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
      context.getResources().getString(R.string.msgProsesProfile));
    progressDialog.setCancelable(false);

    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      return;
    }

    User user = new User();
    user.setToken(Fungsi.getStringFromSharedPref(context, Preference.prefToken));

    LoginHolder loginHolder = new LoginHolder(user, null);
    DataLink dataLink = Fungsi.BindingData();

    final Call<ProfilePojo> ReceivePojo = dataLink.AmbilProfileService(loginHolder);

    ReceivePojo.enqueue(new Callback<ProfilePojo>()
    {
      @Override
      public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode() == FixValue.intError)
            popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
          else
          {
            etProfile.setText(response.body().getProfileRsp().getName());
	          etAlamatProfile.setText(response.body().getProfileRsp().getAlamat());
	          etHPProfile.setText(response.body().getProfileRsp().getPhone());
	          etEmailProfile.setText(response.body().getProfileRsp().getEmail());
	          etTmpProfile.setText(response.body().getProfileRsp().getTmplahir());
	          etTglProfile.setText(response.body().getProfileRsp().getTgllahir());
          }
        }
        else
          popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
      }

      @Override
      public void onFailure(Call<ProfilePojo> call, Throwable t)
      {
        progressDialog.dismiss();
        popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
      }
    });
  }
}
