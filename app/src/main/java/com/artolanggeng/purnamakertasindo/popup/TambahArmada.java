package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class TambahArmada extends Dialog
{

	@BindView(R.id.etTambaharmada)
	EditText etTambaharmada;
	private String TAG = "[Password]";
	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();
	private List<EditText> lstInput = new ArrayList<>();
	private List<String> lstMsg = new ArrayList<>();
	private Context context = getContext();

	public Activity ParentAct;

	public TambahArmada(Activity parentAct)
	{
		super(parentAct);
		this.ParentAct = parentAct;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lay_tambaharmada);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.btnTambahArmada, R.id.btnBatalArmada})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.btnBatalArmada:
				cancel();
			break;
			case R.id.btnTambahArmada:
				Fungsi.storeToSharedPref(context, etTambaharmada.getText().toString(), Preference.PrefListArmada);
				dismiss();
			break;
		}
	}
}

