package com.artolanggeng.purnamakertasindo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Heru Permana on 11/9/2017.
 */

public class TimbanganRspKecil {

    @SerializedName("nourut")
    @Expose
    private Integer nourut;
    @SerializedName("tonasebruto")
    @Expose
    private Integer tonasebruto;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("productcode")
    @Expose
    private String productcode;
    @SerializedName("potongan")
    @Expose
    private Integer potongan;
    @SerializedName("tonasenetto")
    @Expose
    private Integer tonasenetto;
    @SerializedName("jenispotongid")
    @Expose
    private Integer jenispotongid;
    @SerializedName("codeproduct")
    @Expose
    private Integer codeproduct;
    @SerializedName("unitpriceid")
    @Expose
    private Integer unitpriceid;

    public Integer getNourut() {
        return nourut;
    }

    public void setNourut(Integer nourut) {
        this.nourut = nourut;
    }

    public Integer getTonasebruto() {
        return tonasebruto;
    }

    public void setTonasebruto(Integer tonasebruto) {
        this.tonasebruto = tonasebruto;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Integer getPotongan() {
        return potongan;
    }

    public void setPotongan(Integer potongan) {
        this.potongan = potongan;
    }

    public Integer getTonasenetto() {
        return tonasenetto;
    }

    public void setTonasenetto(Integer tonasenetto) {
        this.tonasenetto = tonasenetto;
    }

    public Integer getJenispotongid() {
        return jenispotongid;
    }

    public void setJenispotongid(Integer jenispotongid) {
        this.jenispotongid = jenispotongid;
    }

    public Integer getCodeproduct()
    {
        return codeproduct;
    }

    public void setCodeproduct(Integer codeproduct)
    {
        this.codeproduct=codeproduct;
    }

    public Integer getUnitpriceid()
    {
        return unitpriceid;
    }

    public void setUnitpriceid(Integer unitpriceid)
    {
        this.unitpriceid=unitpriceid;
    }
}
