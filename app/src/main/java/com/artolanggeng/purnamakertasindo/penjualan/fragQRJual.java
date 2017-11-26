package com.artolanggeng.purnamakertasindo.penjualan;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.common.ScanQR;
import com.artolanggeng.purnamakertasindo.popup.InputPemasok;
import com.artolanggeng.purnamakertasindo.service.FragJualLife;
import com.artolanggeng.purnamakertasindo.timbangbesar.FormBesar;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.Preference;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragQRJual extends Fragment implements FragJualLife
{
    Unbinder unbinder;

    public fragQRJual()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.lay_fragarrived, container, false);

        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnScanQR, R.id.ivScanQR, R.id.tvInputPelanggan})
    public void onViewClicked(View view)
    {
        switch(view.getId())
        {
            case R.id.ivScanQR:
            case R.id.btnScanQR:
                Intent ProgresIntent = new Intent(getActivity(), ScanQR.class);
                ProgresIntent.putExtra("KodeTimbangan", "3");
                startActivity(ProgresIntent);
            break;
            case R.id.tvInputPelanggan:
                InputPemasok inputPemasok = new InputPemasok(getActivity());
                inputPemasok.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                inputPemasok.show();
                inputPemasok.setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface)
                    {
                        Intent PemasokManualIntent = new Intent(getActivity(), FormBesar.class);
                        PemasokManualIntent.putExtra("KodePemasok", Fungsi.getStringFromSharedPref(getContext(), Preference.PrefScanQR));
                        PemasokManualIntent.putExtra("Timbang", 0);
                        PemasokManualIntent.putExtra("History", "");
                        getActivity().startActivity(PemasokManualIntent);
                    }
                });
            break;
        }
    }

    @Override
    public void onResumeJualLife()
    {

    }
}
