package com.artolanggeng.purnamakertasindo.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import com.artolanggeng.purnamakertasindo.R;

public class Bantuan extends AppCompatActivity
{
  private WebView wvBantuan;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_bantuan);

    wvBantuan = findViewById(R.id.wvBantuan);
    wvBantuan.loadUrl("http://artolanggeng.ip-dynamic.com:40080/warehouse");
  }
}
