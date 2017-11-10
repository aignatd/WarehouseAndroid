package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class CoreResponse
{
  @SerializedName("Kode")
  @Expose
  private Integer kode;
  @SerializedName("Pesan")
  @Expose
  private String pesan;

  public Integer getKode() {
    return kode;
  }

  public void setKode(Integer kode) {
    this.kode = kode;
  }

  public String getPesan() {
    return pesan;
  }

  public void setPesan(String pesan) {
    this.pesan = pesan;
  }}
