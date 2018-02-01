package com.artolanggeng.purnamakertasindo.data;

import com.artolanggeng.purnamakertasindo.model.ProductRsp;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class IsiProduct
{
	private List<ProductRsp> mProductRsps;
	private List<ProductRsp> mJualanRsps;

	public List<ProductRsp> getmProductRsps()
	{
		return mProductRsps;
	}

	public void setmProductRsps(List<ProductRsp> mProductRsps)
	{
		this.mProductRsps = mProductRsps;
	}

	public List<ProductRsp> getmJualanRsps()
	{
		return mJualanRsps;
	}

	public void setmJualanRsps(List<ProductRsp> mJualanRsps)
	{
		this.mJualanRsps=mJualanRsps;
	}

	private static IsiProduct IsiProductInstance = new IsiProduct();

	public synchronized static IsiProduct getInstance()
	{
		return IsiProductInstance;
	}

	private IsiProduct()
	{
	}

	public static void initIsiProduct()
	{
		IsiProductInstance = new IsiProduct();
	}
}
