package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 14-Jan-17
 * HP/WA : 0857 7070 6 777
 */
public class Device
{
  private String DeviceID;
  private String NamaDevice;
  private String TipeDevice;
  private String OSDevice;
  private Integer businessunit;

  public String getDeviceID()
  {
    return DeviceID;
  }

  public void setDeviceID(String deviceID)
  {
    DeviceID = deviceID;
  }

  public String getNamaDevice()
  {
    return NamaDevice;
  }

  public void setNamaDevice(String namaDevice)
  {
    NamaDevice = namaDevice;
  }

  public String getTipeDevice()
  {
    return TipeDevice;
  }

  public void setTipeDevice(String tipeDevice)
  {
    TipeDevice = tipeDevice;
  }

  public String getOSDevice()
  {
    return OSDevice;
  }

  public void setOSDevice(String OSDevice)
  {
    this.OSDevice = OSDevice;
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
