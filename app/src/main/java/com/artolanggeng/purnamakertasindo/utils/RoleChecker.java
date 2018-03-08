package com.artolanggeng.purnamakertasindo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.artolanggeng.purnamakertasindo.common.TimbangAdmin;
import com.artolanggeng.purnamakertasindo.common.TimbangAdminOpr;
import com.artolanggeng.purnamakertasindo.common.TimbangOpr;
import com.artolanggeng.purnamakertasindo.common.TimbangSuper;
import com.artolanggeng.purnamakertasindo.data.Role;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class RoleChecker
{
	static String TAG = "[RoleChecker]";
	private Activity activity;
	private Context context;
	private int ADMIN = 0;
	private int DIREKSI = 0;
	private int MANAGER = 0;
	private int SUPERVISOR = 0;
	private int KASIR = 0;
	private int OPERATOR = 0;
	private int CUSTOMER = 0;
	private int QC = 0;
	private int SUPERUSER = 0;
	private String strSource="";

	public RoleChecker(Activity activity, Context context)
	{
		this.activity = activity;
		this.context = context;

		for(int i=0; i<Role.getInstance().getRoleResponse().size(); i++)
		{
			if(i > 0)
				strSource += ", ";

			strSource += String.valueOf(Role.getInstance().getRoleResponse().get(i).getId());

			switch(Role.getInstance().getRoleResponse().get(i).getId())
			{
				case FixValue.ADMIN :
					this.ADMIN = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.DIREKSI :
					this.DIREKSI = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.MANAGER :
					this.MANAGER = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.SUPERVISOR :
					this.SUPERVISOR = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.KASIR :
					this.KASIR = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.OPERATOR :
					this.OPERATOR = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.CUSTOMER :
					this.CUSTOMER = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.QC :
					this.QC = Role.getInstance().getRoleResponse().get(i).getId();
				break;
				case FixValue.SUPERUSER :
					this.SUPERUSER = Role.getInstance().getRoleResponse().get(i).getId();
				break;
			}
		}
	}

	public Integer RoleTimbangan()
	{
		Integer intHasil=1;

		if(cekdata(strSource, String.valueOf(FixValue.SUPERUSER)))
		{
			Intent LoginIntent = new Intent(activity, TimbangSuper.class);
			context.startActivity(LoginIntent);
			activity.finish();
		}
		else
		{
			if(((ADMIN == FixValue.ADMIN) && (OPERATOR == FixValue.OPERATOR)) || ((ADMIN == FixValue.ADMIN) && (QC == FixValue.QC)))
			{
				Intent LoginIntent = new Intent(activity, TimbangAdminOpr.class);
				context.startActivity(LoginIntent);
				activity.finish();
			}
			else
			if((OPERATOR == FixValue.OPERATOR) || (QC == FixValue.QC) || ((QC == FixValue.QC) && (OPERATOR == FixValue.OPERATOR)))
			{
				Intent LoginIntent = new Intent(activity, TimbangOpr.class);
				context.startActivity(LoginIntent);
				activity.finish();
			}
			else
			if(ADMIN == FixValue.ADMIN)
			{
				Intent LoginIntent = new Intent(activity, TimbangAdmin.class);
				context.startActivity(LoginIntent);
				activity.finish();
			}
			else
				intHasil = 0;
		}

		return intHasil;
	}

	private boolean cekdata(String source, String data)
	{
		int intIndex = source.indexOf(data);

		if(intIndex == -1)
			return false;
		else
			return true;
	}
}