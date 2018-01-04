package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class InputPemasok extends Dialog
{
	@BindView(R.id.etInputPemasok)
	EditText etInputPemasok;

	private String TAG = "[Password]";
	private Context context = getContext();
	private List<EditText> lstInput = new ArrayList<>();
	private List<String> lstMsg = new ArrayList<>();

	public Activity ParentAct;

	public InputPemasok(Activity parentAct)
	{
		super(parentAct);
		this.ParentAct = parentAct;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lay_inputpemasok);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.btnKirimPemasok, R.id.btnBatalPemasok})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.btnBatalPemasok:
				Fungsi.storeToSharedPref(context, 0, Preference.PrefInputNomorPelanggan);
				cancel();
			break;
			case R.id.btnKirimPemasok:
				lstInput.clear();
				lstMsg.clear();
				lstInput.add(etInputPemasok);
				lstMsg.add(ParentAct.getResources().getString(R.string.msgKodePemasok));

				if(Fungsi.CekInput(lstInput, lstMsg, getContext()))
				{
					Fungsi.storeToSharedPref(context, 1, Preference.PrefInputNomorPelanggan);
					Fungsi.storeToSharedPref(context, etInputPemasok.getText().toString(), Preference.PrefScanQR);
					dismiss();
				}
			break;
		}
	}
}

