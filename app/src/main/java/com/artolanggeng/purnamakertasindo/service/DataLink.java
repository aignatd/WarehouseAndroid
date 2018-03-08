/*
 * Copyright (c) 2016 oleh Agustinus Ignat Deswanto
 *
 *  Dilarang menyalah gunakan aplikasi ini terutama untuk tindak kejahatan.
 *  Jika ada pertanyaan seputar aplikasi ini silakan menghubungi :
 *
 *  Agustinus Ignat Deswanto
 *  Permata Depok Nilam F5a No. 5
 *  Citayam - Depok 16430
 *  Jawa Barat - Indonesia
 *  HP/WA : 085770706777
 *  Email : aignatd@gmail.com
 *
 */

package com.artolanggeng.purnamakertasindo.service;


import com.artolanggeng.purnamakertasindo.pojo.*;
import com.artolanggeng.purnamakertasindo.sending.*;
import com.artolanggeng.purnamakertasindo.setting.Profile;
import com.artolanggeng.purnamakertasindo.utils.FixValue;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Dibuat oleh : ignat
 * Tanggal : 26-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public interface  DataLink
{
  @POST(FixValue.RestfulLogin)
  Call<LoginPojo> LoginService(@Body LoginHolder loginHolder);

  @POST(FixValue.RestfulLogout)
  Call<LoginPojo> LogoutService(@Body LogoutHolder logoutHolder);

  @POST(FixValue.RestfulPassword)
  Call<LoginPojo> PasswordService(@Body PasswordHolder passwordHolder);

  @POST(FixValue.RestfulKartuBaru)
  Call<CustomerPojo> KartuBaruService(@Body CustomerHolder customerHolder);

  @POST(FixValue.RestfulRequest)
  Call<CustomerPojo> RequestService(@Body CustomerHolder customerHolder);

  @POST(FixValue.RestfulFormulir)
  Call<LoginPojo> FormulirService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.RestfulFormulirKecil)
  Call<LoginPojo> FormulirKecilService(@Body FormulirHolderKecil formulirHolder);

  @POST(FixValue.Restfulsynchronize)
  Call<ProsesPojo> DataProsesService(@Body ProsesHolder prosesHolder);

  @POST(FixValue.Restfultimbang)
  Call<ProsesPojo> TimbangService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.Restfulpotongan)
  Call<TimbangPojo> PotonganService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.Restfulupdateqc)
  Call<TimbangPojo> UpdateQCService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.RestfulTambahTimbang)
  Call<LoginPojo> TambahTimbangService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.RestfulPembayaran)
  Call<LoginPojo> PembayaranService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.Restfulnetto)
  Call<TimbangPojo> TimbangNettoService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.RestfulHistory)
  Call<ProsesPojo> DataHistoryService(@Body HistoryHolder historyHolder);

  @POST(FixValue.RestfulDetailHistory)
  Call<CustomerPojo> DetailHistoryService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.Restfulproduct)
  Call<LoginPojo> DataProductService(@Body FormulirHolder formulirHolder);

  @POST(FixValue.RestfulDevice)
  Call<WarehousePojo> DataDeviceService(@Body PasswordHolder deviceHolder);

  @GET(FixValue.RestfulWarehouse)
  Call<WarehousePojo> DataWarehouseService();

  @POST(FixValue.RestfulAutoTimbang)
  Call<TimbangPojo> AutoTimbangService(@Body AutoTimbangHolder autoTimbangHolder);

  @Multipart
  @POST(FixValue.RestfulPhotoBarang)
  Call<LoginPojo> PhotoBarangService(@Part("PemasokID") RequestBody rbPemasokID, @Part("Warehouse") RequestBody rbWarehouse,
                                     @Part("PekerjaanID") RequestBody rbPekerjaanID, @Part MultipartBody.Part Photo);

  @POST(FixValue.RestfulDaftarUser)
  Call<WarehousePojo> DaftarUserService(@Body PasswordHolder deviceHolder);

  @POST(FixValue.RestfulAmbilProfile)
  Call<ProfilePojo> AmbilProfileService(@Body LoginHolder loginHolder);

  @POST(FixValue.RestfulUpdateProfile)
  Call<CustomerPojo> UpdateProfileService(@Body ProfileHolder profileHolder);

  @GET(FixValue.RestfulDataTimbangan)
  Call<TimbangPojo> DataTimbanganService(@Path("warehouse") String warehouse);

  @POST(FixValue.RestfulArmada)
  Call<ProfilePojo> ArmadaService(@Body CustomerHolder customerHolder);

  @POST(FixValue.RestfulRequestKoreksi)
  Call<CustomerPojo> RequestKoreksiService(@Body CustomerHolder customerHolder);
}

