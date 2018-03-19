package com.artolanggeng.purnamakertasindo.sending;

import com.artolanggeng.purnamakertasindo.data.Koreksi;
import com.artolanggeng.purnamakertasindo.data.Proses;
import com.artolanggeng.purnamakertasindo.data.Timbang;
import com.artolanggeng.purnamakertasindo.model.TimbanganRspKecil;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class ProsesHolder
{
  private Proses DataProses;
  private List<Koreksi> DataKoreksi;

  public ProsesHolder(Proses dataProses, List<Koreksi> dataKoreksi)
  {
    DataProses=dataProses;
    DataKoreksi=dataKoreksi;
  }
}
