package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 14-Jan-17
 * HP/WA : 0857 7070 6 777
 */
public class Device
{
  private String deviceid;
  private String device;
  private String tipe;
  private String os;
  private Integer businessunit;

	public String getDeviceid()
	{
		return deviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	public String getTipe()
	{
		return tipe;
	}

	public void setTipe(String tipe)
	{
		this.tipe = tipe;
	}

	public String getOs()
	{
		return os;
	}

	public void setOs(String os)
	{
		this.os = os;
	}

	public Integer getBusinessunit()
	{
		return businessunit;
	}

	public void setBusinessunit(Integer businessunit)
	{
		this.businessunit = businessunit;
	}
}
