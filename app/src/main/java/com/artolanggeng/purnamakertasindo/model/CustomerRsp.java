package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class CustomerRsp
{
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("pemasokid")
	@Expose
	private String pemasokid;
	@SerializedName("perusahaan")
	@Expose
	private String perusahaan;
	@SerializedName("panggilan")
	@Expose
	private String panggilan;
	@SerializedName("telpon")
	@Expose
	private String telpon;
	@SerializedName("Alamat")
	@Expose
	private String alamat;
	@SerializedName("nopolisi")
	@Expose
	private String nopolisi;
	@SerializedName("jumlahtimbang")
	@Expose
	private Integer jumlahtimbang;
	@SerializedName("permintaan")
	@Expose
	private Integer permintaan;
	@SerializedName("pekerjaan")
	@Expose
	private Integer pekerjaan;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("jenistimbang")
	@Expose
	private Integer jenistimbang;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getPemasokid()
	{
		return pemasokid;
	}

	public void setPemasokid(String pemasokid)
	{
		this.pemasokid = pemasokid;
	}

	public String getPerusahaan()
	{
		return perusahaan;
	}

	public void setPerusahaan(String perusahaan)
	{
		this.perusahaan = perusahaan;
	}

	public String getPanggilan()
	{
		return panggilan;
	}

	public void setPanggilan(String panggilan)
	{
		this.panggilan = panggilan;
	}

	public String getTelpon()
	{
		return telpon;
	}

	public void setTelpon(String telpon)
	{
		this.telpon = telpon;
	}

	public String getAlamat()
	{
		return alamat;
	}

	public void setAlamat(String alamat)
	{
		this.alamat = alamat;
	}

	public String getNopolisi()
	{
		return nopolisi;
	}

	public void setNopolisi(String nopolisi)
	{
		this.nopolisi = nopolisi;
	}

	public Integer getJumlahtimbang()
	{
		return jumlahtimbang;
	}

	public void setJumlahtimbang(Integer jumlahtimbang)
	{
		this.jumlahtimbang = jumlahtimbang;
	}

	public Integer getPermintaan()
	{
		return permintaan;
	}

	public void setPermintaan(Integer permintaan)
	{
		this.permintaan = permintaan;
	}

	public Integer getPekerjaan()
	{
		return pekerjaan;
	}

	public void setPekerjaan(Integer pekerjaan)
	{
		this.pekerjaan = pekerjaan;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Integer getJenistimbang()
	{
		return jenistimbang;
	}

	public void setJenistimbang(Integer jenistimbang)
	{
		this.jenistimbang = jenistimbang;
	}
}
