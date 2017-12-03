package com.artolanggeng.purnamakertasindo.timbanganKecil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.TimbanganKecil_Adp;
import com.artolanggeng.purnamakertasindo.data.Customer;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.data.IsiPemasok;
import com.artolanggeng.purnamakertasindo.data.IsiProduct;
import com.artolanggeng.purnamakertasindo.model.PotongRsp;
import com.artolanggeng.purnamakertasindo.model.ProductRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.artolanggeng.purnamakertasindo.model.TimbanganRspKecil;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.popup.TambahArmada;
import com.artolanggeng.purnamakertasindo.sending.CustomerHolder;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolderKecil;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Heru Permana on 10/26/2017.
 */

public class formKecil extends AppCompatActivity {
    @BindView(R.id.tvNamaPanggil)
    TextView tvNamaPanggil;
    @BindView(R.id.tvNamaPT)
    TextView tvNamaPT;
    @BindView(R.id.tvstrNoTelpon)
    TextView tvstrNoTelpon;
    @BindView(R.id.tvAlamat)
    TextView tvAlamat;
    @BindView(R.id.spNoPolisi)
    Spinner spNoPolisi;
    @BindView(R.id.tvNoPolisi)
    TextView tvNoPolisi;
    @BindView(R.id.tvKodePemasok)
    TextView tvKodePemasok;
    //    @BindView(R.id.rvListProses)
//    RecyclerView rvListProses;
//    @BindView(R.id.llSubmit)
//    LinearLayout llSubmit;
    @BindView(R.id.tvStatusProses)
    TextView tvStatusProses;
    @BindView(R.id.ivMenuFormulir)
    ImageView ivMenuFormulir;
    //    @BindView(R.id.tvProsesQC)
//    TextView tvProsesQC;
    @BindView(R.id.tvProsesKasir)
    TextView tvProsesKasir;

    @BindView(R.id.llInputDetailBarang)
    LinearLayout llInputDetailBarang;
    @BindView(R.id.spJenisBarang)
    Spinner spJenisBarang;
    @BindView(R.id.etBeratTimbanganKecil)
    EditText etBeratTimbanganKecil;
    @BindView(R.id.etPotonganBarangKecil)
    EditText etPotonganBarangKecil;

    @BindView(R.id.ivAddDetail)
    ImageView ivAddDetail;

    @BindView(R.id.llInputDetailBarang2)
    LinearLayout llInputDetailBarang2;
    @BindView(R.id.spJenisBarang2)
    Spinner spJenisBarang2;
    @BindView(R.id.etBeratTimbanganKecil2)
    EditText etBeratTimbanganKecil2;
    @BindView(R.id.etPotonganBarangKecil2)
    EditText etPotonganBarangKecil2;

    @BindView(R.id.ivAddDetail2)
    ImageView ivAddDetail2;
    @BindView(R.id.ivDeleteDetail2)
    ImageView ivDeleteDetail2;

    @BindView(R.id.llInputDetailBarang3)
    LinearLayout llInputDetailBarang3;
    @BindView(R.id.spJenisBarang3)
    Spinner spJenisBarang3;
    @BindView(R.id.etBeratTimbanganKecil3)
    EditText etBeratTimbanganKecil3;
    @BindView(R.id.etPotonganBarangKecil3)
    EditText etPotonganBarangKecil3;

    @BindView(R.id.ivDeleteDetail3)
    ImageView ivDeleteDetail;
    @BindView(R.id.llProsesKasir)
    LinearLayout llProsesKasir;
    private PopupMessege pesan = new PopupMessege();
    private ProgressDialog progressDialog;
    private String TAG = "[Formulir]";
    private Context context = this;
    private Activity activity = this;

    private List<TimbangRsp> lstTimbang = null;
    private List<TimbanganRspKecil> lstTimbangHolder = null;

    private TimbangRsp timbangRsp = new TimbangRsp();
    private TimbanganRspKecil timbangRspKecil = new TimbanganRspKecil();

    private ArrayAdapter<String> dataAdapter;
    private ArrayAdapter<String> dataAdapter2;
    private Integer intTimbang;
    private List<ProductRsp> productRsps;
    private List<PotongRsp> potonganRsps;

    private MenuBuilder menuBuilder;
    private MenuPopupHelper menuHelper;

    private SimpleDateFormat df;
    private Calendar calendar;
    private TimbanganKecil_Adp formadapterkecil;

    private EditText etBeratBruto;
    private EditText etBeratNetto;
    private Spinner spKodeBarang;
    private TextView tvTglTimbang;
    private int sizeLayout = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_formkecil);
        ButterKnife.bind(this);

        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        Bundle extras = getIntent().getExtras();
        String KodePemasok = extras.getString("KodePemasok");
        intTimbang = extras.getInt("Timbang") + 1;

        tvKodePemasok.setText(KodePemasok);
        spNoPolisi.setVisibility(View.VISIBLE);
        tvNoPolisi.setVisibility(View.GONE);
        tvStatusProses.setText(context.getString(R.string.strHeaderListProgress, String.valueOf(intTimbang - 1)));
        AmbilInfoPemasok(KodePemasok);
    }

    private void AmbilInfoPemasok(String PemasokID) {
        progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
                context.getResources().getString(R.string.msgDataPemasok));
        progressDialog.setCancelable(false);

        if (Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE) {
            progressDialog.dismiss();
            pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
            return;
        }

        Customer customer = new Customer();
        customer.setPemasokID(PemasokID);

        CustomerHolder customerHolder = new CustomerHolder(customer);
        DataLink dataLink = Fungsi.BindingData();

        final Call<CustomerPojo> ReceivePojo = dataLink.RequestService(customerHolder);

        ReceivePojo.enqueue(new Callback<CustomerPojo>() {
            @Override
            public void onResponse(Call<CustomerPojo> call, Response<CustomerPojo> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getCoreResponse().getKode() == FixValue.intError) {
//                        rvListProses.setVisibility(View.GONE);
//                        llSubmit.setVisibility(View.GONE);
                        pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
                    } else {
                        tvNamaPanggil.setText(response.body().getCustomerrsp().getPanggilan());
                        tvNamaPT.setText(response.body().getCustomerrsp().getPerusahaan());
                        tvstrNoTelpon.setText(response.body().getCustomerrsp().getTelpon());
                        tvAlamat.setText(response.body().getCustomerrsp().getAlamat());

                        if (response.body().getVehiclersp() != null) {
                            String[] items = new String[response.body().getVehiclersp().size()];

                            for (int i = 0; i < response.body().getVehiclersp().size(); i++) {
                                items[i] = (i + 1) + ". " + response.body().getVehiclersp().get(i).getNopolisi();
                            }

//                            rvListProses.setVisibility(View.VISIBLE);

                            ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
                            dataAdapter = new ArrayAdapter<>(activity, R.layout.spiner_text, lst);
                            dataAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
                            spNoPolisi.setAdapter(dataAdapter);
                        }

                        lstTimbang = response.body().getTimbangrsp();
                        TampilkanDataTimbangan();
                    }
                } else {
//                    rvListProses.setVisibility(View.GONE);
//                    llSubmit.setVisibility(View.GONE);
                    pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
                }
            }

            @Override
            public void onFailure(Call<CustomerPojo> call, Throwable t) {
                progressDialog.dismiss();
//                rvListProses.setVisibility(View.GONE);
//                llSubmit.setVisibility(View.GONE);
                pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
            }
        });
    }

    private void BackActivity() {
        pesan.ShowMessege6(context, getResources().getString(R.string.msgBatalTimbang), activity);
    }

    @Override
    public void onBackPressed() {
        BackActivity();
    }

    private void UploadFormulir() {
        progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
                context.getResources().getString(R.string.msgDataPemasok));
        progressDialog.setCancelable(false);

        if (Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE) {
            progressDialog.dismiss();
            pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
            return;
        }

//        formadapterkecil.getItemId(formadapterkecil.getItemCount() - 1);

        DataLink dataLink = Fungsi.BindingData();
        FormulirHolderKecil formulirHolder;
        Call<LoginPojo> ReceivePojo;

        IsiFormulir isiFormulir = new IsiFormulir();
        isiFormulir.setPermintaan(-1);
        isiFormulir.setPekerjaan(-1);
        isiFormulir.setJumlahtimbang(intTimbang);
        Log.d(TAG, "UploadFormulir: " + intTimbang);

//        if (intTimbang == 1) {
        Long tsLong = System.currentTimeMillis() / 1000;
        String[] parts = spNoPolisi.getSelectedItem().toString().split("\\.");

        isiFormulir.setPemasokid(tvKodePemasok.getText().toString());
        isiFormulir.setNopolisi(parts[1].trim());
        isiFormulir.setTgldevice(tsLong.toString());
        isiFormulir.setBisnisunitkode(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));
        isiFormulir.setUserid(Fungsi.getIntFromSharedPref(context, Preference.prefUserID));
        isiFormulir.setJenistimbang(FixValue.TimbangKecil);
        lstTimbangHolder = new ArrayList<>();
        if (llInputDetailBarang.getVisibility() == View.VISIBLE) {
//            timbangRsp = new TimbangRsp();
            timbangRspKecil.setNourut(1);
            timbangRspKecil.setTonasebruto(Integer.valueOf(etBeratTimbanganKecil.getText().toString()));
            timbangRspKecil.setTonasenetto(Integer.valueOf(etBeratTimbanganKecil.getText().toString()));
            timbangRspKecil.setPotongan(Integer.valueOf(etPotonganBarangKecil.getText().toString()));
            timbangRspKecil.setTanggal(Fungsi.curentTime());
            timbangRspKecil.setJenispotongid(1);
            timbangRspKecil.setProductcode(IsiProduct.getInstance().getmProductRsps().get(spJenisBarang.getSelectedItemPosition()).getProductcode().trim());
            lstTimbangHolder.add(timbangRspKecil);
        }

        if (llInputDetailBarang2.getVisibility() == View.VISIBLE) {
            timbangRspKecil.setNourut(2);
            timbangRspKecil.setTonasebruto(Integer.valueOf(etBeratTimbanganKecil2.getText().toString()));
            timbangRspKecil.setTonasenetto(Integer.valueOf(etBeratTimbanganKecil2.getText().toString()));
            timbangRspKecil.setTanggal(Fungsi.curentTime());
            timbangRspKecil.setJenispotongid(1);
            timbangRspKecil.setProductcode(IsiProduct.getInstance().getmProductRsps().get(spJenisBarang2.getSelectedItemPosition()).getProductcode().trim());
            timbangRspKecil.setPotongan(Integer.valueOf(etPotonganBarangKecil2.getText().toString()));
            lstTimbangHolder.add(timbangRspKecil);

        }

        if (llInputDetailBarang3.getVisibility() == View.VISIBLE) {
            timbangRspKecil.setNourut(3);
            timbangRspKecil.setTonasebruto(Integer.valueOf(etBeratTimbanganKecil3.getText().toString()));
            timbangRspKecil.setTonasenetto(Integer.valueOf(etBeratTimbanganKecil3.getText().toString()));
            timbangRspKecil.setTanggal(Fungsi.curentTime());
            timbangRspKecil.setProductcode(IsiProduct.getInstance().getmProductRsps().get(spJenisBarang3.getSelectedItemPosition()).getProductcode().trim());
            timbangRspKecil.setPotongan(Integer.valueOf(etPotonganBarangKecil2.getText().toString()));
            lstTimbangHolder.add(timbangRspKecil);

        }
        formulirHolder = new FormulirHolderKecil(isiFormulir, lstTimbangHolder);
        ReceivePojo = dataLink.FormulirKecilService(formulirHolder);
//        } else {
//            isiFormulir.setId(IsiPemasok.getInstance().getCustomerRsp().getId());
//            Timbang.getInstance().setPekerjaanid(IsiPemasok.getInstance().getCustomerRsp().getId());
//            formulirHolder = new FormulirHolder(isiFormulir, lstTimbang.get(formadapterkecil.getItemCount() - 1));
//            ReceivePojo = dataLink.TambahTimbangService(formulirHolder);
//        }

        ReceivePojo.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getCoreResponse().getKode() == FixValue.intError)
                        pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
                    else {
                        Intent LoginIntent = new Intent(formKecil.this, formTimbanganKecil.class);
                        startActivity(LoginIntent);
                        finish();
                    }
                } else
                    pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                progressDialog.dismiss();
                pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
            }
        });
    }

    private void AmbilDataTimbangan(Integer intPekerjaanID) {
        progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
                context.getResources().getString(R.string.msgAmbilTimbangan));
        progressDialog.setCancelable(false);

        if (Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE) {
            progressDialog.dismiss();
            pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
            return;
        }

        timbangRsp = new TimbangRsp();
        timbangRsp.setPekerjaanid(intPekerjaanID);

        FormulirHolder formulirHolder = new FormulirHolder(null, timbangRsp);
        DataLink dataLink = Fungsi.BindingData();

        final Call<ProsesPojo> ReceivePojo = dataLink.TimbangService(formulirHolder);

        ReceivePojo.enqueue(new Callback<ProsesPojo>() {
            @Override
            public void onResponse(Call<ProsesPojo> call, Response<ProsesPojo> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getCoreResponse().getKode() == FixValue.intError)
                        pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
                    else {
                        lstTimbang = response.body().getTimbanganRsp();
                        TampilkanDataTimbangan();
                    }
                } else
                    pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
            }

            @Override
            public void onFailure(Call<ProsesPojo> call, Throwable t) {
                progressDialog.dismiss();
                pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
            }
        });
    }

    private void TampilkanDataTimbangan() {
//        rvListProses.setVisibility(View.VISIBLE);

        timbangRsp = new TimbangRsp();
        timbangRsp.setTanggal(df.format(calendar.getTime()));

        if (lstTimbang == null) {
            intTimbang = 1;
            lstTimbang = new ArrayList<>();
        } else
            intTimbang = lstTimbang.size() + 1;

        lstTimbang.add(timbangRsp);

        productRsps = IsiProduct.getInstance().getmProductRsps();
        String[] items = new String[productRsps.size()];
        for (int i = 0; i < productRsps.size(); i++) {
            items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + " / " + productRsps.get(i).getProductname().trim();
        }

        ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
        dataAdapter = new ArrayAdapter<>(activity, R.layout.spiner_text, lst);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        spJenisBarang.setAdapter(dataAdapter);
        spJenisBarang2.setAdapter(dataAdapter);
        spJenisBarang3.setAdapter(dataAdapter);

    }

    private void RefreshFormulir() {
        if ((intTimbang - 1) == 0)
            AmbilInfoPemasok(tvKodePemasok.getText().toString());
        else
            AmbilDataTimbangan(IsiPemasok.getInstance().getCustomerRsp().getId());
    }

    private void TambahArmada() {
        TambahArmada tambahArmada = new TambahArmada(formKecil.this);
        tambahArmada.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tambahArmada.show();
        tambahArmada.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                String[] items = new String[1];
                items[0] = Fungsi.getStringFromSharedPref(context, Preference.PrefListArmada);
                ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
                dataAdapter.addAll(lst);
                dataAdapter.notifyDataSetChanged();
                spNoPolisi.setAdapter(dataAdapter);
                spNoPolisi.setSelection(dataAdapter.getCount() - 1);
            }
        });
    }

    @OnClick({R.id.ivBackFormulir, R.id.ivMenuFormulir, R.id.rlMenuFormulir, R.id.tvProsesKasir, R.id.llProsesKasir, R.id.ivAddDetail, R.id.ivAddDetail2, R.id.ivDeleteDetail2, R.id.ivDeleteDetail3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBackFormulir:
                BackActivity();
                break;
            case R.id.rlMenuFormulir:
            case R.id.ivMenuFormulir:
                menuBuilder = new MenuBuilder(context);
                new SupportMenuInflater(context).inflate(R.menu.menu_formulir, menuBuilder);
                menuHelper = new MenuPopupHelper(context, menuBuilder, ivMenuFormulir);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.mnuTimbangBaru:
                                menuHelper.dismiss();
                                return true;
//                            case R.id.mnuTambahArmada:
//                                menuHelper.dismiss();
//                                TambahArmada();
//                                return true;
                            case R.id.mnuRefresh:
                                menuHelper.dismiss();
                                RefreshFormulir();
                                return true;
                        }

                        return false;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });
                break;
            case R.id.tvProsesKasir:
            case R.id.llProsesKasir:
                UploadFormulir();
//                pembayaranKecil PembayaranKecil = new pembayaranKecil(context, activity, IsiPemasok.getInstance().getCustomerRsp().getId());
//                PembayaranKecil.ProsesBayar();
                break;
            case R.id.ivAddDetail:
                llInputDetailBarang2.setVisibility(View.VISIBLE);
                ivAddDetail.setVisibility(View.GONE);
                sizeLayout = sizeLayout + 1;
                break;
            case R.id.ivAddDetail2:
                llInputDetailBarang3.setVisibility(View.VISIBLE);
                ivDeleteDetail2.setVisibility(View.GONE);
                ivAddDetail2.setVisibility(View.GONE);
                sizeLayout = sizeLayout + 1;
                break;
            case R.id.ivDeleteDetail2:
                llInputDetailBarang2.setVisibility(View.GONE);
                ivAddDetail.setVisibility(View.VISIBLE);
                sizeLayout = sizeLayout - 1;

                break;
            case R.id.ivDeleteDetail3:
                llInputDetailBarang3.setVisibility(View.GONE);
                ivDeleteDetail2.setVisibility(View.VISIBLE);
                ivAddDetail2.setVisibility(View.VISIBLE);
                sizeLayout = sizeLayout - 1;

                break;
        }
    }

}
