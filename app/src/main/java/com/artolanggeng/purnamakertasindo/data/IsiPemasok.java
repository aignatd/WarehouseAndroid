package com.artolanggeng.purnamakertasindo.data;

import com.artolanggeng.purnamakertasindo.model.CustomerRsp;

/**
 * Dibuat oleh : ignat
 * Tanggal : 01-Oct-17
 * HP/WA : 0857 7070 6 777
 */
public class IsiPemasok
{
	private CustomerRsp customerRsp;

	public CustomerRsp getCustomerRsp()
	{
		return customerRsp;
	}

	public void setCustomerRsp(CustomerRsp customerRsp)
	{
		this.customerRsp = customerRsp;
	}

	private static IsiPemasok IsiPemasokInstance = new IsiPemasok();

	public synchronized static IsiPemasok getInstance()
	{
		return IsiPemasokInstance;
	}

	private IsiPemasok()
	{
	}

	public static void initIsiPemasok()
	{
		IsiPemasokInstance = new IsiPemasok();
	}
}
