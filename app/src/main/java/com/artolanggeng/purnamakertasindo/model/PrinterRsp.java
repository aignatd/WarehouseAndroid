package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class PrinterRsp
{
  @SerializedName("pekerjaanid")
  @Expose
  private Integer pekerjaanid;
  @SerializedName("pemasokid")
  @Expose
  private String pemasokid;

	public Integer getPekerjaanid()
	{
		return pekerjaanid;
	}

	public void setPekerjaanid(Integer pekerjaanid)
	{
		this.pekerjaanid = pekerjaanid;
	}

	public String getPemasokid()
	{
		return pemasokid;
	}

	public void setPemasokid(String pemasokid)
	{
		this.pemasokid = pemasokid;
	}
}
