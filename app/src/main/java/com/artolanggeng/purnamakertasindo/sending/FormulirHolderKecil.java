package com.artolanggeng.purnamakertasindo.sending;

import com.artolanggeng.purnamakertasindo.data.IsiFormulir;
import com.artolanggeng.purnamakertasindo.model.TimbanganRspKecil;

import java.util.List;

/**
 * Created by Heru Permana on 11/9/2017.
 */

public class FormulirHolderKecil {
    private IsiFormulir DataFormulir;
    private List<TimbanganRspKecil> DataTimbangan;

    public FormulirHolderKecil(IsiFormulir dataFormulir, List<TimbanganRspKecil> dataTimbangan)
    {
        DataFormulir = dataFormulir;
        DataTimbangan = dataTimbangan;
    }
}
