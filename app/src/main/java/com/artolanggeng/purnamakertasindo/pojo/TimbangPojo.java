package com.artolanggeng.purnamakertasindo.pojo;

import com.artolanggeng.purnamakertasindo.data.DataTimbangan;
import com.artolanggeng.purnamakertasindo.model.CoreResponse;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class TimbangPojo
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
	@SerializedName("TimbanganRsp")
	@Expose
	private TimbangRsp TimbanganRsp;
	@SerializedName("SetTimbangRsp")
	@Expose
	private List<DataTimbangan> SetTimbangRsp = null;

	public CoreResponse getCoreResponse()
	{
		return coreResponse;
	}

	public void setCoreResponse(CoreResponse coreResponse)
	{
		this.coreResponse = coreResponse;
	}

	public TimbangRsp getTimbanganRsp()
	{
		return TimbanganRsp;
	}

	public void setTimbanganRsp(TimbangRsp timbanganRsp)
	{
		TimbanganRsp = timbanganRsp;
	}

	public List<DataTimbangan> getSetTimbangRsp()
	{
		return SetTimbangRsp;
	}

	public void setSetTimbangRsp(List<DataTimbangan> setTimbangRsp)
	{
		SetTimbangRsp=setTimbangRsp;
	}
}
