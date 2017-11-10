package com.artolanggeng.purnamakertasindo.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.common.Utama;

public class Driver extends AppCompatActivity
{
  @BindView(R.id.tvHeader) TextView tvHeader;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_driver);
    ButterKnife.bind(this);

    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(getResources().getString(R.string.TitleDriver));
  }

  private void BackActivity()
  {
    Intent UtamaIntent = new Intent(Driver.this, Utama.class);
    startActivity(UtamaIntent);
    finish();
  }

  @OnClick(R.id.ivBackIcon)
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
    }
  }
}
