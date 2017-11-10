package com.artolanggeng.purnamakertasindo.common;

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
import com.artolanggeng.purnamakertasindo.utils.PesanPopup;

import java.util.ArrayList;
import java.util.List;

public class BuatAkun extends AppCompatActivity
{
  @BindView(R.id.tvHeader) TextView tvHeader;
  @BindView(R.id.etNoHP) EditText etNoHP;
  @BindView(R.id.etPass) EditText etPass;
  @BindView(R.id.etVerPass) EditText etVerPass;

  private String TAG = "[BuatAkun]";
  private PesanPopup pesan = new PesanPopup();
  private Context context = this;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

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
      break;
    }
  }
}
