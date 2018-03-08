package com.artolanggeng.purnamakertasindo.koreksi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.Koreksi_Adp;
import com.artolanggeng.purnamakertasindo.adapter.Timbang_Adp;
import com.artolanggeng.purnamakertasindo.data.AutoTimbang;
import com.artolanggeng.purnamakertasindo.data.Customer;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.data.IsiPemasok;
import com.artolanggeng.purnamakertasindo.data.IsiProduct;
import com.artolanggeng.purnamakertasindo.data.Timbang;
import com.artolanggeng.purnamakertasindo.model.PrinterRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.artolanggeng.purnamakertasindo.penjualan.MainJual;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.pojo.LoginPojo;
import com.artolanggeng.purnamakertasindo.pojo.ProfilePojo;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.pojo.TimbangPojo;
import com.artolanggeng.purnamakertasindo.popup.TambahArmada;
import com.artolanggeng.purnamakertasindo.sending.AutoTimbangHolder;
import com.artolanggeng.purnamakertasindo.sending.CustomerHolder;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.service.Pembayaran;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.warehouse.MainProses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormKoreksi extends AppCompatActivity
{
	@BindView(R.id.tvNamaPanggil)
	TextView tvNamaPanggil;
	@BindView(R.id.tvNamaPT)
	TextView tvNamaPT;
	@BindView(R.id.tvstrNoTelpon)
	TextView tvstrNoTelpon;
	@BindView(R.id.tvAlamat)
	TextView tvAlamat;
	@BindView(R.id.tvNoPolisi)
	TextView tvNoPolisi;
	@BindView(R.id.tvKodePemasok)
	TextView tvKodePemasok;
	@BindView(R.id.rvListProses)
	RecyclerView rvListProses;
	@BindView(R.id.tvStatusProses)
	TextView tvStatusProses;
	@BindView(R.id.tvUpdateKoreksi)
	TextView tvUpdateKoreksi;
	@BindView(R.id.tvHeader)
	TextView tvHeader;

	private PopupMessege pesan = new PopupMessege();
	private ProgressDialog progressDialog;
	private String TAG = "[Form Koreksi]";
	private Context context = this;
	private Activity activity = this;

	private List<TimbangRsp> lstTimbang = null;

	private SimpleDateFormat df, datePrint, timePrint;
	private Calendar calendar;
	private Koreksi_Adp formadapter;

	String History;
	String Jual;
	String PekerjaanID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_formkoreksi);
		ButterKnife.bind(this);

		calendar = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

		Bundle extras = getIntent().getExtras();
		String KodePemasok = extras.getString("KodePemasok");
		History = extras.getString("History");
		Jual = extras.getString("Jual");
		PekerjaanID = extras.getString("PekerjaanID");
		tvKodePemasok.setText(KodePemasok);

		AmbilInfoPemasok(KodePemasok);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rvListProses.setLayoutManager(layoutManager);

		datePrint = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		timePrint = new SimpleDateFormat("HH:mm:ss", Locale.US);
	}

	private void AmbilInfoPemasok(String PemasokID)
	{
		progressDialog = ProgressDialog.show(context, getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgDataPemasok));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		Customer customer = new Customer();
		customer.setPemasokID(PemasokID);
		customer.setPekerjaanID(PekerjaanID);

		CustomerHolder customerHolder = new CustomerHolder(customer);
		DataLink dataLink = Fungsi.BindingData();

		final Call<CustomerPojo> ReceivePojo = dataLink.RequestKoreksiService(customerHolder);

		ReceivePojo.enqueue(new Callback<CustomerPojo>()
		{
			@Override
			public void onResponse(Call<CustomerPojo> call, Response<CustomerPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
					{
						rvListProses.setVisibility(View.GONE);
						pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					}
					else
					{
						tvStatusProses.setText(context.getString(R.string.strHeaderListKoreksi, String.valueOf(response.body().getCustomerrsp().getJumlahtimbang())));
						tvNoPolisi.setText(response.body().getCustomerrsp().getNopolisi());
						tvNamaPanggil.setText(response.body().getCustomerrsp().getPanggilan());
						tvNamaPT.setText(response.body().getCustomerrsp().getPerusahaan());
						tvstrNoTelpon.setText(response.body().getCustomerrsp().getTelpon());
						tvAlamat.setText(response.body().getCustomerrsp().getAlamat());

						lstTimbang = response.body().getTimbangrsp();
						TampilkanDataTimbangan(response.body().getCustomerrsp().getJenistimbang());
					}
				} else
				{
					rvListProses.setVisibility(View.GONE);
					pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
				}
			}

			@Override
			public void onFailure(Call<CustomerPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				rvListProses.setVisibility(View.GONE);
				pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	private void BackActivity()
	{
		if((History.matches("History")) || ((History.matches("HistoryJual"))))
			finish();
		else
			pesan.ShowMessege6(context, getResources().getString(R.string.msgBatalKoreksi), activity);
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

	private void TampilkanDataTimbangan(Integer JenisTimbang)
	{
		rvListProses.setVisibility(View.VISIBLE);

		if(lstTimbang == null)
		{
			pesan.ShowMessege1(context, context.getResources().getString(R.string.msgTimbangKosong));
			return;
		}

		rvListProses.invalidate();
		formadapter = new Koreksi_Adp(activity, context, lstTimbang, JenisTimbang);
		rvListProses.setAdapter(formadapter);
	}

	@OnClick({R.id.ivBackFormulir, R.id.tvUpdateKoreksi})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivBackFormulir:
				BackActivity();
			break;
			case R.id.tvUpdateKoreksi:
				BackActivity();
			break;
		}
	}

	private void AmbilDataProduct(String strBisnisUnitKode)
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgAmbilProduct));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		IsiFormulir isiFormulir = new IsiFormulir();
		isiFormulir.setBisnisunitkode(strBisnisUnitKode);

		FormulirHolder formulirHolder = new FormulirHolder(isiFormulir, null);
		DataLink dataLink = Fungsi.BindingData();

		final Call<LoginPojo> ReceivePojo = dataLink.DataProductService(formulirHolder);

		ReceivePojo.enqueue(new Callback<LoginPojo>()
		{
			@Override
			public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						IsiProduct.initIsiProduct();
						IsiProduct.getInstance().setmProductRsps(response.body().getProductrsp());
					}
				}
				else
					pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<LoginPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
