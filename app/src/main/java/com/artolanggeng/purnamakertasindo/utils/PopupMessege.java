package com.artolanggeng.purnamakertasindo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import com.artolanggeng.purnamakertasindo.R;

/**
 * Created by aignatd on 6/25/16.
 */
public class PopupMessege extends Activity
{
	public void ShowMessege0(final Context context, final Class<?> cls)
	{
		Intent Pesan0 = new Intent(context, cls);
		context.startActivity(Pesan0);
		finish();
	}

  public void ShowMessege1(final Context context, String strMsg)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.titleMessege)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void ShowMessege2(final Context context, String strMsg, final Activity act, final Class<?> cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.titleMessege)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						act.finish();
					}
				})
				.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

  public void ShowMessege3(final Context context, String strMsg, final Activity cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.titleMessege)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						cls.finish();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

  public void ShowMessege4(final Context context, String strMsg, final Activity act, final Class<?> cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.titleMessege)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
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

	public void ShowMessege5(final Context context, String strMsg, final Class<?> cls)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
				.setTitle(R.string.titleMessege)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
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

	public void ShowMessege6(final Context context, String strMsg, final Activity act)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
			.setTitle(R.string.titleMessege)
			.setMessage(strMsg)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setCancelable(false)
			.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					act.finish();
				}
			})
			.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int id)
				{
					dialog.cancel();
				}
			});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
