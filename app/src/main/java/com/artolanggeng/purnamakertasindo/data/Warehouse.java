package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Aug-17
 * HP/WA : 0857 7070 6 777
 */
public class Warehouse
{
  private Integer WarehouseID;
  private String Kode;
  private String Name;
  private String Adress;
  private String City;
  private String Phone1;

  public Integer getWarehouseID()
  {
    return WarehouseID;
  }

  public void setWarehouseID(Integer warehouseID)
  {
    WarehouseID = warehouseID;
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

	private static Warehouse WarehouseInstance = new Warehouse();

	public synchronized static Warehouse getInstance()
	{
		return WarehouseInstance;
	}

	private Warehouse()
	{
	}

	public static void initWarehouse()
	{
		WarehouseInstance = new Warehouse();
	}
}
