package com.artolanggeng.purnamakertasindo.utils;

/**
 * Created by ignat on 16-Jun-16.
 */
public class FixValue
{
	public static final int SPLASH_DISPLAY_LENGHT = 2500;
	public static final String strNamaPref = "com.artolanggeng.purnamakertasindo.pref";
	public static final String FolderDoc = "/Warehouse";
	public static final String RestfulLogin = "users/login";
	public static final String RestfulLogout = "users/logout";
	public static final String RestfulPassword = "users/password";
	public static final String RestfulDaftarUser = "users/daftaruser";
	public static final String RestfulKartuBaru = "customers/kartubaru";
	public static final String RestfulRequest = "customers/request";
	public static final String RestfulFormulir = "formulirs/uploadbesar";
	public static final String RestfulFormulirKecil = "formulirs/uploadkecil";
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
	public static final String RestfulPhotoBarang = "formulirs/photobarang";
	public static final String RestfulAutoTimbang = "timbangan";
	public static final String RestfulAmbilProfile = "users/ambilprofile";
	public static final String RestfulUpdateProfile = "users/updateprofile";
	public static final String RestfulDataTimbangan = "formulirs/datatimbang/{warehouse}";

	// Server Online
	public static final String Hostname = "http://www.purnamakertasindo.com:41014/api/v1/";
	//public static final String Hostname = "http://artolanggeng.dyndns-server.com:21012/api/v1/";
	//public static final String Hostname = "http://111.94.20.226:41014/api/v1/";

	// Server Offline
	//public static final String Hostname = "http://192.168.212.18:41014/api/v1/";
	//public static final String Hostname = "http://192.168.43.124:41014/api/v1/";
	//public static final String Hostname = "http://192.168.137.1:41014/api/v1/";

	// Server Timbangan
	public static final String Hosttimbangan = "http://192.168.1.13:42024/api/v1/";

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
	public static final int TimbangJual = 3;
}
