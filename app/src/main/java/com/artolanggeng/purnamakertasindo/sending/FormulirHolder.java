package com.artolanggeng.purnamakertasindo.sending;

import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class FormulirHolder
{
  private IsiFormulir DataFormulir;
  private TimbangRsp DataTimbangan;

  public FormulirHolder(IsiFormulir dataFormulir, TimbangRsp dataTimbangan)
  {
    DataFormulir = dataFormulir;
    DataTimbangan = dataTimbangan;
  }
}
