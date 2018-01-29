package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class ProductRsp
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
	@SerializedName("harga")
	@Expose
	private Integer harga;
	@SerializedName("unitpriceid")
	@Expose
	private Integer unitpriceid;

	public String getProductcode()
	{
		return productcode;
	}

	public void setProductcode(String productcode)
	{
		this.productcode = productcode;
	}

	public Integer getMproductpk()
	{
		return mproductpk;
	}

	public void setMproductpk(Integer mproductpk)
	{
		this.mproductpk = mproductpk;
	}

	public String getProductname()
	{
		return productname;
	}

	public void setProductname(String productname)
	{
		this.productname = productname;
	}

	public Integer getHarga()
	{
		return harga;
	}

	public void setHarga(Integer harga)
	{
		this.harga = harga;
	}

	public Integer getUnitpriceid()
	{
		return unitpriceid;
	}

	public void setUnitpriceid(Integer unitpriceid)
	{
		this.unitpriceid=unitpriceid;
	}
}
