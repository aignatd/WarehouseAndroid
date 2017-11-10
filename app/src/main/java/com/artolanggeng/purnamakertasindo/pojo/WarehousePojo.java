package com.artolanggeng.purnamakertasindo.pojo;

import com.artolanggeng.purnamakertasindo.model.CoreResponse;
import com.artolanggeng.purnamakertasindo.model.WarehouseRsp;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Jul-17
 * HP/WA : 0857 7070 6 777
 */
public class WarehousePojo
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
	@SerializedName("WarehouseRsp")
	@Expose
	private List<WarehouseRsp> warehouseRsps;

	public CoreResponse getCoreResponse()
	{
		return coreResponse;
	}

	public void setCoreResponse(CoreResponse coreResponse)
	{
		this.coreResponse = coreResponse;
	}

	public List<WarehouseRsp> getWarehouseRsps()
	{
		return warehouseRsps;
	}

	public void setWarehouseRsps(List<WarehouseRsp> warehouseRsps)
	{
		this.warehouseRsps = warehouseRsps;
	}
}
