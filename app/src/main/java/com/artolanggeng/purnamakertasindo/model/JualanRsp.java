package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class JualanRsp
{
	@SerializedName("mproductpk")
	@Expose
	private Integer mproductpk;
	@SerializedName("productcode")
	@Expose
	private String productcode;
	@SerializedName("productname")
	@Expose
	private String productname;

	public Integer getMproductpk()
	{
		return mproductpk;
	}

	public void setMproductpk(Integer mproductpk)
	{
		this.mproductpk=mproductpk;
	}

	public String getProductcode()
	{
		return productcode;
	}

	public void setProductcode(String productcode)
	{
		this.productcode=productcode;
	}

	public String getProductname()
	{
		return productname;
	}

	public void setProductname(String productname)
	{
		this.productname=productname;
	}
}
