package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.sending.PasswordHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class GantiPassword extends Dialog
{
  @BindView(R.id.etPassLama)
  EditText etPassLama;
  @BindView(R.id.etPassBaru)
  EditText etPassBaru;
  @BindView(R.id.etPassVer)
  EditText etPassVer;

  private String TAG = "[Password]";
  private ProgressDialog progressDialog;
  private PopupMessege popupMessege = new PopupMessege();
  private List<EditText> lstInput = new ArrayList<>();
  private List<String> lstMsg = new ArrayList<>();
  private Context context = getContext();

  public Activity ParentAct;

  public GantiPassword(Activity parentAct)
  {
    super(parentAct);
    this.ParentAct = parentAct;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.lay_gantipassword);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.btnGantiPass, R.id.btnBatalPass})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.btnGantiPass:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etPassLama);
        lstInput.add(etPassBaru);
        lstMsg.add(ParentAct.getResources().getString(R.string.msgPassLama));
        lstMsg.add(ParentAct.getResources().getString(R.string.msgPassKosong));

        if(Fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          if(!etPassBaru.getText().toString().matches(etPassVer.getText().toString()))
          {
            popupMessege.ShowMessege1(getContext(), ParentAct.getResources().getString(R.string.msgPassBeda));
            etPassBaru.requestFocus();
          }
          else if(etPassLama.getText().toString().matches(etPassBaru.getText().toString()))
          {
            popupMessege.ShowMessege1(getContext(), ParentAct.getResources().getString(R.string.msgPassSama));
            etPassBaru.requestFocus();
          }
          else
            ProsesGantiPass();
        }
      break;
      case R.id.btnBatalPass:
        dismiss();
      break;
    }
  }

  private void ProsesGantiPass()
  {
    progressDialog = ProgressDialog.show(context, ParentAct.getResources().getString(R.string.msgHarapTunggu),
      context.getResources().getString(R.string.msgProsesLogout));
    progressDialog.setCancelable(false);

    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      return;
    }

    User user = new User();
    user.setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPassLama.getText().toString().trim()))));
    user.setPasswordBaru(new String(Hex.encodeHex(DigestUtils.md5(etPassBaru.getText().toString().trim()))));
    user.setToken(Fungsi.getStringFromSharedPref(context, Preference.prefToken));

    PasswordHolder passwordHolder = new PasswordHolder(user);
    DataLink dataLink = Fungsi.BindingData();

    final Call<LoginPojo> ReceivePojo = dataLink.PasswordService(passwordHolder);

    ReceivePojo.enqueue(new Callback<LoginPojo>()
    {
      @Override
      public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());

          if(response.body().getCoreResponse().getKode() == FixValue.intSuccess)
            dismiss();
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

