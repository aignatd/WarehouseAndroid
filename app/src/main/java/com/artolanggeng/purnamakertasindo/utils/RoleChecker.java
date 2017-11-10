package com.artolanggeng.purnamakertasindo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.Role;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class RoleChecker
{
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

	public RoleChecker(Activity activity, Context context)
	{
		this.activity = activity;
		this.context = context;

		for(int i=0; i<Role.getInstance().getRoleResponse().size(); i++)
		{
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

	public void RoleTimbangan()
	{
		LinearLayout llOpr = activity.findViewById(R.id.llOperator);
		LinearLayout llAdm = activity.findViewById(R.id.llAdmin);
		LinearLayout llSuper = activity.findViewById(R.id.llSuper);
		RelativeLayout rlDaftarDevice = activity.findViewById(R.id.rlDaftarDevice);

		llOpr.setVisibility(View.GONE);
		llAdm.setVisibility(View.GONE);
		llSuper.setVisibility(View.GONE);

		if(((ADMIN == FixValue.ADMIN) && (OPERATOR == FixValue.OPERATOR) && (SUPERUSER == FixValue.SUPERUSER)) ||
			(SUPERUSER == FixValue.SUPERUSER))
		{
			llOpr.setVisibility(View.VISIBLE);
			llAdm.setVisibility(View.VISIBLE);
			llSuper.setVisibility(View.VISIBLE);
		}
		else
		if((ADMIN == FixValue.ADMIN) && (OPERATOR == FixValue.OPERATOR))
		{
			llOpr.setVisibility(View.VISIBLE);
			llAdm.setVisibility(View.VISIBLE);
		}
		else
		if(ADMIN == FixValue.ADMIN)
			llAdm.setVisibility(View.VISIBLE);
		else
		if(OPERATOR == FixValue.OPERATOR)
			llOpr.setVisibility(View.VISIBLE);

		if(SUPERUSER != FixValue.SUPERUSER)
		{
			llSuper.setVisibility(View.VISIBLE);
			rlDaftarDevice.setVisibility(View.GONE);
		}
	}
}
