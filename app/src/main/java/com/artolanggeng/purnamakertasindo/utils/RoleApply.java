package com.artolanggeng.purnamakertasindo.utils;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.model.RoleResponse;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class RoleApply
{
	private static int ADMIN = 0;
	private static int DIREKSI = 0;
	private static int MANAGER = 0;
	private static int SUPERVISOR = 0;
	private static int KASIR = 0;
	private static int OPERATOR = 0;
	private static int CUSTOMER = 0;
	private static int QC = 0;
	private static int SUPERUSER = 0;

	public RoleApply(List<RoleResponse> roleRsp)
	{
		List<RoleResponse> roleRsp1 = roleRsp;

		for(int i = 0; i< roleRsp1.size(); i++)
		{
			switch(roleRsp1.get(i).getId())
			{
				case FixValue.ADMIN:
					this.ADMIN = roleRsp1.get(i).getId();
				break;
				case FixValue.DIREKSI:
					this.DIREKSI = roleRsp1.get(i).getId();
				break;
				case FixValue.MANAGER:
					this.MANAGER = roleRsp1.get(i).getId();
				break;
				case FixValue.SUPERVISOR:
					this.SUPERVISOR = roleRsp1.get(i).getId();
				break;
				case FixValue.KASIR:
					this.KASIR = roleRsp1.get(i).getId();
				break;
				case FixValue.OPERATOR:
					this.OPERATOR = roleRsp1.get(i).getId();
				break;
				case FixValue.CUSTOMER:
					this.CUSTOMER = roleRsp1.get(i).getId();
				break;
				case FixValue.QC:
					this.QC = roleRsp1.get(i).getId();
				break;
				case FixValue.SUPERUSER:
					this.SUPERUSER = roleRsp1.get(i).getId();
				break;
			}
		}
	}

	public static final int RoleTimbangan()
	{
		int intLay = R.layout.lay_timbang_opr;

		if((ADMIN == FixValue.ADMIN) && (OPERATOR == FixValue.OPERATOR))
			intLay = R.layout.lay_timbang_admin;
		else
		if(OPERATOR == FixValue.OPERATOR)
			intLay = R.layout.lay_timbang_opr;
		else
		if(SUPERUSER == FixValue.SUPERUSER)
			intLay = R.layout.lay_timbang_super;

		return intLay;
	}
}
