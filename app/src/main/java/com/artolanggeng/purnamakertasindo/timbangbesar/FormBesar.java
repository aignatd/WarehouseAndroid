package com.artolanggeng.purnamakertasindo.timbangbesar;

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
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.Timbang_Adp;
import com.artolanggeng.purnamakertasindo.data.*;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.*;

public class FormBesar extends AppCompatActivity
{
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
	@BindView(R.id.rvListProses)
	RecyclerView rvListProses;
	@BindView(R.id.llSubmit)
	LinearLayout llSubmit;
	@BindView(R.id.tvStatusProses)
	TextView tvStatusProses;
	@BindView(R.id.ivMenuFormulir)
	ImageView ivMenuFormulir;
	@BindView(R.id.tvProsesQC)
	TextView tvProsesQC;
	@BindView(R.id.tvProsesKasir)
	TextView tvProsesKasir;
	@BindView(R.id.ivPrintTimbang)
	ImageView ivPrintTimbang;
	@BindView(R.id.ivPhotoTimbang)
	ImageView ivPhotoTimbang;
	@BindView(R.id.tvHeader)
	TextView tvHeader;

	private PopupMessege pesan = new PopupMessege();
	private ProgressDialog progressDialog;
	private String TAG = "[Formulir]";
	private Context context = this;
	private Activity activity = this;

	private List<TimbangRsp> lstTimbang = null;
	private TimbangRsp timbangRsp = new TimbangRsp();

	private ArrayAdapter<String> dataAdapter;
	private Integer intTimbang;

	private MenuBuilder menuBuilder;
	private MenuPopupHelper menuHelper;

	private SimpleDateFormat df, datePrint, timePrint;
	private Calendar calendar;
	private Timbang_Adp formadapter;

	private EditText etBeratBruto;
	private EditText etBeratNetto;
	private Spinner spKodeBarang;
	private TextView tvTglTimbang;

	String History;
	String Jual;
	Integer Pemasok;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_formbesar);
		ButterKnife.bind(this);

		calendar = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

		Bundle extras = getIntent().getExtras();
		String KodePemasok = extras.getString("KodePemasok");
		intTimbang = extras.getInt("Timbang") + 1;
		History = extras.getString("History");
		Jual = extras.getString("Jual");
		Pemasok = extras.getInt("Pemasok");

		if((History.matches("History")) || ((History.matches("HistoryJual"))))
		{
			tvHeader.setText(getResources().getString(R.string.titleHistory));
			ivMenuFormulir.setVisibility(View.GONE);
		}

		tvKodePemasok.setText(KodePemasok);
		tvProsesQC.setText(context.getString(R.string.strProsesQC, "", ""));

		ivPrintTimbang.setVisibility(View.GONE);
		ivPhotoTimbang.setVisibility(View.GONE);

		if((intTimbang - 1) == 0)
		{
			tvProsesKasir.setText(context.getString(R.string.strSebelumnya));
			spNoPolisi.setVisibility(View.VISIBLE);
			tvNoPolisi.setVisibility(View.GONE);
			tvStatusProses.setText(context.getString(R.string.strHeaderListProgress, String.valueOf(intTimbang)));
			AmbilInfoPemasok(KodePemasok);
		}
		else
		{
			tvProsesKasir.setText(context.getString(R.string.strKasirBayar));
			spNoPolisi.setVisibility(View.GONE);
			tvNoPolisi.setVisibility(View.VISIBLE);
			tvStatusProses.setText(context.getString(R.string.strProsesTimbang, String.valueOf(IsiPemasok.getInstance().getCustomerRsp().getId()) + ". ",
				" (" + String.valueOf(IsiPemasok.getInstance().getCustomerRsp().getJumlahtimbang()) + ")"));

			tvNamaPanggil.setText(IsiPemasok.getInstance().getCustomerRsp().getPanggilan());
			tvNamaPT.setText(IsiPemasok.getInstance().getCustomerRsp().getPerusahaan());
			tvAlamat.setText(IsiPemasok.getInstance().getCustomerRsp().getAlamat());
			tvstrNoTelpon.setText(IsiPemasok.getInstance().getCustomerRsp().getTelpon());

			spNoPolisi.setVisibility(View.GONE);
			tvNoPolisi.setVisibility(View.VISIBLE);
			tvNoPolisi.setText(IsiPemasok.getInstance().getCustomerRsp().getNopolisi());
			llSubmit.setVisibility(View.VISIBLE);

			AmbilDataTimbangan(IsiPemasok.getInstance().getCustomerRsp().getId());
		}

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

		CustomerHolder customerHolder = new CustomerHolder(customer);
		DataLink dataLink = Fungsi.BindingData();

		final Call<CustomerPojo> ReceivePojo = dataLink.RequestService(customerHolder);

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
						llSubmit.setVisibility(View.GONE);
						pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					}
					else
					{
						tvNamaPanggil.setText(response.body().getCustomerrsp().getPanggilan());
						tvNamaPT.setText(response.body().getCustomerrsp().getPerusahaan());
						tvstrNoTelpon.setText(response.body().getCustomerrsp().getTelpon());
						tvAlamat.setText(response.body().getCustomerrsp().getAlamat());

						if(response.body().getVehiclersp() != null)
						{
							String[] items = new String[response.body().getVehiclersp().size()];

							for(int i = 0; i < response.body().getVehiclersp().size(); i++)
							{
								items[i] = (i + 1) + ". " + response.body().getVehiclersp().get(i).getNopolisi() +
									" / " + response.body().getVehiclersp().get(i).getStatus();
							}

							rvListProses.setVisibility(View.VISIBLE);

							ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
							dataAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, lst);
							dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							spNoPolisi.setAdapter(dataAdapter);
						}

						lstTimbang = response.body().getTimbangrsp();

						if((History.matches("History")) || ((History.matches("HistoryJual"))))
							TampilkanDataTimbangan();
						else
							AmbilDataTimbangOtomatis();
					}
				} else
				{
					rvListProses.setVisibility(View.GONE);
					llSubmit.setVisibility(View.GONE);
					pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
				}
			}

			@Override
			public void onFailure(Call<CustomerPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				rvListProses.setVisibility(View.GONE);
				llSubmit.setVisibility(View.GONE);
				pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	private void BackActivity()
	{
		if((History.matches("History")) || ((History.matches("HistoryJual"))))
			finish();
		else
		if(Pemasok == 1)
			pesan.ShowMessege2(context, getResources().getString(R.string.msgBatalTimbang), activity, MainProses.class);
		else
			pesan.ShowMessege6(context, getResources().getString(R.string.msgBatalTimbang), activity);
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

	private void UploadFormulir()
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

		formadapter.getItemId(formadapter.getItemCount()-1);

		DataLink dataLink = Fungsi.BindingData();
		FormulirHolder formulirHolder;
		Call<LoginPojo> ReceivePojo;

		IsiFormulir isiFormulir = new IsiFormulir();
		isiFormulir.setJumlahtimbang(intTimbang);

		if(intTimbang == 1)
		{
			Long tsLong = System.currentTimeMillis() / 1000;
			String[] temps = spNoPolisi.getSelectedItem().toString().split("\\.");
			String[] parts = temps[1].split(" / ");

			isiFormulir.setPemasokid(tvKodePemasok.getText().toString());
			isiFormulir.setNopolisi(parts[0].trim());
			isiFormulir.setStatus(parts[1].trim());
			isiFormulir.setTgldevice(tsLong.toString());
			isiFormulir.setBisnisunitkode(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));
			isiFormulir.setUserid(Fungsi.getIntFromSharedPref(context, Preference.prefUserID));
			isiFormulir.setTimbanganid(Fungsi.getIntFromSharedPref(context, Preference.PrefTimbangBesar));

			if(Jual.matches("Jual"))
				isiFormulir.setJenistimbang(FixValue.TimbangJual);
			else
				isiFormulir.setJenistimbang(FixValue.TimbangBesar);

			timbangRsp = new TimbangRsp();
			timbangRsp.setNourut(intTimbang);
			timbangRsp.setTanggal(tvTglTimbang.getText().toString());

			String strBerat = etBeratBruto.getText().toString().substring(12);
			strBerat = strBerat.substring(0, strBerat.length() - 3);

			if(Jual.matches("Jual"))
			{
				isiFormulir.setPermintaan(1);
				isiFormulir.setPekerjaan(1);
				timbangRsp.setTonasenetto(Integer.valueOf(strBerat.trim()));
			}
			else
			{
				isiFormulir.setPermintaan(1);
				isiFormulir.setPekerjaan(2);
				timbangRsp.setTonasebruto(Integer.valueOf(strBerat.trim()));
			}

			formulirHolder = new FormulirHolder(isiFormulir, timbangRsp);
			ReceivePojo = dataLink.FormulirService(formulirHolder);
		}
		else
		{
			isiFormulir.setPermintaan(1);
			isiFormulir.setPekerjaan(2);
			isiFormulir.setId(IsiPemasok.getInstance().getCustomerRsp().getId());
			Timbang.getInstance().setPekerjaanid(IsiPemasok.getInstance().getCustomerRsp().getId());
			formulirHolder = new FormulirHolder(isiFormulir, lstTimbang.get(formadapter.getItemCount()-1));
			ReceivePojo = dataLink.TambahTimbangService(formulirHolder);
		}

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
						final String Printer = Fungsi.getStringFromSharedPref(context, Preference.prefPortName);
						final PrinterRsp printerRsp = response.body().getPrinterRsp();

						if(Printer.isEmpty() || (Jual.matches("Jual")) || (printerRsp == null))
							LanjutProses();
						else
							ProsesPrint(Printer, printerRsp);
					}
				} else
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

	private void AmbilDataTimbangan(Integer intPekerjaanID)
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgAmbilTimbangan));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		timbangRsp = new TimbangRsp();
		timbangRsp.setPekerjaanid(intPekerjaanID);

		FormulirHolder formulirHolder = new FormulirHolder(null, timbangRsp);
		DataLink dataLink = Fungsi.BindingData();

		final Call<ProsesPojo> ReceivePojo = dataLink.TimbangService(formulirHolder);

		ReceivePojo.enqueue(new Callback<ProsesPojo>()
		{
			@Override
			public void onResponse(Call<ProsesPojo> call, Response<ProsesPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						lstTimbang = response.body().getTimbanganRsp();

						if((History.matches("History")) || ((History.matches("HistoryJual"))))
							TampilkanDataTimbangan();
						else
							AmbilDataTimbangOtomatis();
					}
				}
				else
					pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<ProsesPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	private void TampilkanDataTimbangan()
	{
		rvListProses.setVisibility(View.VISIBLE);

		timbangRsp = new TimbangRsp();
		timbangRsp.setTanggal(df.format(calendar.getTime()));

		if(lstTimbang == null)
		{
			intTimbang = 1;
			lstTimbang = new ArrayList<>();
		}
		else
			intTimbang = lstTimbang.size() + 1;

		rvListProses.invalidate();

		Integer intFormAsal = 1;

		if((History.matches("History")) || ((History.matches("HistoryJual"))))
		{
			tvHeader.setText(getResources().getString(R.string.titleHistory));
			llSubmit.setVisibility(View.GONE);

			if(History.matches("History"))
				intFormAsal = 4;
			else
				intFormAsal = 8;
		}
		else
		{
			if(Jual.matches("Jual"))
				intFormAsal = 7;

			lstTimbang.add(timbangRsp);
			llSubmit.setVisibility(View.VISIBLE);
		}

		formadapter = new Timbang_Adp(activity, context, lstTimbang, intFormAsal, null);
		rvListProses.setAdapter(formadapter);
	}

	private void RefreshFormulir()
	{
		if((intTimbang - 1) == 0)
			AmbilInfoPemasok(tvKodePemasok.getText().toString());
		else
			AmbilDataTimbangan(IsiPemasok.getInstance().getCustomerRsp().getId());
	}

	private void TambahArmada()
	{
		Fungsi.storeToSharedPref(context, "", Preference.PrefListArmada);

		TambahArmada tambahArmada = new TambahArmada(FormBesar.this);
		tambahArmada.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		tambahArmada.show();
		tambahArmada.setOnDismissListener(new DialogInterface.OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialogInterface)
			{
				String strTemp = (spNoPolisi.getCount() + 1) + ". " + Fungsi.getStringFromSharedPref(context, Preference.PrefListArmada);
				Integer intTemp = Fungsi.getIntFromSharedPref(context, Preference.prefKendaraan);

				if(strTemp.matches(""))
					pesan.ShowMessege1(context, getResources().getString(R.string.msgNoPolisi));
				else
				{
					if(!strTemp.matches("Batal"))
					{
						String[] items = new String[1];

						if(intTemp == 1)
							items[0] = strTemp + " / Internal";
						else
							items[0] = strTemp + " / Eksternal";

						ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
						dataAdapter.addAll(lst);
						dataAdapter.notifyDataSetChanged();
						spNoPolisi.setAdapter(dataAdapter);
						spNoPolisi.setSelection(dataAdapter.getCount() - 1);
						SimpanDataArmada(strTemp);
					}
				}
			}
		});
	}

	private void SimpanDataArmada(String strArmada)
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgSimpanArmada));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			pesan.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		Customer customer = new Customer();
		customer.setNopolisi(strArmada);
		customer.setPemasokID(tvKodePemasok.getText().toString().trim());
		customer.setKendaraan(Fungsi.getIntFromSharedPref(context, Preference.prefKendaraan));

		CustomerHolder customerHolder = new CustomerHolder(customer);
		DataLink dataLink = Fungsi.BindingData();

		final Call<ProfilePojo> ReceivePojo = dataLink.ArmadaService(customerHolder);

		ReceivePojo.enqueue(new Callback<ProfilePojo>()
		{
			@Override
			public void onResponse(Call<ProfilePojo> call, Response<ProfilePojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
					pesan.ShowMessege1(context, response.body().getCoreResponse().getPesan());
				else
					pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<ProfilePojo> call, Throwable t)
			{
				progressDialog.dismiss();
				pesan.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	@OnClick({R.id.ivBackFormulir, R.id.ivMenuFormulir, R.id.rlMenuFormulir, R.id.tvProsesKasir, R.id.llProsesKasir, R.id.tvProsesQC, R.id.llProsesQC})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
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

				menuBuilder.setCallback(new MenuBuilder.Callback()
				{
					@Override
					public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem)
					{
						switch(menuItem.getItemId())
						{
							case R.id.mnuTimbangBaru:
								menuHelper.dismiss();
							return true;
							case R.id.mnuTambahArmada:
								menuHelper.dismiss();
								TambahArmada();
							return true;
							case R.id.mnuRefresh:
								menuHelper.dismiss();
								AmbilDataProduct(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));
							return true;
						}

						return false;
					}

					@Override
					public void onMenuModeChange(MenuBuilder menu)
					{
					}
				});
			break;
			case R.id.tvProsesKasir:
			case R.id.llProsesKasir:
				if(tvProsesKasir.getText().toString().matches(context.getString(R.string.strSebelumnya)))
					BackActivity();
				else if(tvProsesKasir.getText().toString().matches(context.getString(R.string.strKasirBayar)))
				{
					Pembayaran pembayaran = new Pembayaran(context, activity, IsiPemasok.getInstance().getCustomerRsp().getId());
					pembayaran.ProsesBayar();
				}
			break;
			case R.id.tvProsesQC:
			case R.id.llProsesQC:
				View v = rvListProses.getLayoutManager().findViewByPosition(formadapter.getItemCount() - 1);
				etBeratBruto = v.findViewById(R.id.etBeratBruto);
				etBeratNetto = v.findViewById(R.id.etBeratNetto);
				spKodeBarang = v.findViewById(R.id.spKodeBarang);
				tvTglTimbang = v.findViewById(R.id.tvTglTimbang);

				if((etBeratBruto.getText().toString().matches("")) && (intTimbang == 1))
					pesan.ShowMessege1(context, getResources().getString(R.string.msgTonaseBruto));
				else
				{
					if((etBeratNetto.getText().toString().matches("")) && (intTimbang > 1))
						pesan.ShowMessege1(context, getResources().getString(R.string.msgTonaseBruto));
					else
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder
							.setTitle(R.string.titleMessege)
							.setMessage(getResources().getString(R.string.msgProsesQC))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setCancelable(false)
							.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									UploadFormulir();
								}
							})
							.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int id)
									{
										dialog.cancel();
									}
								}
							);

						AlertDialog alert = builder.create();
						alert.show();
					}
				}
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
						RefreshFormulir();
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

	private void LanjutProses()
	{
		Intent FormBesarIntent;

		if(Jual.matches("Jual"))
			FormBesarIntent = new Intent(FormBesar.this, MainJual.class);
		else
			FormBesarIntent = new Intent(FormBesar.this, MainProses.class);

		startActivity(FormBesarIntent);
		finish();
	}

	private void ProsesPrint(final String Printer, final PrinterRsp printerRsp)
	{
		final ArrayList<byte[]> list = new ArrayList<>();
		list.clear();

		list.add(new byte[] { 0x1b, 0x1d, 0x61, 0x01 }); // Center in paper
		list.add(new byte[] { 0x1b, 0x1d, 0x74, (byte)0x80 }); // Code Page UTF-8

		// Add BarCode data
		String dtPrint = datePrint.format(calendar.getTime());
		String tmPrint = timePrint.format(calendar.getTime());

		byte[] barCodeData = (printerRsp.getPemasokid() + "#" + printerRsp.getPekerjaanid().toString() + "#" + dtPrint + "#" + tmPrint).getBytes();
		byte cellSize = (byte) (8);
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x53, 0x32, cellSize });
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x44, 0x31, 0x00, (byte) (barCodeData.length % 128), (byte) (barCodeData.length / 128) });
		list.add(barCodeData);
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x50 });
		list.add("\r\n".getBytes());

		list.add(("#" + printerRsp.getPemasokid() + "\r\n").getBytes());
		list.add(("Antrian : " + printerRsp.getPekerjaanid().toString()  + "\r\n").getBytes());

		list.add(("Tanggal : " + dtPrint + "\r\n").getBytes());
		list.add(("Jam : " +  tmPrint + "\r\n").getBytes());

		list.add("\r\n\r\n".getBytes());
		list.add(new byte[] { 0x1b, 0x64, 0x02 }); // Feed to cutter position

		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgAmbilProduct));
		progressDialog.setCancelable(false);

		new Thread(new Runnable()
		{
			public void run()
			{
				final int hasil = Fungsi.sendCommandStar(context, Printer, "", list);

				progressDialog.dismiss();

				runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Log.d(TAG, "run: " + hasil);
						LanjutProses();
					}
				});
			}
		}).start();
	}

	private void AmbilDataTimbangOtomatis()
	{
		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
			return;

		AutoTimbang autoTimbang = new AutoTimbang();
		autoTimbang.setJenisTimbang(2);
		autoTimbang.setWarehouse(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));

		String strAutoTimbang = Fungsi.getStringFromSharedPref(context, Preference.PrefUrlTimbang2);

		if(strAutoTimbang.matches(""))
			return;

		AutoTimbangHolder autoTimbangHolder = new AutoTimbangHolder(autoTimbang);
		DataLink dataLink = Fungsi.BindingTimbangan(strAutoTimbang);

		final Call<TimbangPojo> ReceivePojo = dataLink.AutoTimbangService(autoTimbangHolder);

		ReceivePojo.enqueue(new Callback<TimbangPojo>()
		{
			@Override
			public void onResponse(Call<TimbangPojo> call, Response<TimbangPojo> response)
			{
				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						Fungsi.storeToSharedPref(context, "0", Preference.PrefDataTimbang);
					else
						Fungsi.storeToSharedPref(context, response.body().getTimbanganRsp().getTimbangan(), Preference.PrefDataTimbang);
				}
				else
					Fungsi.storeToSharedPref(context, "0", Preference.PrefDataTimbang);

				TampilkanDataTimbangan();
			}

			@Override
			public void onFailure(Call<TimbangPojo> call, Throwable t)
			{
				Fungsi.storeToSharedPref(context, "0", Preference.PrefDataTimbang);
				TampilkanDataTimbangan();
			}
		});
	}
}
