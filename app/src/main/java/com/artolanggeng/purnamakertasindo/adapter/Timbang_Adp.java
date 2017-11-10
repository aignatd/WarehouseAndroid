package com.artolanggeng.purnamakertasindo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.model.PotongRsp;
import com.artolanggeng.purnamakertasindo.model.ProductRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;

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

	private ArrayAdapter<String> dataAdapter;
	private List<TimbangRsp> lstTimbang;
	private List<ProductRsp> productRsps;
	private List<PotongRsp> potongRsps;
	private Integer FormAsal;

	public Timbang_Adp(Activity activity, Context context, List<TimbangRsp> lstTimbang,
	                   List<ProductRsp> productRsps, Integer FormAsal, List<PotongRsp> potongRsps)
	{
		this.activity = activity;
		this.context = context;
		this.lstTimbang = lstTimbang;
		this.productRsps = productRsps;
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

		if(((lstTimbang.size() - 1) == position) && (FormAsal != 4))
		{
			if(FormAsal == 1)
			{
				holder.tvKodeBarang.setVisibility(View.GONE);
				holder.rlKodeBarang.setVisibility(View.GONE);
				holder.llNilaiPotongan.setVisibility(View.GONE);
			}
			else
			if((FormAsal == 2) || (FormAsal == 3))
			{
				holder.ivBeratBruto.setVisibility(View.GONE);
				holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
				holder.etBeratBruto.setFocusable(false);
				holder.etBeratBruto.setEnabled(false);
				holder.etBeratBruto.setCursorVisible(false);
				holder.etBeratBruto.setKeyListener(null);
				holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);

				holder.tvKodeBarang.setVisibility(View.VISIBLE);
				holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));

				if(FormAsal == 2)
				{
					holder.rlKodeBarang.setVisibility(View.VISIBLE);
					holder.tvKodeBarang.setVisibility(View.GONE);
					holder.tvJenisPotong.setVisibility(View.GONE);
					holder.rlJenisPotong.setVisibility(View.VISIBLE);

					holder.llNilaiPotongan.setVisibility(View.VISIBLE);
					holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());

					String[] items = new String[productRsps.size()];

					for(int i = 0; i < productRsps.size(); i++)
					{
						items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + " / " + productRsps.get(i).getProductname().trim();
					}

					ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
					dataAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, lst);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					holder.spKodeBarang.setAdapter(dataAdapter);

					items = new String[potongRsps.size()];

					for(int i = 0; i < potongRsps.size(); i++)
					{
						items[i] = potongRsps.get(i).getDisplay().trim() + "      ";
					}

					lst = new ArrayList<>(Arrays.asList(items));
					dataAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, lst);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					holder.spJenisPotong.setAdapter(dataAdapter);
				}
				else
				if(FormAsal == 3)
				{
					holder.llNilaiPotongan.setVisibility(View.VISIBLE);
					holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString() + lstTimbang.get(position).getDisplay());
					holder.etNilaiPotongan.setFocusable(false);
					holder.etNilaiPotongan.setEnabled(false);
					holder.etNilaiPotongan.setCursorVisible(false);
					holder.etNilaiPotongan.setKeyListener(null);
					holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);

					holder.llBeratNetto.setVisibility(View.VISIBLE);
				}
			}
		}
		else
		{
			holder.ivBeratBruto.setVisibility(View.GONE);

			holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
			holder.etBeratBruto.setFocusable(false);
			holder.etBeratBruto.setEnabled(false);
			holder.etBeratBruto.setCursorVisible(false);
			holder.etBeratBruto.setKeyListener(null);
			holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);

			holder.llNilaiPotongan.setVisibility(View.VISIBLE);
			holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString() + lstTimbang.get(position).getDisplay());
			holder.etNilaiPotongan.setFocusable(false);
			holder.etNilaiPotongan.setEnabled(false);
			holder.etNilaiPotongan.setCursorVisible(false);
			holder.etNilaiPotongan.setKeyListener(null);
			holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);

			holder.tvKodeBarang.setVisibility(View.VISIBLE);
			holder.rlKodeBarang.setVisibility(View.GONE);

			holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
			holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));

			holder.llBeratNetto.setVisibility(View.VISIBLE);
			holder.etBeratNetto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));
			holder.etBeratNetto.setFocusable(false);
			holder.etBeratNetto.setEnabled(false);
			holder.etBeratNetto.setCursorVisible(false);
			holder.etBeratNetto.setKeyListener(null);
			holder.etBeratNetto.setBackgroundColor(Color.TRANSPARENT);
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

		public ViewHolder(View itemView)
		{
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
