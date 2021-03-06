package com.artolanggeng.purnamakertasindo.utils;

/**
 * Created by ignat on 16-Jun-16.
 */
public class FixValue
{
	public static final int SPLASH_DISPLAY_LENGHT = 2500;
	public static final String strNamaPref = "com.artolanggeng.purnamakertasindo.pref";

	public static final String RestfulLogin = "users/login";
	public static final String RestfulLogout = "users/logout";
	public static final String RestfulPassword = "users/password";
	public static final String RestfulKartuBaru = "customers/kartubaru";
	public static final String RestfulRequest = "customers/request";
	public static final String RestfulFormulir = "formulirs/upload";
	public static final String RestfulTambahTimbang = "formulirs/tambah";
	public static final String RestfulPembayaran = "formulirs/bayar";
	public static final String Restfulsynchronize = "proses/synchronize";
	public static final String Restfultimbang = "proses/timbang";
	public static final String Restfulpotongan = "proses/potongan";
	public static final String Restfulupdateqc = "proses/updateqc";
	public static final String Restfulnetto = "proses/netto";
	public static final String RestfulHistory = "history/historyuser";
	public static final String RestfulDetailHistory = "history/historypemasok";
	public static final String Restfulproduct = "customers/product";
	public static final String RestfulWarehouse = "device/datawarehouse";
	public static final String RestfulDevice = "device/daftardevice";

	// Server Online
	//public static final String Hostname = "http://myserverartolanggengcom-over.cloud.revoluz.io:49650/api/v1/";

	// Server Offline
	public static final String Hostname = "http://192.168.1.109:41014/api/v1/";

	public static final int TimeoutConnection = 45000;
	public static final String txtTitlePesan = "Informasi";
	public static final String strBtnOK = "OK";
	public static final String strBtnBatal = "Batal";

	//Network Connectivity
	public static final int TYPE_NONE = 0;
	public static final int TYPE_WIFI = 1;
	public static final int TYPE_MOBILE = 2;

	public static final int intSuccess = 0;
	public static final int intError = -1;
	public static final int intEmpty = -2;

	public static final int ADMIN = 1;
	public static final int DIREKSI = 2;
	public static final int MANAGER = 3;
	public static final int SUPERVISOR = 4;
	public static final int KASIR = 5;
	public static final int OPERATOR = 6;
	public static final int CUSTOMER = 7;
	public static final int QC = 8;
	public static final int SUPERUSER = 9;

	public static final int TimbangKecil = 1;
	public static final int TimbangBesar = 2;
}
