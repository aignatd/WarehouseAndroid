package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Aug-17
 * HP/WA : 0857 7070 6 777
 */
public class WarehouseRsp
{
  @SerializedName("id")
  @Expose
  private Integer Id;
  @SerializedName("Kode")
  @Expose
  private String Kode;
  @SerializedName("Name")
  @Expose
  private String Name;
  @SerializedName("Adress")
  @Expose
  private String Adress;
  @SerializedName("City")
  @Expose
  private String City;
  @SerializedName("Phone1")
  @Expose
  private String Phone1;

  public Integer getId()
  {
    return Id;
  }

  public void setId(Integer id)
  {
    Id = id;
  }

  public String getKode()
  {
    return Kode;
  }

  public void setKode(String kode)
  {
    Kode = kode;
  }

  public String getName()
  {
    return Name;
  }

  public void setName(String name)
  {
    Name = name;
  }

  public String getAdress()
  {
    return Adress;
  }

  public void setAdress(String adress)
  {
    Adress = adress;
  }

  public String getCity()
  {
    return City;
  }

  public void setCity(String city)
  {
    City = city;
  }

  public String getPhone1()
  {
    return Phone1;
  }

  public void setPhone1(String phone1)
  {
    Phone1 = phone1;
  }
}
