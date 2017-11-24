package com.artolanggeng.purnamakertasindo.timbanganKecil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artolanggeng.purnamakertasindo.R;

/**
 * Created by Heru Permana on 10/25/2017.
 */

public class fragmentSemua extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_default, container, false);
    }
}
