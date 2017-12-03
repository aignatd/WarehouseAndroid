package com.artolanggeng.purnamakertasindo.timbanganKecil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.History_Adapter_Kecil;
import com.artolanggeng.purnamakertasindo.data.Proses;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.sending.HistoryHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.service.FragMainLife;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Heru Permana on 10/25/2017.
 */

public class fragmentRiwayat extends Fragment implements FragMainLife {
    @BindView(R.id.rvFragHistory)
    RecyclerView rvFragHistory;
    Unbinder unbinder;
    private PopupMessege popupMessege = new PopupMessege();
    static ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_fragment_riwayat_kecil, container, false);
        unbinder = ButterKnife.bind(this, root);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFragHistory.setLayoutManager(layoutManager);
        recieveContent();
        return root;
    }

    @Override
    public void onResumeFragMainLife() {

    }

    private void recieveContent() {
        if (Fungsi.isNetworkAvailable(getContext()) == FixValue.TYPE_NONE) {
            popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgKoneksiError));
            return;
        }

        User user = new User();
        user.setUserID(Fungsi.getIntFromSharedPref(getContext(), Preference.prefUserID));
        int i = Fungsi.getIntFromSharedPref(getContext(), Preference.prefUserID);

//        user.setUserID(3);

        Proses proses = new Proses();
        proses.setJenistimbang(1);

        HistoryHolder historyHolder = new HistoryHolder(user, proses);
        DataLink dataLink = Fungsi.BindingData();

        final Call<ProsesPojo> ReceivePojo = dataLink.DataHistoryService(historyHolder);

        ReceivePojo.enqueue(new Callback<ProsesPojo>() {
            @Override
            public void onResponse(Call<ProsesPojo> call, Response<ProsesPojo> response) {
//				progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if ((response.body().getCoreResponse().getKode() == FixValue.intError) || (response.body().getCoreResponse().getKode() == FixValue.intEmpty))
                        Toast.makeText(getContext(), response.body().getCoreResponse().getPesan(), Toast.LENGTH_SHORT).show();
                    else {
                        rvFragHistory.invalidate();
                        History_Adapter_Kecil history_adp = new History_Adapter_Kecil(getActivity(), getContext(), response.body().getCustomerRsps());
                        rvFragHistory.setAdapter(history_adp);
                    }
                } else
                    popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgServerData));
            }

            @Override
            public void onFailure(Call<ProsesPojo> call, Throwable t) {
//				progressDialog.dismiss();
                popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgServerFailure));
            }
        });
    }
}
