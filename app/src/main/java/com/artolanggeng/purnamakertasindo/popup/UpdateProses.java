package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.Timbang_Adp;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.data.IsiPotongan;
import com.artolanggeng.purnamakertasindo.data.IsiProduct;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.pojo.TimbangPojo;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class UpdateProses extends Dialog
{
	@BindView(R.id.rvPotongan)
	RecyclerView rvPotongan;

	private String TAG = "[UpdateProses]";
	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();
	private List<EditText> lstInput = new ArrayList<>();
	private List<String> lstMsg = new ArrayList<>();
	private Context context = getContext();
	private List<TimbangRsp> timbangRsps = new ArrayList<>();

	private Activity ParentAct;
	private Integer intPekerjaanID;
	private Integer FormAsal;
	private String strProses;

	private Timbang_Adp timbangadapter;

	public UpdateProses(Activity parentAct, Integer intPekerjaanID, Integer FormAsal, String strProses)
	{
		super(parentAct);
		this.ParentAct = parentAct;
		this.intPekerjaanID = intPekerjaanID;
		this.FormAsal = FormAsal;
		this.strProses = strProses;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lay_updateproses);
		ButterKnife.bind(this);

		LinearLayoutManager layoutManager = new LinearLayoutManager(ParentAct, LinearLayoutManager.VERTICAL, false);
		rvPotongan.setLayoutManager(layoutManager);
		AmbilDataTimbangan();
	}

	@OnClick({R.id.btnInputPotong, R.id.btnBatalPotong, R.id.btnRefreshPotong})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.btnBatalPotong:
				Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
				dismiss();
			break;
			case R.id.btnInputPotong:
				String strPesan = context.getString(R.string.msgProsesPotong);

				if(FormAsal == 3)
					strPesan = context.getString(R.string.msgProsesBayar);

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder
					.setTitle(R.string.titleMessege)
					.setMessage(strPesan)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setCancelable(false)
					.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							SimpanDataProses();
						}
					})
					.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
							dialog.cancel();
						}
					});

				AlertDialog alert = builder.create();
				alert.show();
			break;
			case R.id.btnRefreshPotong:
				Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
				AmbilDataTimbangan();
			break;
		}
	}

	private void AmbilDataTimbangan()
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu), context.getResources().getString(R.string.msgAmbilTimbangan));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		TimbangRsp timbangRsp = new TimbangRsp();
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
					if((response.body().getCoreResponse().getKode() == FixValue.intError) || (response.body().getCoreResponse().getKode() == FixValue.intEmpty))
					{
						Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					}
					else
					{
						rvPotongan.invalidate();
						rvPotongan.setHasFixedSize(true);
						timbangRsps = response.body().getTimbanganRsp();
						timbangadapter = new Timbang_Adp(ParentAct, context, timbangRsps, IsiProduct.getInstance().getmProductRsps(), FormAsal, IsiPotongan.getInstance().getmPotongRsp());
						rvPotongan.setAdapter(timbangadapter);
					}
				}
				else
				{
					Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
				}
			}

			@Override
			public void onFailure(Call<ProsesPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	private void SimpanDataProses()
	{
		lstInput.clear();
		lstMsg.clear();

		View v = rvPotongan.getLayoutManager().findViewByPosition(timbangadapter.getItemCount() - 1);
		EditText etProses = null;
		boolean bInput = true;

		if((FormAsal == 2) || (FormAsal == 3))
		{
			if(FormAsal == 2)
			{
				etProses = v.findViewById(R.id.etNilaiPotongan);
				lstMsg.add(context.getResources().getString(R.string.msgPotongan));
			}
			else
			if(FormAsal == 3)
			{
				etProses = v.findViewById(R.id.etBeratNetto);
				lstMsg.add(context.getResources().getString(R.string.msgTonaseNetto));
			}

			lstInput.add(etProses);
			bInput = Fungsi.CekInput(lstInput, lstMsg, context);
		}

		if(bInput)
		{
			if(FormAsal == 2)
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu), context.getResources().getString(R.string.msgSimpanPotongan));
			else
			if(FormAsal == 3)
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu), context.getResources().getString(R.string.msgSimpanTimbangNetto));
			else
			if(FormAsal == 6)
				progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu), context.getResources().getString(R.string.msgSimpanBarangJual));

			progressDialog.setCancelable(false);

			if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
				return;
			}

			TimbangRsp timbangRsp = new TimbangRsp();
			timbangRsp.setId(timbangRsps.get(timbangRsps.size() - 1).getId());
			timbangRsp.setNourut(timbangRsps.get(timbangRsps.size() - 1).getNourut());

			FormulirHolder formulirHolder = null;

			if((FormAsal == 2) || (FormAsal == 6))
			{
				View vi = rvPotongan.getLayoutManager().findViewByPosition(timbangadapter.getItemCount() - 1);
				Spinner spJenisPotong = vi.findViewById(R.id.spJenisPotong);
				Spinner spKodeBarang = vi.findViewById(R.id.spKodeBarang);

				if(FormAsal == 2)
				{
					timbangRsp.setPotongan(Integer.valueOf(etProses.getText().toString()));
					timbangRsp.setJenispotongid(IsiPotongan.getInstance().getmPotongRsp().get(spJenisPotong.getSelectedItemPosition()).getId());
				}
				else
				if(FormAsal == 6)
				{
					timbangRsp.setPotongan(0);
					timbangRsp.setJenispotongid(1);
				}

				timbangRsp.setProductcode(IsiProduct.getInstance().getmProductRsps().get(spKodeBarang.getSelectedItemPosition()).getProductcode().trim());
				formulirHolder = new FormulirHolder(null, timbangRsp);
			}
			else
			if(FormAsal == 3)
			{
				timbangRsp.setTonasenetto(Integer.valueOf(etProses.getText().toString()));

				IsiFormulir isiFormulir = new IsiFormulir();
				isiFormulir.setId(intPekerjaanID);

				if(strProses.matches(context.getString(R.string.strKasirBayar)))
				{
					timbangRsp.setProses(strProses);
					isiFormulir.setPermintaan(-1);
					isiFormulir.setPekerjaan(-1);
				}
				else
				if(strProses.matches(context.getString(R.string.strTimbangBaru)))
				{
					timbangRsp.setProses(strProses);
					timbangRsp.setTanggal(timbangRsps.get(timbangRsps.size() - 1).getTanggal());

					isiFormulir.setPermintaan(1);
					isiFormulir.setPekerjaan(2);
				}

				formulirHolder = new FormulirHolder(isiFormulir, timbangRsp);
			}

			DataLink dataLink = Fungsi.BindingData();
			Call<TimbangPojo> ReceivePojo = null;

			if((FormAsal == 2) || (FormAsal == 6))
				ReceivePojo = dataLink.PotonganService(formulirHolder);
			else
			if(FormAsal == 3)
				ReceivePojo = dataLink.TimbangNettoService(formulirHolder);

			ReceivePojo.enqueue(new Callback<TimbangPojo>()
			{
				@Override
				public void onResponse(Call<TimbangPojo> call, Response<TimbangPojo> response)
				{
					progressDialog.dismiss();

					if(response.isSuccessful())
					{
						if(response.body().getCoreResponse().getKode() == FixValue.intError)
						{
							Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
							popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
						}
						else
						{
							Fungsi.storeToSharedPref(context, 1, Preference.prefUpdateProses);
							dismiss();
						}
					}
					else
					{
						Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
						popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
					}
				}

				@Override
				public void onFailure(Call<TimbangPojo> call, Throwable t)
				{
					progressDialog.dismiss();
					Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
				}
			});
		}
	}
}

