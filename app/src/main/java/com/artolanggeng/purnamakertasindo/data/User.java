package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class User
{
  private Integer UserID;
  private String username;
  private String Phone;
  private String name;
  private String password;
  private String PasswordBaru;
  private String Token;

  public Integer getUserID()
  {
    return UserID;
  }

  public void setUserID(Integer userID)
  {
    UserID = userID;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPhone()
  {
    return Phone;
  }

  public void setPhone(String phone)
  {
    Phone = phone;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getPasswordBaru()
  {
    return PasswordBaru;
  }

  public void setPasswordBaru(String passwordBaru)
  {
    PasswordBaru = passwordBaru;
  }

  public String getToken()
  {
    return Token;
  }

  public void setToken(String token)
  {
    Token = token;
  }
}
