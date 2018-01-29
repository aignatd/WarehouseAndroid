package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class TimbangRsp
{
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("nourut")
	@Expose
	private Integer nourut;
	@SerializedName("tonasebruto")
	@Expose
	private Integer tonasebruto;
	@SerializedName("tanggal")
	@Expose
	private String tanggal;
	@SerializedName("pekerjaanid")
	@Expose
	private Integer pekerjaanid;
	@SerializedName("productcode")
	@Expose
	private String productcode;
	@SerializedName("harga")
	@Expose
	private Integer harga;
	@SerializedName("potongan")
	@Expose
	private Integer potongan;
	@SerializedName("tonasenetto")
	@Expose
	private Integer tonasenetto;
	@SerializedName("jenispotongid")
	@Expose
	private Integer jenispotongid;
	@SerializedName("proses")
	@Expose
	private String proses;
	@SerializedName("display")
	@Expose
	private String display;
	@SerializedName("Timbangan")
	@Expose
	private String Timbangan;
	@SerializedName("unitpriceid")
	@Expose
	private Integer unitpriceid;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getNourut()
	{
		return nourut;
	}

	public void setNourut(Integer nourut)
	{
		this.nourut = nourut;
	}

	public Integer getTonasebruto()
	{
		return tonasebruto;
	}

	public void setTonasebruto(Integer tonasebruto)
	{
		this.tonasebruto = tonasebruto;
	}

	public String getTanggal()
	{
		return tanggal;
	}

	public void setTanggal(String tanggal)
	{
		this.tanggal = tanggal;
	}

	public Integer getPekerjaanid()
	{
		return pekerjaanid;
	}

	public void setPekerjaanid(Integer pekerjaanid)
	{
		this.pekerjaanid = pekerjaanid;
	}

	public String getProductcode()
	{
		return productcode;
	}

	public void setProductcode(String productcode)
	{
		this.productcode = productcode;
	}

	public Integer getHarga()
	{
		return harga;
	}

	public void setHarga(Integer harga)
	{
		this.harga = harga;
	}

	public Integer getPotongan()
	{
		return potongan;
	}

	public void setPotongan(Integer potongan)
	{
		this.potongan = potongan;
	}

	public Integer getTonasenetto()
	{
		return tonasenetto;
	}

	public void setTonasenetto(Integer tonasenetto)
	{
		this.tonasenetto = tonasenetto;
	}

	public Integer getJenispotongid()
	{
		return jenispotongid;
	}

	public void setJenispotongid(Integer jenispotongid)
	{
		this.jenispotongid = jenispotongid;
	}

	public String getProses()
	{
		return proses;
	}

	public void setProses(String proses)
	{
		this.proses = proses;
	}

	public String getDisplay()
	{
		return display;
	}

	public void setDisplay(String display)
	{
		this.display = display;
	}

	public String getTimbangan()
	{
		return Timbangan;
	}

	public void setTimbangan(String timbangan)
	{
		Timbangan = timbangan;
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
