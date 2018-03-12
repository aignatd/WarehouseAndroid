package com.artolanggeng.purnamakertasindo.data;

import com.artolanggeng.purnamakertasindo.model.RoleResponse;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class Proses
{
  private Integer Permintaan;
  private Integer Pekerjaan;
  private String BisnisUnit;
  private String TglRequest;
	private Integer userid;
	private Integer jenistimbang;
	private List<RoleResponse> roleuser;

  public Integer getPermintaan()
  {
    return Permintaan;
  }

  public void setPermintaan(Integer permintaan)
  {
    Permintaan = permintaan;
  }

  public Integer getPekerjaan()
  {
    return Pekerjaan;
  }

  public void setPekerjaan(Integer pekerjaan)
  {
    Pekerjaan = pekerjaan;
  }

	public String getBisnisUnit()
	{
		return BisnisUnit;
	}

	public void setBisnisUnit(String bisnisUnit)
	{
		BisnisUnit = bisnisUnit;
	}

	public String getTglRequest()
	{
		return TglRequest;
	}

	public void setTglRequest(String tglRequest)
	{
		TglRequest = tglRequest;
	}

	public Integer getUserid()
	{
		return userid;
	}

	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}

	public Integer getJenistimbang()
	{
		return jenistimbang;
	}

	public void setJenistimbang(Integer jenistimbang)
	{
		this.jenistimbang = jenistimbang;
	}

	public List<RoleResponse> getRoleuser()
	{
		return roleuser;
	}

	public void setRoleuser(List<RoleResponse> roleuser)
	{
		this.roleuser=roleuser;
	}
}
