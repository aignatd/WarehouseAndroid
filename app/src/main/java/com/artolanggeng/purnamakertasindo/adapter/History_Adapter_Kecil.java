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

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.data.IsiPemasok;
import com.artolanggeng.purnamakertasindo.model.CustomerRsp;
import com.artolanggeng.purnamakertasindo.pojo.CustomerPojo;
import com.artolanggeng.purnamakertasindo.sending.FormulirHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.timbanganKecil.formKecil;
import com.artolanggeng.purnamakertasindo.timbangbesar.FormBesar;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Heru Permana on 11/14/2017.
 */

public class History_Adapter_Kecil extends RecyclerView.Adapter<History_Adapter_Kecil.ViewHolder> {
    private static String TAG = "[History_Adapter_Kecil]";
    private ProgressDialog progressDialog;
    private PopupMessege popupMessege = new PopupMessege();
    private Context context;
    private List<CustomerRsp> customerRsps;
    private Activity activity;

    public History_Adapter_Kecil(Activity activity, Context context, List<CustomerRsp> customerRsps)
    {
        this.context = context;
        this.customerRsps = customerRsps;
        this.activity = activity;
    }

    @Override
    public History_Adapter_Kecil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.riwayat_item, parent, false);
        return new History_Adapter_Kecil.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(History_Adapter_Kecil.ViewHolder holder, int position)
    {
        String id = String.valueOf(customerRsps.get(position).getId());
        String jumTimbangan = String.valueOf(customerRsps.get(position).getJumlahtimbang());
        holder.tvNomorTiketKecil.setText(id);
        holder.tvNomorPolisiKecil.setText(customerRsps.get(position).getNopolisi());
        holder.tvOutputJumlahTimbangan.setText(": "+jumTimbangan);
        holder.tvOutputStatus.setText(": "+customerRsps.get(position).getStatus());
        holder.llRiwyatItem.setTag(position);
    }

    @Override
    public int getItemCount()
    {
        return customerRsps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tvNomorTiketKecil)
        TextView tvNomorTiketKecil;
        @BindView(R.id.tvNomorPolisiKecil)
        TextView tvNomorPolisiKecil;
        @BindView(R.id.tvOutputJumlahTimbangan)
        TextView tvOutputJumlahTimbangan;
        @BindView(R.id.tvOutputStatus)
        TextView tvOutputStatus;
        @BindView(R.id.llRiwyatItem)
        RelativeLayout llRiwyatItem;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.llRiwyatItem})
        public void onViewClicked(View view)
        {
            int intTag = (int) llRiwyatItem.getTag();

            switch(view.getId())
            {

                case R.id.llRiwyatItem:
                    AmbilDetailHistory(customerRsps.get(intTag).getId(), customerRsps.get(intTag).getPemasokid(), intTag);
                    break;
            }
        }
    }

    private void AmbilDetailHistory(final int id, final String pemasokid, final int intTag)
    {
//        progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
//                context.getResources().getString(R.string.msgDetailHistory));
//        progressDialog.setCancelable(false);

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
//                progressDialog.dismiss();

                if(response.isSuccessful())
                {
                    if(response.body().getCoreResponse().getKode() == FixValue.intError)
                        popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
                    else
                    {
                        IsiPemasok.initIsiPemasok();
                        IsiPemasok.getInstance().setCustomerRsp(response.body().getCustomerrsp());

                        Intent HistoryIntent = new Intent(activity, formKecil.class);
                        HistoryIntent.putExtra("KodePemasok", pemasokid);
                        HistoryIntent.putExtra("Timbang", customerRsps.get(intTag).getJumlahtimbang());
                        HistoryIntent.putExtra("History", "History");
                        HistoryIntent.putExtra("intentJump", "fragmentRiwayat");
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
