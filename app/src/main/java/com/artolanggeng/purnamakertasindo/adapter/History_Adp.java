package com.artolanggeng.purnamakertasindo.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.data.IsiPemasok;
import com.artolanggeng.purnamakertasindo.model.CustomerRsp;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.timbangbesar.FormBesar;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 20-Oct-17
 * HP/WA : 0857 7070 6 777
 */
public class History_Adp extends RecyclerView.Adapter<History_Adp.ViewHolder>
{
	private static String TAG = "[History_Adp]";
	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();
	private Context context;
	private List<CustomerRsp> customerRsps;
	private Activity activity;

	public History_Adp(Activity activity, Context context, List<CustomerRsp> customerRsps)
	{
		this.context = context;
		this.customerRsps = customerRsps;
		this.activity = activity;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.lay_listdatahistory, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.tvDetailHistory1.setText(String.valueOf(position + 1) + ". No. Tiket : " + String.valueOf(customerRsps.get(position).getId()) +
			", jumlah timbang : " + String.valueOf(customerRsps.get(position).getJumlahtimbang()));

		holder.tvDetailHistory3.setText("No. Polisi : " + customerRsps.get(position).getNopolisi() +
			", Status : " + customerRsps.get(position).getStatus());

		holder.rlDetailHistory.setTag(position);
	}

	@Override
	public int getItemCount()
	{
		return customerRsps.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder
	{
		@BindView(R.id.tvDetailHistory1)
		TextView tvDetailHistory1;
		@BindView(R.id.tvDetailHistory3)
		TextView tvDetailHistory3;
		@BindView(R.id.rlDetailHistory)
		RelativeLayout rlDetailHistory;

		public ViewHolder(View itemView)
		{
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@OnClick({R.id.ivDetailHistory, R.id.rlDetailHistory})
		public void onViewClicked(View view)
		{
			int intTag = (int) rlDetailHistory.getTag();

			switch(view.getId())
			{
				case R.id.ivDetailHistory:
				case R.id.rlDetailHistory:
					AmbilDetailHistory(customerRsps.get(intTag).getId(), customerRsps.get(intTag).getPemasokid(), intTag);
				break;
			}
		}
	}

	private void AmbilDetailHistory(final int id, final String pemasokid, final int intTag)
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgDetailHistory));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		IsiFormulir isiFormulir = new IsiFormulir();
		isiFormulir.setId(id);
		isiFormulir.setPemasokid(pemasokid);

		FormulirHolder formulirHolder = new FormulirHolder(isiFormulir, null);
		DataLink dataLink = Fungsi.BindingData();

		final Call<CustomerPojo> ReceivePojo = dataLink.DetailHistoryService(formulirHolder);

		ReceivePojo.enqueue(new Callback<CustomerPojo>()
		{
			@Override
			public void onResponse(Call<CustomerPojo> call, Response<CustomerPojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
					{
						IsiPemasok.initIsiPemasok();
						IsiPemasok.getInstance().setCustomerRsp(response.body().getCustomerrsp());

						Intent HistoryIntent = new Intent(activity, FormBesar.class);
						HistoryIntent.putExtra("KodePemasok", pemasokid);
						HistoryIntent.putExtra("Timbang", customerRsps.get(intTag).getJumlahtimbang());

						if(customerRsps.get(intTag).getStatus().matches("Jual"))
							HistoryIntent.putExtra("History", "HistoryJual");
						else
							HistoryIntent.putExtra("History", "History");

						HistoryIntent.putExtra("Jual", "");
						context.startActivity(HistoryIntent);
					}
				}
				else
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<CustomerPojo> call, Throwable t)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
