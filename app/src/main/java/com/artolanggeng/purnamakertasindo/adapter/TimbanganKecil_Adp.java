package com.artolanggeng.purnamakertasindo.adapter;

/**
 * Created by Heru Permana on 10/26/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.model.ProductRsp;
import com.artolanggeng.purnamakertasindo.model.TimbangRsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;


public class TimbanganKecil_Adp extends RecyclerView.Adapter<TimbanganKecil_Adp.ViewHolder> {
    private static String TAG = "[TimbangKecil_Adp]";
    private Context context;
    private Activity activity;

    private ArrayAdapter<String> dataAdapter;
    private List<TimbangRsp> lstTimbang;
    private List<ProductRsp> productRsps;
    private Integer FormAsal;
    private String strProses;

    public TimbanganKecil_Adp(Activity activity, Context context, List<TimbangRsp> lstTimbang,
                              List<ProductRsp> productRsps, Integer FormAsal) {
        this.activity = activity;
        this.context = context;
        this.lstTimbang = lstTimbang;
        this.productRsps = productRsps;
        this.FormAsal = FormAsal;
        this.strProses = strProses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lay_detailbarangkecil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String[] items = new String[productRsps.size()];
        for (int i = 0; i < productRsps.size(); i++) {
            items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + " / " + productRsps.get(i).getProductname().trim();
        }
        ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
        dataAdapter = new ArrayAdapter<>(activity, R.layout.spiner_text, lst);
        dataAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
//        holder.spJenisBarang.setAdapter(dataAdapter);

//        holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
//        holder.ivNoTimbang.setTag(position);

//        Log.d(TAG, "onBindViewHolder: " + lstTimbang.size());
//        if ((lstTimbang.size() - 1) == position) {
//            if (FormAsal == 1) {
//                holder.tvKodeBarang.setVisibility(View.GONE);
//                holder.rlKodeBarang.setVisibility(View.VISIBLE);
//                holder.llNilaiPotongan.setVisibility(View.GONE);
//
//                String[] items = new String[productRsps.size()];
//
//                for (int i = 0; i < productRsps.size(); i++) {
//                    items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().trim() + " / " + productRsps.get(i).getProductname().trim();
//                }
//
//                ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
//                dataAdapter = new ArrayAdapter<>(activity, R.layout.spiner_text, lst);
//                dataAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
//                holder.spKodeBarang.setAdapter(dataAdapter);
//            } else if ((FormAsal == 2) || (FormAsal == 3)) {
//                holder.ivBeratBruto.setVisibility(View.GONE);
//                holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
//                holder.etBeratBruto.setFocusable(false);
//                holder.etBeratBruto.setEnabled(false);
//                holder.etBeratBruto.setCursorVisible(false);
//                holder.etBeratBruto.setKeyListener(null);
//                holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);
//
//                holder.tvKodeBarang.setVisibility(View.VISIBLE);
//                holder.rlKodeBarang.setVisibility(View.GONE);
//                holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));
//
//                if (FormAsal == 2) {
//                    holder.llNilaiPotongan.setVisibility(View.VISIBLE);
//                    holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());
//                } else if (FormAsal == 3) {
//                    holder.llNilaiPotongan.setVisibility(View.VISIBLE);
//                    holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());
//                    holder.etNilaiPotongan.setFocusable(false);
//                    holder.etNilaiPotongan.setEnabled(false);
//                    holder.etNilaiPotongan.setCursorVisible(false);
//                    holder.etNilaiPotongan.setKeyListener(null);
//                    holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);
//
//                    holder.llBeratNetto.setVisibility(View.VISIBLE);
//                }
//            }
//        } else {
//            holder.ivBeratBruto.setVisibility(View.GONE);
//
//            holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
//            holder.etBeratBruto.setFocusable(false);
//            holder.etBeratBruto.setEnabled(false);
//            holder.etBeratBruto.setCursorVisible(false);
//            holder.etBeratBruto.setKeyListener(null);
//            holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);
//
//            holder.llNilaiPotongan.setVisibility(View.VISIBLE);
//            holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());
//            holder.etNilaiPotongan.setFocusable(false);
//            holder.etNilaiPotongan.setEnabled(false);
//            holder.etNilaiPotongan.setCursorVisible(false);
//            holder.etNilaiPotongan.setKeyListener(null);
//            holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);
//
//            holder.tvKodeBarang.setVisibility(View.VISIBLE);
//            holder.rlKodeBarang.setVisibility(View.GONE);
//
//            holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
//            holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));
//
//            holder.llBeratNetto.setVisibility(View.VISIBLE);
//            holder.etBeratNetto.setText(context.getString(R.string.titleBeratNetto, lstTimbang.get(position).getTonasenetto().toString()));
//            holder.etBeratNetto.setFocusable(false);
//            holder.etBeratNetto.setEnabled(false);
//            holder.etBeratNetto.setCursorVisible(false);
//            holder.etBeratNetto.setKeyListener(null);
//            holder.etBeratNetto.setBackgroundColor(Color.TRANSPARENT);
//
//        }

/*
            if(FormAsal > 1)
			{
				holder.llNilaiPotongan.setVisibility(View.VISIBLE);
				holder.etNilaiPotongan.setText(lstTimbang.get(position).getPotongan().toString());

				if(((lstTimbang.size() - 1) != position) || (FormAsal == 3))
				{
					holder.etNilaiPotongan.setFocusable(false);
					holder.etNilaiPotongan.setEnabled(false);
					holder.etNilaiPotongan.setCursorVisible(false);
					holder.etNilaiPotongan.setKeyListener(null);
					holder.etNilaiPotongan.setBackgroundColor(Color.TRANSPARENT);

					holder.llBeratNetto.setVisibility(View.VISIBLE);
				}

				holder.tvKodeBarang.setVisibility(View.VISIBLE);
				holder.rlKodeBarang.setVisibility(View.GONE);
				holder.ivBeratBruto.setVisibility(View.GONE);

				holder.etBeratBruto.setText(context.getString(R.string.titleBeratBruto, lstTimbang.get(position).getTonasebruto().toString()));
				holder.etBeratBruto.setFocusable(false);
				holder.etBeratBruto.setEnabled(false);
				holder.etBeratBruto.setCursorVisible(false);
				holder.etBeratBruto.setKeyListener(null);
				holder.etBeratBruto.setBackgroundColor(Color.TRANSPARENT);

				holder.tvTglTimbang.setText(lstTimbang.get(position).getTanggal());
				holder.tvKodeBarang.setText(context.getString(R.string.titleKodeBarang, lstTimbang.get(position).getProductcode()));
			}
*/
    }

/*
        holder.tvJamTimbang.setText(lstTimbang.get(position).getJam());
		holder.tvKodeBarang.setText("Kode " + lstTimbang.get(position).getProductcode());

		if(lstTimbang.get(position).getHarga() != null)
		{
			if(lstTimbang.get(position).getHarga() == 0)
				holder.tvHargaTonase.setText(context.getString(R.string.strRupiah, " " + Fungsi.FormatDesimal(0)));
			else
				holder.tvHargaTonase.setText(context.getString(R.string.strRupiah, " " + Fungsi.FormatDesimal(lstTimbang.get(position).getHarga())));
		} else
			holder.tvHargaTonase.setText(context.getString(R.string.strRupiah, " " + Fungsi.FormatDesimal(0)));

		if(lstTimbang.get(position).getPotongan() != null)
		{
			if(lstTimbang.get(position).getPotongan() == 0)
			{
				holder.tvNilaiPotongan.setText("Potong 0%");
				holder.tvJumPotongan.setText("Rp. 0");
			} else
			{
				holder.tvNilaiPotongan.setText("Potong " + lstTimbang.get(position).getPotongan() + "%");
				holder.tvJumPotongan.setText(context.getString(R.string.strRupiah, " " + Fungsi.FormatDesimal(lstTimbang.get(position).getJumlahpotongan())));
			}
		} else
		{
			holder.tvNilaiPotongan.setText("Potong 0%");
			holder.tvJumPotongan.setText("Rp. 0");
		}
*/


    @Override
    public int getItemCount() {
        return lstTimbang.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.spJenisBarang)
//        Spinner spJenisBarang;
//        @BindView(R.id.etBeratTimbanganKecil)
//        EditText etBeratTimbanganKecil;
//        @BindView(R.id.etPotonganBarangKecil)
//        EditText etPotonganBarangKecil;
//        @BindView(R.id.spPotongan)
//        Spinner spPotongan;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
