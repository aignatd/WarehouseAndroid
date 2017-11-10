package com.artolanggeng.purnamakertasindo.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class User
{
  private Integer UserID;
  private String Username;
  private String Phone;
  private String Name;
  private String Password;
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
    return Username;
  }

  public void setUsername(String username)
  {
    Username = username;
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
    return Name;
  }

  public void setName(String name)
  {
    Name = name;
  }

  public String getPassword()
  {
    return Password;
  }

  public void setPassword(String password)
  {
    Password = password;
  }

  public String getToken()
  {
    return Token;
  }

  public void setToken(String token)
  {
    Token = token;
  }

  public String getPasswordBaru()
  {
    return PasswordBaru;
  }

  public void setPasswordBaru(String passwordBaru)
  {
    PasswordBaru = passwordBaru;
  }
}
