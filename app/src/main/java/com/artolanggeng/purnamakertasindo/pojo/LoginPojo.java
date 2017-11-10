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
public class LoginPojo
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("UserResponse")
  @Expose
  private UserResponse userResponse;
  @SerializedName("RoleResponse")
  @Expose
  private List<RoleResponse> roleResponse = null;
	@SerializedName("PotongRsp")
	@Expose
	private List<PotongRsp> potongRsp = null;
	@SerializedName("ProductRsp")
	@Expose
	private List<ProductRsp> productrsp = null;
	@SerializedName("PrinterRsp")
	@Expose
	private PrinterRsp printerRsp;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public UserResponse getUserResponse() {
    return userResponse;
  }

  public void setUserResponse(UserResponse userResponse) {
    this.userResponse = userResponse;
  }

	public List<RoleResponse> getRoleResponse()
	{
		return roleResponse;
	}

	public void setRoleResponse(List<RoleResponse> roleResponse)
	{
		this.roleResponse = roleResponse;
	}

	public List<PotongRsp> getPotongRsp()
	{
		return potongRsp;
	}

	public void setPotongRsp(List<PotongRsp> potongRsp)
	{
		this.potongRsp = potongRsp;
	}

	public List<ProductRsp> getProductrsp()
	{
		return productrsp;
	}

	public void setProductrsp(List<ProductRsp> productrsp)
	{
		this.productrsp = productrsp;
	}

	public PrinterRsp getPrinterRsp()
	{
		return printerRsp;
	}

	public void setPrinterRsp(PrinterRsp printerRsp)
	{
		this.printerRsp = printerRsp;
	}
}
