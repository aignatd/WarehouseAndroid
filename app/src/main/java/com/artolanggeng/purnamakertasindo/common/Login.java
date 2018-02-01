package com.artolanggeng.purnamakertasindo.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.*;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.sending.LoginHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity
{
  @BindView(R.id.etUsername)
  EditText etUsername;
  @BindView(R.id.etPass)
  EditText etPass;
  @BindView(R.id.tvDaftarLogin)
  TextView tvDaftarLogin;

  static String TAG = "[Login]";
  private Context context = this;
  private PopupMessege popupMessege = new PopupMessege();
  static ProgressDialog progressDialog;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_login);

    Fungsi.CheckPermission(Login.this, context);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.btnLogin, R.id.tvDaftarLogin})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.btnLogin:
        LoginUser();
      break;
      case R.id.tvDaftarLogin:
        Intent BuatAkunIntent = new Intent(Login.this, BuatAkun.class);
        startActivity(BuatAkunIntent);
        finish();
      break;
    }
  }

  private void LoginUser()
  {
    lstInput.clear();
    lstMsg.clear();
    lstInput.add(etUsername);
    lstInput.add(etPass);
    lstMsg.add(getResources().getString(R.string.msgUsername));
    lstMsg.add(getResources().getString(R.string.msgPassword));

    if(Fungsi.CekInput(lstInput, lstMsg, context))
    {
      progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
                                           context.getResources().getString(R.string.msgProsesLogin));
      progressDialog.setCancelable(false);

      if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
      {
        progressDialog.dismiss();
        popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
        return;
      }

      User user = new User();
      user.setUsername(etUsername.getText().toString().trim());
      user.setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPass.getText().toString().trim()))));

      Device device = new Device();
      device.setDeviceid(Fungsi.DeviceInfo(context, 0));
      device.setDevice(Fungsi.DeviceName());
      device.setTipe(Fungsi.DeviceTipe(context));
      device.setOs(Fungsi.AndroidVersion());

      final LoginHolder loginHolder = new LoginHolder(user, device);
      DataLink dataLink = Fungsi.BindingData();

      final Call<LoginPojo> ReceivePojo = dataLink.LoginService(loginHolder);

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
              Fungsi.storeToSharedPref(context, response.body().getUserResponse().getName(), Preference.prefName);
              Fungsi.storeToSharedPref(context, response.body().getUserResponse().getRememberToken(), Preference.prefToken);
              Fungsi.storeToSharedPref(context, response.body().getUserResponse().getWareHouse(), Preference.prefWarehouse);
              Fungsi.storeToSharedPref(context, response.body().getUserResponse().getKodewarehouse(), Preference.prefKodeWarehouse);
              Fungsi.storeToSharedPref(context, response.body().getUserResponse().getUserID(), Preference.prefUserID);

              Role.initRole();
              Role.getInstance().setRoleResponse(response.body().getRoleResponse());

              IsiProduct.initIsiProduct();
              IsiProduct.getInstance().setmProductRsps(response.body().getProductrsp());
              IsiProduct.getInstance().setmJualanRsps(response.body().getJualanrsp());

              IsiPotongan.initIsiPotongan();
              IsiPotongan.getInstance().setmPotongRsp(response.body().getPotongRsp());

              /* Contoh simpan data ke sharedpreference menggunakan object
              Fungsi.storeObjectToSharedPref(context, response.body(), Preference.prefUser);
              */

              RoleChecker roleChecker = new RoleChecker(Login.this, context);
              if(roleChecker.RoleTimbangan() == 0)
              	popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
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
  }
}
