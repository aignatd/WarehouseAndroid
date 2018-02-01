package com.artolanggeng.purnamakertasindo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.AutoTimbang;
import com.artolanggeng.purnamakertasindo.data.IsiProduct;
import com.artolanggeng.purnamakertasindo.model.PotongRsp;
import com.artolanggeng.purnamakertasindo.model.ProductRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;
import com.artolanggeng.purnamakertasindo.pojo.TimbangPojo;
import com.artolanggeng.purnamakertasindo.sending.AutoTimbangHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 16-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class Timbang_Adp extends RecyclerView.Adapter<Timbang_Adp.ViewHolder>
{
	private static String TAG = "[Timbang_Adp]";
	private Context context;
	private Activity activity;

	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();

	private ArrayAdapter<String> dataAdapter;
	private List<TimbangRsp> lstTimbang;
	private List<ProductRsp> productRsps;
	private List<ProductRsp> jualanRsps;
	private List<PotongRsp> potongRsps;
	private Integer FormAsal;

	public Timbang_Adp(Activity activity, Context context, List<TimbangRsp> lstTimbang,
	                   Integer FormAsal, List<PotongRsp> potongRsps)
	{
		this.activity = activity;
		this.context = context;
		this.lstTimbang = lstTimbang;
		this.productRsps = IsiProduct.getInstance().getmProductRsps();
		this.jualanRsps = IsiProduct.getInstance().getmJualanRsps();
		this.potongRsps = potongRsps;
		this.FormAsal = FormAsal;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.lay_detailberatbarang, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
		holder.ivNoTimbang.setTag(position);

		if(position == 0)
			holder.ivNoTimbang.setBackgroundResource(R.drawable.timbang1);
		else if(position == 1)
			holder.ivNoTimbang.setBackgroundResource(R.drawable.timbang2);
		else if(position == 2)
			holder.ivNoTimbang.setBackgroundResource(R.drawable.timbang3);

		Integer intHistory=0;

		if((FormAsal == 4) || (FormAsal == 8))
			intHistory = 1;

		if(((lstTimbang.size() - 1) == position) && (intHistory == 0))
		{
			if((FormAsal == 1) || (FormAsal == 7))
			{
				holder.tvKodeBarang.setVisibility(View.GONE);
				holder.rlKodeBarang.setVisibility(View.GONE);
				holder.llNilaiPotongan.setVisibility(View.GONE);

				if(FormAsal == 1)
					holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, Fungsi.getStringFromSharedPref(context, Preference.PrefDataTimbang)));
				else
				if(FormAsal == 7)
					holder.etBeratBruto.setText(context.getString(R.string.titleBeratNetto, Fungsi.getStringFromSharedPref(context, Preference.PrefDataTimbang)));

				holder.etBeratBruto.setFocusable(false);
				holder.etBeratBruto.setEnabled(false);
				holder.etBeratBruto.setCursorVisible(false);
				holder.etBeratBruto.setKeyListener(null);
				holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);
			}
			else
			if((FormAsal == 2) || (FormAsal == 3) || (FormAsal == 5) || (FormAsal == 6))
			{
				holder.ivBeratBruto.setVisibility(View.GONE);
				holder.etBeratBruto.setFocusable(false);
				holder.etBeratBruto.setEnabled(false);
				holder.etBeratBruto.setCursorVisible(false);
				holder.etBeratBruto.setKeyListener(null);
				holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);

				holder.tvKodeBarang.setVisibility(View.VISIBLE);
				holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));

				if((FormAsal == 2) || (FormAsal == 6))
				{
					String[] items;
					ArrayList<String> lst;

					holder.rlKodeBarang.setVisibility(View.VISIBLE);
					holder.tvKodeBarang.setVisibility(View.GONE);
					holder.tvJenisPotong.setVisibility(View.GONE);

					if(FormAsal == 6)
					{
						holder.etBeratBruto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));
						holder.llNilaiPotongan.setVisibility(View.GONE);

						items = new String[jualanRsps.size()];

						for(int i = 0; i < jualanRsps.size(); i++)
						{
							items[i] = (i + 1) + ". " + jualanRsps.get(i).getProductcode().trim() + " / " + jualanRsps.get(i).getProductname().trim();
						}
					}
					else
					{
						holder.rlJenisPotong.setVisibility(View.VISIBLE);
						holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
						holder.llNilaiPotongan.setVisibility(View.VISIBLE);
						holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());

						items = new String[potongRsps.size()];

						for(int i = 0; i < potongRsps.size(); i++)
						{
							items[i] = potongRsps.get(i).getDisplay().trim() + "      ";
						}

						lst = new ArrayList<>(Arrays.asList(items));
						dataAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, lst);
						dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						holder.spJenisPotong.setAdapter(dataAdapter);

						items = new String[productRsps.size()];

						for(int i = 0; i < productRsps.size(); i++)
						{
							items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + " / " + productRsps.get(i).getProductname().trim();
						}

						holder.etNilaiPotongan.requestFocus();
					}

					lst = new ArrayList<>(Arrays.asList(items));
					dataAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, lst);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					holder.spKodeBarang.setAdapter(dataAdapter);
				}
				else
				if((FormAsal == 3) || (FormAsal == 5))
				{
					holder.llNilaiPotongan.setVisibility(View.VISIBLE);
					holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString() + lstTimbang.get(position).getDisplay());
					holder.etNilaiPotongan.setFocusable(false);
					holder.etNilaiPotongan.setEnabled(false);
					holder.etNilaiPotongan.setCursorVisible(false);
					holder.etNilaiPotongan.setKeyListener(null);
					holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);

					if(FormAsal == 3)
					{
						holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
						holder.etBeratNetto.setText(context.getString(R.string.titleBeratNetto, Fungsi.getStringFromSharedPref(context, Preference.PrefDataTimbang)));
					}
					else
					if(FormAsal == 5)
					{
						holder.etBeratBruto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));
						holder.etBeratNetto.setText(context.getString(R.string.titleBeratBruto, Fungsi.getStringFromSharedPref(context, Preference.PrefDataTimbang)));
						holder.llNilaiPotongan.setVisibility(View.GONE);
					}

					holder.llBeratNetto.setVisibility(View.VISIBLE);
					holder.etBeratNetto.setFocusable(false);
					holder.etBeratNetto.setEnabled(false);
					holder.etBeratNetto.setCursorVisible(false);
					holder.etBeratNetto.setKeyListener(null);
					holder.etBeratNetto.setBackgroundColor(Color.TRANSPARENT);
					holder.etNilaiPotongan.requestFocus();
				}
			}
		}
		else
		{
			holder.ivBeratBruto.setVisibility(View.GONE);

			if((FormAsal == 5) || (FormAsal == 8))
				holder.etBeratBruto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));
			else
				holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));

			holder.etBeratBruto.setFocusable(false);
			holder.etBeratBruto.setEnabled(false);
			holder.etBeratBruto.setCursorVisible(false);
			holder.etBeratBruto.setKeyListener(null);
			holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);

			Integer intJual=0;

			if((FormAsal == 5) || (FormAsal == 8))
				intJual = 1;

			if(intJual == 0)
			{
				holder.llNilaiPotongan.setVisibility(View.VISIBLE);
				holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString() + lstTimbang.get(position).getDisplay());
				holder.etNilaiPotongan.setFocusable(false);
				holder.etNilaiPotongan.setEnabled(false);
				holder.etNilaiPotongan.setCursorVisible(false);
				holder.etNilaiPotongan.setKeyListener(null);
				holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);
			}

			holder.tvKodeBarang.setVisibility(View.VISIBLE);
			holder.rlKodeBarang.setVisibility(View.GONE);

			holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
			holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));

			holder.llBeratNetto.setVisibility(View.VISIBLE);

			if((FormAsal == 5) || (FormAsal == 8))
				holder.etBeratNetto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
			else
				holder.etBeratNetto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));

			holder.etBeratNetto.setFocusable(false);
			holder.etBeratNetto.setEnabled(false);
			holder.etBeratNetto.setCursorVisible(false);
			holder.etBeratNetto.setKeyListener(null);
			holder.etBeratNetto.setBackgroundColor(Color.TRANSPARENT);

			holder.ivBeratNetto.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return lstTimbang.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder
	{
		@BindView(R.id.ivNoTimbang)
		ImageView ivNoTimbang;
		@BindView(R.id.tvTglTimbang)
		TextView tvTglTimbang;
		@BindView(R.id.spKodeBarang)
		Spinner spKodeBarang;
		@BindView(R.id.tvKodeBarang)
		TextView tvKodeBarang;
		@BindView(R.id.etBeratBruto)
		EditText etBeratBruto;
		@BindView(R.id.ivBeratBruto)
		ImageView ivBeratBruto;
		@BindView(R.id.llNilaiPotongan)
		LinearLayout llNilaiPotongan;
		@BindView(R.id.rlKodeBarang)
		RelativeLayout rlKodeBarang;
		@BindView(R.id.etNilaiPotongan)
		EditText etNilaiPotongan;
		@BindView(R.id.llBeratNetto)
		LinearLayout llBeratNetto;
		@BindView(R.id.etBeratNetto)
		EditText etBeratNetto;
		@BindView(R.id.spJenisPotong)
		Spinner spJenisPotong;
		@BindView(R.id.tvJenisPotong)
		TextView tvJenisPotong;
		@BindView(R.id.rlJenisPotong)
		RelativeLayout rlJenisPotong;
		@BindView(R.id.ivBeratNetto)
		ImageView ivBeratNetto;

		public ViewHolder(View itemView)
		{
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@OnClick({R.id.ivBeratBruto, R.id.ivBeratNetto})
		public void onViewClicked(View view)
		{
			int intTag = (int) ivNoTimbang.getTag();
			int intViewID = R.id.ivBeratBruto;

			switch(view.getId())
			{
				case R.id.ivBeratBruto:
					intViewID = R.id.ivBeratBruto;
				break;
				case R.id.ivBeratNetto:
					intViewID = R.id.ivBeratNetto;
				break;
			}

			AmbilDataTimbangan(intTag, intViewID);
		}

		private void AmbilDataTimbangan(final int intTag, final int viewID)
		{
			progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
				context.getResources().getString(R.string.msgDataTimbang));
			progressDialog.setCancelable(false);

			if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
				return;
			}

			AutoTimbang autoTimbang = new AutoTimbang();
			autoTimbang.setJenisTimbang(2);
			autoTimbang.setWarehouse(Fungsi.getStringFromSharedPref(context, Preference.prefKodeWarehouse));

			String strAutoTimbang = Fungsi.getStringFromSharedPref(context, Preference.PrefUrlTimbang2);

			if(strAutoTimbang.matches(""))
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.strSettingTimbangan));
				return;
			}

			AutoTimbangHolder autoTimbangHolder = new AutoTimbangHolder(autoTimbang);
			DataLink dataLink = Fungsi.BindingTimbangan(strAutoTimbang);

			final Call<TimbangPojo> ReceivePojo = dataLink.AutoTimbangService(autoTimbangHolder);

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
							if(viewID == R.id.ivBeratBruto)
							{
								lstTimbang.get(intTag).setTonasebruto(Integer.valueOf(response.body().getTimbanganRsp().getTimbangan()));

								if((FormAsal == 5) || (FormAsal == 6))
									etBeratBruto.setText(context.getString(R.string.titleBeratNetto, response.body().getTimbanganRsp().getTimbangan()));
								else
									etBeratBruto.setText(context.getString(R.string.titleBeratBruto, response.body().getTimbanganRsp().getTimbangan()));
							}
							else
							if(viewID == R.id.ivBeratNetto)
							{
								lstTimbang.get(intTag).setTonasenetto(Integer.valueOf(response.body().getTimbanganRsp().getTimbangan()));

								if((FormAsal == 5) || (FormAsal == 6))
									etBeratNetto.setText(context.getString(R.string.titleBeratBruto, response.body().getTimbanganRsp().getTimbangan()));
								else
									etBeratNetto.setText(context.getString(R.string.titleBeratNetto, response.body().getTimbanganRsp().getTimbangan()));
							}
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
	}
}
