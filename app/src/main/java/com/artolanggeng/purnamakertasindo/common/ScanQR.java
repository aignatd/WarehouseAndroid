package com.artolanggeng.purnamakertasindo.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.timbanganKecil.formKecil;
import com.artolanggeng.purnamakertasindo.timbangbesar.FormBesar;
import com.artolanggeng.purnamakertasindo.utils.CameraManager;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.warehouse.MainProses;
import net.sourceforge.zbar.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ScanQR extends AppCompatActivity implements SurfaceHolder.Callback
{
  @BindView(R.id.txt_flash)
  TextView txt_flash;
  @BindView(R.id.cameraPreview)
  SurfaceView cameraPreview;

  private String TAG = "[Scan QR]";
  private CameraManager cameraManager;
  private Context context = this;
  private String kodeTimbangan;
  PopupMessege pesan = new PopupMessege();
  private ImageScanner mScanner;
  private boolean hasSurface;
  private boolean flashOn = false;
  private boolean isInitCameraProcess = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_scanqrkartu);
    ButterKnife.bind(this);
    Bundle extras = getIntent().getExtras();
    kodeTimbangan = extras.getString("KodeTimbangan");
    cameraManager = new CameraManager(this, barcodeCallback);
    hasSurface = false;
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder)
  {
    if(!hasSurface) hasSurface = true;
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
  {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder)
  {
    hasSurface = false;
  }

  @OnClick({R.id.ivBackQR, R.id.btn_flash})
  public void onViewClicked(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackQR:
        BackActivity();
        break;
      case R.id.btn_flash:
        if(hasFlash())
          switchFlashlight();
        break;
    }
  }

  private Camera.PreviewCallback barcodeCallback = new Camera.PreviewCallback()
  {
    public void onPreviewFrame(byte[] data, Camera camera)
    {
      Camera.Parameters parameters = camera.getParameters();
      Camera.Size size = parameters.getPreviewSize();

      Image barcode = new Image(size.width, size.height, "Y800");
      barcode.setData(data);

      if(mScanner == null)
      {
        return;
      }

      int result = mScanner.scanImage(barcode);

      if(result != 0)
      {
        onPause();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);

        String barcodeText = "";
        SymbolSet symbols = mScanner.getResults();
        for(Symbol sym : symbols)
        {
          barcodeText = sym.getData().trim();
        }

	      Intent ScanQRIntent = null;

        if (kodeTimbangan.equals("1"))
          ScanQRIntent = new Intent(ScanQR.this, formKecil.class);
        else
        if (kodeTimbangan.equals("2") || kodeTimbangan.equals("3") || kodeTimbangan.equals("4"))
        {
	        ScanQRIntent = new Intent(ScanQR.this, FormBesar.class);

	        if(kodeTimbangan.equals("2"))
	          ScanQRIntent.putExtra("Jual", "");
	        else
	        if(kodeTimbangan.equals("3"))
		        ScanQRIntent.putExtra("Jual", "Jual");
          else
          if(kodeTimbangan.equals("4"))
          {
            ScanQRIntent.putExtra("Jual", "");
            ScanQRIntent.putExtra("Koreksi", "Koreksi");
          }
        }

	      ScanQRIntent.putExtra("KodePemasok", barcodeText);
	      ScanQRIntent.putExtra("Timbang", 0);
        ScanQRIntent.putExtra("History", "");
	      startActivity(ScanQRIntent);
	      finish();
      }
    }
  };

  private boolean hasFlash()
  {
    return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }

  private void switchFlashlight()
  {
    if(!flashOn)
    {
      cameraManager.setTorch(true);
      txt_flash.setText(getString(R.string.strFlashOn));
    }
    else
    {
      cameraManager.setTorch(false);
      txt_flash.setText(getString(R.string.strFlashOff));
    }

    flashOn = !flashOn;
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    isInitCameraProcess = true;
    Timer timer = new Timer();

    timer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        runOnUiThread(new Runnable()
        {
          @Override
          public void run()
          {
            SurfaceHolder surfaceHolder = cameraPreview.getHolder();
            if(hasSurface)
            {
              // The activity was paused but not stopped, so the surface still exists.
              // Therefore surfaceCreated() won't be called, so init the camera here.
              initCamera(cameraPreview, true);
            }
            else
            {
              // Install the callback and wait for surfaceCreated() to init the camera.
              surfaceHolder.addCallback(ScanQR.this);
              surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
              initCamera(cameraPreview, true);
            }
          }
        });
      }
    }, 500);
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    try
    {
      cameraManager.stopPreview();
      cameraManager.closeDriver();

      if(!hasSurface)
      {
        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.removeCallback(this);
      }
    }
    catch(RuntimeException e)
    {
      // Can be already released
    }
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    cleanCameraInstance();
  }

  private void cleanCameraInstance()
  {
    if(cameraManager.isOpen())
    {
      cameraManager.stopPreview();
      cameraManager.closeDriver();
      if(!hasSurface)
      {
        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.removeCallback(this);
      }
    }
  }

  private void initCamera(SurfaceView surfaceView, boolean oneTime)
  {
    if(surfaceView == null || surfaceView.getHolder() == null)
      throw new IllegalStateException("No SurfaceHolder provided");

    if(cameraManager.isOpen()) return;

    try
    {
      surfaceView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

      cameraManager.openDriver(surfaceView);
      // Creating the handler starts the preview, which can also throw a
      // RuntimeException.
      cameraManager.startPreview();
    }
    catch(IOException ioe)
    {
      ioe.printStackTrace();
    }
    catch(RuntimeException e)
    {
      if(null != cameraManager) cameraManager.closeDriver();

      if(oneTime) initCamera(surfaceView, false);
      else return;
    }

    mScanner = new ImageScanner();
    mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
    mScanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);
    mScanner.setConfig(Symbol.NONE, Config.X_DENSITY, 3);
    mScanner.setConfig(Symbol.NONE, Config.Y_DENSITY, 3);

    surfaceView.setBackgroundColor(Color.TRANSPARENT);
    isInitCameraProcess = false;
  }

  private void BackActivity()
  {
    Intent MainProsesIntent = new Intent(ScanQR.this, MainProses.class);
    startActivity(MainProsesIntent);
    finish();
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }
}
