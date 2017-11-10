package com.artolanggeng.purnamakertasindo.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.utils.FixValue;

public class Splash extends AppCompatActivity
{
  private TextView tvVersion;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_splash);

    tvVersion = (TextView) findViewById(R.id.tvVersion);
    String myVersionName = "Unknown";
    Context context = getApplicationContext(); // or activity.getApplicationContext()
    PackageManager packageManager = context.getPackageManager();
    String packageName = context.getPackageName();

    try
    {
      myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
    }
    catch (PackageManager.NameNotFoundException e)
    {
      e.printStackTrace();
    }

    tvVersion.setText("Versi " + myVersionName);

    new Handler().postDelayed(new Runnable()
    {
      @Override
      public void run()
      {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(FixValue.strNamaPref, null);
        Intent mainIntent = new Intent(Splash.this, Login.class);
        startActivity(mainIntent);
        finish();
      }
    }, FixValue.SPLASH_DISPLAY_LENGHT);
  }
}
