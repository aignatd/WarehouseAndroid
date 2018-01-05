package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class Profile
{
  private String name;
  private String alamat;
  private String phone;
  private String email;
  private String tmplahir;
  private String tgllahir;
  private String seks;
  private String Token;
	private Integer idxseks;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name=name;
  }

  public String getAlamat()
  {
    return alamat;
  }

  public void setAlamat(String alamat)
  {
    this.alamat=alamat;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone=phone;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email=email;
  }

  public String getTmplahir()
  {
    return tmplahir;
  }

  public void setTmplahir(String tmplahir)
  {
    this.tmplahir=tmplahir;
  }

  public String getTgllahir()
  {
    return tgllahir;
  }

  public void setTgllahir(String tgllahir)
  {
    this.tgllahir=tgllahir;
  }

  public String getSeks()
  {
    return seks;
  }

  public void setSeks(String seks)
  {
    this.seks=seks;
  }

  public String getToken()
  {
    return Token;
  }

  public void setToken(String token)
  {
    Token=token;
  }

	public Integer getIdxseks()
	{
		return idxseks;
	}

	public void setIdxseks(Integer idxseks)
	{
		this.idxseks=idxseks;
	}
}
