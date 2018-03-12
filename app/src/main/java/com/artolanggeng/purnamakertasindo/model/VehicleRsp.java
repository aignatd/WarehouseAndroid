package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class VehicleRsp
{
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("nopolisi")
	@Expose
	private String nopolisi;
	@SerializedName("status")
	@Expose
	private String status;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNopolisi()
	{
		return nopolisi;
	}

	public void setNopolisi(String nopolisi)
	{
		this.nopolisi = nopolisi;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status=status;
	}

	private static VehicleRsp VehicleRspInstance = new VehicleRsp();

	public synchronized static VehicleRsp getInstance()
	{
		return VehicleRspInstance;
	}

	private VehicleRsp()
	{
	}

	public static void initVehicleRsp()
	{
		VehicleRspInstance = new VehicleRsp();
	}
}
