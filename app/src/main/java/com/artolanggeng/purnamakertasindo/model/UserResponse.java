package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class UserResponse
{
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("warehouse")
  @Expose
  private String WareHouse;
  @SerializedName("kodewarehouse")
  @Expose
  private String kodewarehouse;
  @SerializedName("remember_token")
  @Expose
  private String rememberToken;
  @SerializedName("UserID")
  @Expose
  private Integer UserID;

  public String getRememberToken()
  {
    return rememberToken;
  }

  public void setRememberToken(String rememberToken)
  {
    this.rememberToken = rememberToken;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getWareHouse()
  {
    return WareHouse;
  }

  public void setWareHouse(String wareHouse)
  {
    WareHouse = wareHouse;
  }

  public String getKodewarehouse()
  {
    return kodewarehouse;
  }

  public void setKodewarehouse(String kodewarehouse)
  {
    this.kodewarehouse = kodewarehouse;
  }

  public Integer getUserID()
  {
    return UserID;
  }

  public void setUserID(Integer userID)
  {
    UserID = userID;
  }
}
