package com.artolanggeng.purnamakertasindo.pojo;

import com.artolanggeng.purnamakertasindo.model.CoreResponse;
import com.artolanggeng.purnamakertasindo.model.CustomerRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class ProsesPojo
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("CustomerRsp")
  @Expose
  private List<CustomerRsp> customerRsps;
	@SerializedName("TimbanganRsp")
	@Expose
	private List<TimbangRsp> TimbanganRsp;

	public CoreResponse getCoreResponse()
	{
		return coreResponse;
	}

	public void setCoreResponse(CoreResponse coreResponse)
	{
		this.coreResponse = coreResponse;
	}

	public List<CustomerRsp> getCustomerRsps()
	{
		return customerRsps;
	}

	public void setCustomerRsps(List<CustomerRsp> customerRsps)
	{
		this.customerRsps = customerRsps;
	}

	public List<TimbangRsp> getTimbanganRsp()
	{
		return TimbanganRsp;
	}

	public void setTimbanganRsp(List<TimbangRsp> timbanganRsp)
	{
		TimbanganRsp = timbanganRsp;
	}
}
