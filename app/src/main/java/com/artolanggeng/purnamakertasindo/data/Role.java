package com.artolanggeng.purnamakertasindo.data;

import com.artolanggeng.purnamakertasindo.model.RoleResponse;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 10-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class Role
{
	private List<RoleResponse> roleResponse = null;

	public List<RoleResponse> getRoleResponse()
	{
		return roleResponse;
	}

	public void setRoleResponse(List<RoleResponse> roleResponse)
	{
		this.roleResponse = roleResponse;
	}

	private static Role RoleInstance = new Role();

	public synchronized static Role getInstance()
	{
		return RoleInstance;
	}

	private Role()
	{
	}

	public static void initRole()
	{
		RoleInstance = new Role();
	}
}
