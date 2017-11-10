package com.artolanggeng.purnamakertasindo.pojo;

import com.artolanggeng.purnamakertasindo.model.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class CustomerPojo
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("CustomerRsp")
  @Expose
  private CustomerRsp customerrsp;
  @SerializedName("VehicleRsp")
  @Expose
  private List<VehicleRsp> vehiclersp = null;
	@SerializedName("TimbangRsp")
	@Expose
	private List<TimbangRsp> timbangrsp = null;

	public CoreResponse getCoreResponse()
	{
		return coreResponse;
	}

	public void setCoreResponse(CoreResponse coreResponse)
	{
		this.coreResponse = coreResponse;
	}

	public CustomerRsp getCustomerrsp()
	{
		return customerrsp;
	}

	public void setCustomerrsp(CustomerRsp customerrsp)
	{
		this.customerrsp = customerrsp;
	}

	public List<VehicleRsp> getVehiclersp()
	{
		return vehiclersp;
	}

	public void setVehiclersp(List<VehicleRsp> vehiclersp)
	{
		this.vehiclersp = vehiclersp;
	}

	public List<TimbangRsp> getTimbangrsp()
	{
		return timbangrsp;
	}

	public void setTimbangrsp(List<TimbangRsp> timbangrsp)
	{
		this.timbangrsp = timbangrsp;
	}
}
