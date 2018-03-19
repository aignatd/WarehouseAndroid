package com.artolanggeng.purnamakertasindo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.AutoTimbang;
import com.artolanggeng.purnamakertasindo.data.IsiPotongan;
import com.artolanggeng.purnamakertasindo.data.IsiProduct;
import com.artolanggeng.purnamakertasindo.model.JualanRsp;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dibuat oleh : ignat
 * Tanggal : 16-Sep-17
 * HP/WA : 0857 7070 6 777
 */
public class Koreksi_Adp extends RecyclerView.Adapter<Koreksi_Adp.ViewHolder>
{
	private static String TAG = "[Koreksi_Adp]";
	private Context context;
	private Activity activity;

	private List<TimbangRsp> lstTimbang;
	private Integer JenisTimbang;
	private List<ProductRsp> productRsps;
	private List<PotongRsp> potongRsps;
	private ArrayAdapter<String> dataAdapter;

	public Koreksi_Adp(Activity activity, Context context, List<TimbangRsp> lstTimbang, Integer JenisTimbang)
	{
		this.activity = activity;
		this.context = context;
		this.lstTimbang = lstTimbang;
		this.JenisTimbang = JenisTimbang;
		this.productRsps = IsiProduct.getInstance().getmProductRsps();
		this.potongRsps =IsiPotongan.getInstance().getmPotongRsp();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.lay_detailkoreksibarang, parent, false);
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

		if(JenisTimbang == 3)
		{
			holder.tvBeratBruto.setText(context.getString(R.string.titleBeratNetto, String.valueOf(lstTimbang.get(position).getTonasebruto())));
			holder.tvBeratNetto.setText(context.getString(R.string.titleBeratBruto, String.valueOf(lstTimbang.get(position).getTonasenetto())));
		}
		else
		{
			holder.tvBeratBruto.setText(context.getString(R.string.titleBeratBruto, String.valueOf(lstTimbang.get(position).getTonasebruto())));
			holder.tvBeratNetto.setText(context.getString(R.string.titleBeratNetto, String.valueOf(lstTimbang.get(position).getTonasenetto())));
		}

		holder.tvTglTimbang.setText(context.getString(R.string.titleTglBarang, lstTimbang.get(position).getTanggal()));
		holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));
		holder.tvNilaiPotongan.setText(context.getString(R.string.titlePotongan, String.valueOf(lstTimbang.get(position).getPotongan())));
//		holder.tvJenisPotong.setText(lstTimbang.get(position).getJenispotongid());

		String[] items;
		ArrayList<String> lst;

		items = new String[potongRsps.size()];

		for(int i = 0; i < potongRsps.size(); i++)
		{
			items[i] = potongRsps.get(i).getDisplay().trim() + "      ";
		}

		lst = new ArrayList<>(Arrays.asList(items));
		dataAdapter = new ArrayAdapter<>(activity, R.layout.spselected, lst);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.spPotongKoreksi.setAdapter(dataAdapter);

		items = new String[productRsps.size()];

		for(int i = 0; i < productRsps.size(); i++)
		{
			String strProductName;

			if(productRsps.get(i).getProductname() == null)
				strProductName = "";
			else
				strProductName = " / " + productRsps.get(i).getProductname().trim();

			items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + strProductName;
		}

		lst = new ArrayList<>(Arrays.asList(items));
		dataAdapter = new ArrayAdapter<>(activity, R.layout.spselected, lst);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.spKodeBarangKoreksi.setAdapter(dataAdapter);
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
		@BindView(R.id.tvKodeBarang)
		TextView tvKodeBarang;
		@BindView(R.id.tvBeratBruto)
		TextView tvBeratBruto;
		@BindView(R.id.tvBeratNetto)
		TextView tvBeratNetto;
		@BindView(R.id.tvJenisPotong)
		TextView tvJenisPotong;
		@BindView(R.id.tvNilaiPotongan)
		TextView tvNilaiPotongan;
		@BindView(R.id.spPotongKoreksi)
		Spinner spPotongKoreksi;
		@BindView(R.id.spKodeBarangKoreksi)
		Spinner spKodeBarangKoreksi;

		public ViewHolder(View itemView)
		{
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}
}
