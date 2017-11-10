package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 16-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class Timbang
{
	private Integer id;
	private Integer nourut;
	private Integer tonasebruto;
	private String tanggal;
	private Integer pekerjaanid;
	private String productcode;
	private Integer potongan;
	private Integer tonasenetto;
	private String jenispotongid;

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

	public String getJenispotongid()
	{
		return jenispotongid;
	}

	public void setJenispotongid(String jenispotongid)
	{
		this.jenispotongid = jenispotongid;
	}

	private static Timbang TimbangInstance = new Timbang();
	public synchronized static Timbang getInstance()
	{
		return TimbangInstance;
	}

	private Timbang()
	{
	}

	public static void initTimbang()
	{
		TimbangInstance = new Timbang();
	}
}
