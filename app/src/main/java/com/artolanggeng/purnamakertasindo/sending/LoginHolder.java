package com.artolanggeng.purnamakertasindo.sending;

import com.artolanggeng.purnamakertasindo.data.Device;
import com.artolanggeng.purnamakertasindo.data.User;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class LoginHolder
{
  private User DataUser;
  private Device DataDevice;

  public LoginHolder(User dataUser, Device dataDevice)
  {
    DataUser = dataUser;
    DataDevice = dataDevice;
  }
}
