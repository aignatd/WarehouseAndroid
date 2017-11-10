package com.artolanggeng.purnamakertasindo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by aignatd on 6/25/16.
 */
public class PesanPopup extends Activity
{
  public void TampilPesan1(final Context context, String strMsg)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(FixValue.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void TampilPesan2(final Context context, final Class<?> cls)
  {
		Intent Pesan0 = new Intent(context, cls);
		context.startActivity(Pesan0);
		finish();
  }

  public void TampilPesan3(final Context context, String strMsg, final Activity cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(FixValue.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(FixValue.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						cls.finish();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

  public void TampilPesan4(final Context context, String strMsg, final Activity act, final Class<?> cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(FixValue.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(FixValue.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						act.finish();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

	public void TampilPesan5(final Context context, String strMsg, final Activity act, final Class<?> cls)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
				.setTitle(FixValue.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(FixValue.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						act.finish();
					}
				})
				.setNegativeButton(FixValue.strBtnBatal, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void TampilPesan6(final Context context, String strMsg, final Class<?> cls)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
				.setTitle(FixValue.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(FixValue.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						finish();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
