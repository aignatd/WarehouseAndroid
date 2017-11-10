package com.artolanggeng.purnamakertasindo.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.popup.GantiPassword;
import com.artolanggeng.purnamakertasindo.setting.Armada;
import com.artolanggeng.purnamakertasindo.setting.Driver;
import com.artolanggeng.purnamakertasindo.setting.Profile;
import com.artolanggeng.purnamakertasindo.utils.PesanPopup;

import java.util.ArrayList;
import java.util.List;

public class Utama extends AppCompatActivity
{
  @BindView(R.id.ivBackIcon)
  ImageView ivBackIcon;
  @BindView(R.id.tvHeader)
  TextView tvHeader;
  @BindView(R.id.ivNextIcon)
  ImageView ivNextIcon;
  @BindView(R.id.ivProUtama)
  ImageView ivProUtama;
  @BindView(R.id.tvNamaUtama)
  TextView tvNamaUtama;
  @BindView(R.id.tvNoHpUtama)
  TextView tvNoHpUtama;
  @BindView(R.id.llProfile)
  LinearLayout llProfile;
  @BindView(R.id.rlProfile)
  RelativeLayout rlProfile;
  @BindView(R.id.tvAbsen)
  TextView tvAbsen;
  @BindView(R.id.tvPSB)
  TextView tvPSB;
  @BindView(R.id.vfAbsen)
  ViewFlipper vfAbsen;
  @BindView(R.id.vfPSB)
  ViewFlipper vfPSB;

  private String TAG = "[Utama]";
  private ProgressDialog progressDialog;
  private Context context = this;
  private PesanPopup pesan = new PesanPopup();
  private Activity activity = this;
  private List<String> lstLogin = new ArrayList<>();

  List<String> lstLogout;

  private String strKomponen = "";

  int[] imgAbsen = {R.drawable.qrcodelands, R.drawable.qrcodeteks};
  int[] imgPSB = {R.drawable.psb, R.drawable.psbteks};

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_utama);
    ButterKnife.bind(this);

    lstLogout = new ArrayList<>();
    BindingView();
    BindingSlide();
  }

  private void BindingView()
  {
    ivBackIcon.setImageResource(R.drawable.ic_home);

    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(R.string.srtDashboard);

    ivNextIcon.setVisibility(View.VISIBLE);
    ivNextIcon.setImageResource(R.drawable.ic_list_menu);
  }

  private void BindingSlide()
  {
    for(int i = 0; i < imgAbsen.length; i++)
    {
      ImageView ivFlipper = new ImageView(Utama.this);
      ivFlipper.setImageResource(imgAbsen[i]);
      ivFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
      vfAbsen.addView(ivFlipper);
    }

    vfAbsen.setFlipInterval(15000);
    vfAbsen.startFlipping();

    for(int j = 0; j < imgPSB.length; j++)
    {
      Log.d("", "");

      ImageView ivFlipper = new ImageView(Utama.this);
      ivFlipper.setImageResource(imgPSB[j]);
      ivFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
      vfPSB.addView(ivFlipper);
    }

    vfPSB.setFlipInterval(10000);
    vfPSB.startFlipping();
  }

  @Override
  public void onBackPressed()
  {
    moveTaskToBack(true);
  }

  @OnClick(R.id.ivNextIcon)
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivNextIcon:
        MenuBuilder menuBuilder = new MenuBuilder(context);
        new SupportMenuInflater(context).inflate(R.menu.menu_other, menuBuilder);

        menuBuilder.setCallback(new MenuBuilder.Callback()
        {
          @Override
          public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem)
          {
            switch(menuItem.getItemId())
            {
              case R.id.mnuProfile:
                EditProfile();
              return true;
              case R.id.mnuRubahPass:
                GantiPassword cdMenuPass = new GantiPassword(Utama.this);
                cdMenuPass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdMenuPass.show();
              return true;
            }

            return false;
          }

          @Override
          public void onMenuModeChange(MenuBuilder menu)
          {
          }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, ivNextIcon);
        menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
        menuHelper.show();
      break;
    }
  }

  private void EditArmada()
  {
    Intent ArmadaIntent = new Intent(Utama.this, Armada.class);
    startActivity(ArmadaIntent);
    finish();
  }

  private void EditDriver()
  {
    Intent DriverIntent = new Intent(Utama.this, Driver.class);
    startActivity(DriverIntent);
    finish();
  }

  private void EditProfile()
  {
    Intent ProfileIntent = new Intent(Utama.this, Profile.class);
    startActivity(ProfileIntent);
    finish();
  }
}
