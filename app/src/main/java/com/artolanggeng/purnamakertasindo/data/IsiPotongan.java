package com.artolanggeng.purnamakertasindo.data;

import com.artolanggeng.purnamakertasindo.model.PotongRsp;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class IsiPotongan
{
	private List<PotongRsp> mPotongRsp;

	public List<PotongRsp> getmPotongRsp()
	{
		return mPotongRsp;
	}

	public void setmPotongRsp(List<PotongRsp> mPotongRsp)
	{
		this.mPotongRsp = mPotongRsp;
	}

	private static IsiPotongan IsiPotonganInstance = new IsiPotongan();

	public synchronized static IsiPotongan getInstance()
	{
		return IsiPotonganInstance;
	}

	private IsiPotongan()
	{
	}

	public static void initIsiPotongan()
	{
		IsiPotonganInstance = new IsiPotongan();
	}
}
