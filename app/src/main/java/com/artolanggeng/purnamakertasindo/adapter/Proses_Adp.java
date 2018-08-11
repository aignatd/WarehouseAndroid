package com.artolanggeng.purnamakertasindo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.common.Photo;
import com.artolanggeng.purnamakertasindo.data.AutoTimbang;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.model.CustomerRsp;
import com.artolanggeng.purnamakertasindo.pojo.TimbangPojo;
import com.artolanggeng.purnamakertasindo.popup.UpdateProses;
import com.artolanggeng.purnamakertasindo.sending.AutoTimbangHolder;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Dibuat oleh : ignat
 * Tanggal : 24-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class Proses_Adp extends BaseAdapter
{
	private static String TAG = "[Proses_Adp]";
	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();
	private Context context;
	private List<CustomerRsp> customerRsps;
	private Activity activity;
	private Calendar calendar;

	public Proses_Adp(Context context, List<CustomerRsp> customerRsps, Activity activity)
	{
		this.context = context;
		this.customerRsps = customerRsps;
		this.activity = activity;
	}

	@Override
	public int getCount()
	{
		return customerRsps.size();
	}

	@Override
	public Object getItem(int i)
	{
		return null;
	}

	@Override
	public long getItemId(int i)
	{
		return customerRsps.get(i).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup)
	{
		if(view == null)
		{
			LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			view = lInflater.inflate(R.layout.lay_listdataproses, null);
		}

		ViewHolder holder = new ViewHolder(view);
		holder.tvStatusProses.setText(context.getString(R.string.strHeaderListProgress, String.valueOf(customerRsps.get(position).getJumlahtimbang())));
		holder.tvKodePemasok.setText(customerRsps.get(position).getPemasokid());
		holder.tvNamaPanggil.setText(customerRsps.get(position).getPanggilan());
		holder.tvNamaPT.setText(customerRsps.get(position).getPerusahaan());
		holder.tvAlamat.setText(customerRsps.get(position).getAlamat());
		holder.tvstrNoTelpon.setText(customerRsps.get(position).getTelpon());
		holder.spNoPolisi.setVisibility(View.GONE);
		holder.tvNoPolisi.setVisibility(View.VISIBLE);
		holder.tvNoPolisi.setText(customerRsps.get(position).getNopolisi());
		holder.llSubmit.setVisibility(View.VISIBLE);
		holder.tvStatusProses.setTag(position);

		if(customerRsps.get(position).getJenistimbang() == 3)
			holder.ivPrintTimbang.setVisibility(View.GONE);

		if((customerRsps.get(position).getPermintaan() == 1) && ((customerRsps.get(position).getPekerjaan() == 1) ||
			 (customerRsps.get(position).getPekerjaan() == 2)))
		{
			holder.tvProsesKasir.setText(context.getResources().getString(R.string.strPotongan));
			holder.tvProsesQC.setText(context.getString(R.string.strProsesTimbang, "", ""));
			holder.tvStatusProses.setText(context.getString(R.string.strProsesQC, String.valueOf(customerRsps.get(position).getId()) + ". ",
				" (" + String.valueOf(customerRsps.get(position).getJumlahtimbang()) + ")"));
			holder.rlheadatasproses.setBackgroundResource(R.drawable.up_two_rounded_squash);
		}
		else
		if((customerRsps.get(position).getPermintaan() == 2) && ((customerRsps.get(position).getPekerjaan() == 1) ||
			 (customerRsps.get(position).getPekerjaan() == 2)))
		{
			holder.tvProsesKasir.setText(context.getResources().getString(R.string.strKasirBayar));
			holder.tvProsesQC.setText(context.getResources().getString(R.string.strTimbangBaru));
			holder.tvStatusProses.setText(context.getString(R.string.strProsesTimbang, String.valueOf(customerRsps.get(position).getId()) + ". ",
				" (" + String.valueOf(customerRsps.get(position).getJumlahtimbang()) + ")"));
			holder.rlheadatasproses.setBackgroundResource(R.drawable.up_two_rounded_green);
		}

		if(customerRsps.get(position).getJumlahtimbang() == 1)
		{
			if((customerRsps.get(position).getPermintaan() == 1) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				 (customerRsps.get(position).getPekerjaan() == 2)))
				holder.btnGaris1.setBackgroundResource(R.color.squash);
			else
			if((customerRsps.get(position).getPermintaan() == 2) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				 (customerRsps.get(position).getPekerjaan() == 2)))
				holder.btnGaris1.setBackgroundResource(R.color.appleGreen);

			holder.btnGaris2.setBackgroundResource(R.color.whiteThree);
			holder.btnGaris3.setBackgroundResource(R.color.whiteThree);
		}
		else
		if(customerRsps.get(position).getJumlahtimbang() == 2)
		{
			if((customerRsps.get(position).getPermintaan() == 1) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				(customerRsps.get(position).getPekerjaan() == 2)))
			{
				holder.btnGaris1.setBackgroundResource(R.color.squash);
				holder.btnGaris2.setBackgroundResource(R.color.squash);
			}
			else
			if((customerRsps.get(position).getPermintaan() == 2) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				(customerRsps.get(position).getPekerjaan() == 2)))
			{
				holder.btnGaris1.setBackgroundResource(R.color.appleGreen);
				holder.btnGaris2.setBackgroundResource(R.color.appleGreen);
			}

			holder.btnGaris3.setBackgroundResource(R.color.whiteThree);
		}
		else if(customerRsps.get(position).getJumlahtimbang() == 3)
		{
			if((customerRsps.get(position).getPermintaan() == 1) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				(customerRsps.get(position).getPekerjaan() == 2)))
			{
				holder.btnGaris1.setBackgroundResource(R.color.squash);
				holder.btnGaris2.setBackgroundResource(R.color.squash);
				holder.btnGaris3.setBackgroundResource(R.color.squash);
			}
			else
			if((customerRsps.get(position).getPermintaan() == 2) && ((customerRsps.get(position).getPekerjaan() == 1) ||
				(customerRsps.get(position).getPekerjaan() == 2)))
			{
				holder.btnGaris1.setBackgroundResource(R.color.appleGreen);
				holder.btnGaris2.setBackgroundResource(R.color.appleGreen);
				holder.btnGaris3.setBackgroundResource(R.color.appleGreen);
			}
		}

		return view;
	}

	class ViewHolder
	{
		@BindView(R.id.tvStatusProses)
		TextView tvStatusProses;
		@BindView(R.id.tvKodePemasok)
		TextView tvKodePemasok;
		@BindView(R.id.tvNamaPanggil)
		TextView tvNamaPanggil;
		@BindView(R.id.tvNamaPT)
		TextView tvNamaPT;
		@BindView(R.id.tvNoPolisi)
		TextView tvNoPolisi;
		@BindView(R.id.spNoPolisi)
		Spinner spNoPolisi;
		@BindView(R.id.tvAlamat)
		TextView tvAlamat;
		@BindView(R.id.tvstrNoTelpon)
		TextView tvstrNoTelpon;
		@BindView(R.id.llSubmit)
		LinearLayout llSubmit;
		@BindView(R.id.tvProsesKasir)
		TextView tvProsesKasir;
		@BindView(R.id.tvProsesQC)
		TextView tvProsesQC;
		@BindView(R.id.btnGaris1)
		Button btnGaris1;
		@BindView(R.id.btnGaris2)
		Button btnGaris2;
		@BindView(R.id.btnGaris3)
		Button btnGaris3;
		@BindView(R.id.ivPrintTimbang)
		ImageView ivPrintTimbang;
		@BindView(R.id.rlheadatasproses)
		RelativeLayout rlheadatasproses;

		ViewHolder(View view)
		{
			ButterKnife.bind(this, view);
		}

		@OnClick({R.id.tvProsesKasir, R.id.tvProsesQC, R.id.llProsesKasir, R.id.llProsesQC, R.id.ivPhotoTimbang, R.id.ivPrintTimbang})
		public void onViewClicked(View view)
		{
			int intTag = (int) tvStatusProses.getTag();
			final Integer intPekerjaanID = customerRsps.get(intTag).getId();
			final String strPemasokID = customerRsps.get(intTag).getPemasokid();

			switch(view.getId())
			{
				case R.id.llProsesKasir:
				case R.id.tvProsesKasir:
					Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);

					if(tvProsesKasir.getText().equals(context.getResources().getString(R.string.strPotongan)))
					{
						UpdateProses updateProses;

						if(customerRsps.get(intTag).getJenistimbang() == 3)
							updateProses = new UpdateProses(activity, intPekerjaanID, 6, "");
						else
							updateProses = new UpdateProses(activity, intPekerjaanID, 2, "");

						updateProses.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						updateProses.show();
					}
					else
					if(tvProsesKasir.getText().equals(context.getString(R.string.strKasirBayar)))
						BayarAtauTimbangBaru(context.getString(R.string.strKasirBayar), intPekerjaanID, intTag);
				break;
				case R.id.llProsesQC:
				case R.id.tvProsesQC:
					if(tvProsesQC.getText().equals(context.getString(R.string.strProsesTimbang, "", "")))
					{
						if(Fungsi.getIntFromSharedPref(context, Preference.prefUpdateProses) == 0)
						{
							popupMessege.ShowMessege1(context, context.getString(R.string.msgKodeBarang));
							return;
						}

						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder
							.setTitle(R.string.titleMessege)
							.setMessage(context.getString(R.string.msgTanyaDataQC))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setCancelable(false)
							.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									ProsesUpdateQC((Integer) tvStatusProses.getTag());
								}
							})
							.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int id)
								{
									dialog.cancel();
								}
							});

						AlertDialog alert = builder.create();
						alert.show();
					}
					else
					if(tvProsesQC.getText().equals(context.getString(R.string.strTimbangBaru)))
						BayarAtauTimbangBaru(context.getString(R.string.strTimbangBaru), intPekerjaanID, intTag);
				break;
				case R.id.ivPrintTimbang:
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder
						.setTitle(R.string.titleMessege)
						.setMessage(context.getString(R.string.msgPrintAntrian) + " " + intPekerjaanID + " ?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setCancelable(false)
						.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								ProsesPrint(strPemasokID, intPekerjaanID, tvNamaPanggil.getText().toString().trim());
							}
						})
						.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								dialog.cancel();
							}
						});

					AlertDialog alert = builder.create();
					alert.show();
				break;
				case R.id.ivPhotoTimbang:
					Intent PhotoIntent = new Intent(context, Photo.class);
					PhotoIntent.putExtra("PekerjaanID", intPekerjaanID);
					PhotoIntent.putExtra("PemasokID", strPemasokID);
					PhotoIntent.putExtra("KodeWarehouse", Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));
					activity.startActivity(PhotoIntent);

//					MyPhoto myPhoto = new MyPhoto(context, activity, intPekerjaanID, strPemasokID);
//					myPhoto.AmbilPhoto();
				break;
			}
		}
	}

	private void ProsesUpdateQC(final Integer intTag)
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu), context.getResources().getString(R.string.msgUpdateDataQC));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		IsiFormulir isiFormulir = new IsiFormulir();
		isiFormulir.setId(customerRsps.get(intTag).getId());

		if((customerRsps.get(intTag).getPermintaan() == 1) && ((customerRsps.get(intTag).getPekerjaan() == 1) ||
			(customerRsps.get(intTag).getPekerjaan() == 2)))
		{
			isiFormulir.setPermintaan(2);

			if(customerRsps.get(intTag).getPekerjaan() == 1)
				isiFormulir.setPekerjaan(2);
			else
			if(customerRsps.get(intTag).getPekerjaan() == 2)
				isiFormulir.setPekerjaan(1);
		}

		FormulirHolder formulirHolder = new FormulirHolder(isiFormulir, null);
		DataLink dataLink = Fungsi.BindingData();

		final Call<TimbangPojo> ReceivePojo = dataLink.UpdateQCService(formulirHolder);

		ReceivePojo.enqueue(new Callback<TimbangPojo>()
		{
			@Override
			public void onResponse(Call<TimbangPojo> call, Response<TimbangPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);

						if((customerRsps.get(intTag).getPermintaan() == 1) && ((customerRsps.get(intTag).getPekerjaan() == 1) ||
							(customerRsps.get(intTag).getPekerjaan() == 2)))
						{
							customerRsps.get(intTag).setPermintaan(2);

							if(customerRsps.get(intTag).getPekerjaan() == 1)
								customerRsps.get(intTag).setPekerjaan(2);
							else
							if(customerRsps.get(intTag).getPekerjaan() == 2)
								customerRsps.get(intTag).setPekerjaan(1);
						}

						notifyDataSetChanged();
					}
				}
				else
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<TimbangPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}

	private void BayarAtauTimbangBaru(final String strProses, Integer intPekerjaanID, final int intPosView)
	{
		UpdateProses updateProses = new UpdateProses(activity, intPekerjaanID, 3, strProses);

		if(customerRsps.get(intPosView).getJenistimbang() == 3)
			updateProses = new UpdateProses(activity, intPekerjaanID, 5, strProses);

		updateProses.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		updateProses.show();
		updateProses.setOnDismissListener(new DialogInterface.OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialogInterface)
			{
				if(Fungsi.getIntFromSharedPref(context, Preference.prefUpdateProses) == 1)
				{
					if(strProses.matches(context.getString(R.string.strKasirBayar)))
						customerRsps.remove(intPosView);
					else
					if(strProses.matches(context.getString(R.string.strTimbangBaru)))
					{
						customerRsps.get(intPosView).setPermintaan(1);

						if(customerRsps.get(intPosView).getJenistimbang() == 3)
							customerRsps.get(intPosView).setPekerjaan(1);
						else
							customerRsps.get(intPosView).setPekerjaan(2);

						customerRsps.get(intPosView).setJumlahtimbang(customerRsps.get(intPosView).getJumlahtimbang() + 1);
					}

					notifyDataSetChanged();
					Fungsi.storeToSharedPref(context, 0, Preference.prefUpdateProses);
				}
			}
		});
	}

	private void ProsesPrint(final String Pemasokid, final Integer getPekerjaanid, final String NamaPanggil)
	{
		SimpleDateFormat datePrint = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		SimpleDateFormat timePrint = new SimpleDateFormat("HH:mm:ss", Locale.US);

		final String Printer = Fungsi.getStringFromSharedPref(context, Preference.prefPortName);

		final ArrayList<byte[]> list = new ArrayList<>();
		list.clear();

		list.add(new byte[] { 0x1b, 0x1d, 0x61, 0x01 }); // Center in paper
		list.add(new byte[] { 0x1b, 0x1d, 0x74, (byte)0x80 }); // Code Page UTF-8

		// Add BarCode data
		String dtPrint = datePrint.format(calendar.getTime());
		String tmPrint = timePrint.format(calendar.getTime());

		byte[] barCodeData = (Pemasokid + "#" + getPekerjaanid.toString() + "#" + dtPrint + "#" + tmPrint).getBytes();
		byte cellSize = (byte) (8);
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x53, 0x32, cellSize });
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x44, 0x31, 0x00, (byte) (barCodeData.length % 128), (byte) (barCodeData.length / 128) });
		list.add(barCodeData);
		list.add(new byte[] { 0x1b, 0x1d, 0x79, 0x50 });
		list.add("\r\n".getBytes());

		list.add(("#" + Pemasokid + "\r\n").getBytes());
		list.add(("Nama    : " + NamaPanggil + "\r\n").getBytes());
		list.add(("Antrian : " + getPekerjaanid  + "\r\n").getBytes());

		calendar = Calendar.getInstance();
		list.add(("Tanggal : " + dtPrint + "\r\n").getBytes());
		list.add(("Jam : " + tmPrint + "\r\n").getBytes());

		list.add("\r\n\r\n\r\n\r\n".getBytes());
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

				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						Log.d(TAG, "run: " + hasil);
					}
				});
			}
		}).start();
	}
}
