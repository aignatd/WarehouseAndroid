package com.artolanggeng.purnamakertasindo.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.Device;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.pojo.WarehousePojo;
import com.artolanggeng.purnamakertasindo.sending.PasswordHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PesanPopup;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class BuatAkun extends AppCompatActivity
{
  @BindView(R.id.tvHeader) TextView tvHeader;
  @BindView(R.id.etNoHP) EditText etNoHP;
  @BindView(R.id.etPass) EditText etPass;
  @BindView(R.id.etVerPass) EditText etVerPass;
  @BindView(R.id.etFullName) EditText etFullName;

  private String TAG = "[BuatAkun]";
  private PesanPopup pesan = new PesanPopup();
  private Context context = this;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();
	private PopupMessege popupMessege = new PopupMessege();
	static ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_buatakun);
    ButterKnife.bind(this);

    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(getResources().getString(R.string.TitleHeadAkun));
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void BackActivity()
  {
    Intent BuatAkunIntent = new Intent(BuatAkun.this, Login.class);
    startActivity(BuatAkunIntent);
    finish();
  }

  @OnClick({R.id.ivBackIcon, R.id.btnKirim})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
      case R.id.btnKirim:
        ProsesRegistrasi();
      break;
    }
  }

  private void ProsesRegistrasi()
  {
    lstInput.clear();
    lstMsg.clear();
    lstInput.add(etNoHP);
    lstInput.add(etPass);
    lstInput.add(etVerPass);
    lstInput.add(etFullName);
	  lstMsg.add(getResources().getString(R.string.msgUsername));
    lstMsg.add(getResources().getString(R.string.msgPassword));
    lstMsg.add(getResources().getString(R.string.msgVerPassword));
	  lstMsg.add(getResources().getString(R.string.msgNamaLengkap));

	  if(!etPass.getText().toString().trim().matches(etVerPass.getText().toString().trim()))
		  popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgPassBeda));
		else
    if(Fungsi.CekInput(lstInput, lstMsg, context))
    {
	    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
		    context.getResources().getString(R.string.msgDataDevice));
	    progressDialog.setCancelable(false);

	    if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
	    {
		    progressDialog.dismiss();
		    popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
		    return;
	    }

	    User user = new User();
	    user.setName(etFullName.getText().toString().trim());
	    user.setUsername(etNoHP.getText().toString().trim());
	    user.setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPass.getText().toString().trim()))));

	    Device device = new Device();
	    device.setDeviceid(Fungsi.DeviceInfo(context, 0));
	    device.setDevice(Fungsi.DeviceName());
	    device.setOs(Fungsi.AndroidVersion());
	    device.setTipe(Fungsi.DeviceTipe(context));

	    PasswordHolder passwordHolder = new PasswordHolder(user, device);
	    DataLink dataLink = Fungsi.BindingData();
	    final Call<WarehousePojo> ReceivePojo = dataLink.DaftarUserService(passwordHolder);

	    ReceivePojo.enqueue(new Callback<WarehousePojo>()
	    {
		    @Override
		    public void onResponse(Call<WarehousePojo> call, Response<WarehousePojo> response)
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
		    public void onFailure(Call<WarehousePojo> call, Throwable t)
		    {
			    progressDialog.dismiss();
			    popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
		    }
	    });
    }
  }
}
