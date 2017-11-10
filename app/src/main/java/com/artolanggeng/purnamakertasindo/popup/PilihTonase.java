package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.Timbang;
import com.artolanggeng.purnamakertasindo.model.ProductRsp;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class PilihTonase extends Dialog
{
	@BindView(R.id.etTonase)
	EditText etTonase;
	@BindView(R.id.spJenisBarang)
	Spinner spJenisBarang;

	private String TAG = "[PilihTonase]";
	private PopupMessege popupMessege = new PopupMessege();
	private List<EditText> lstInput = new ArrayList<>();
	private List<String> lstMsg = new ArrayList<>();
	private Context context = getContext();

	private SimpleDateFormat df;
	private Calendar calendar;

	private Activity ParentAct;
	private List<ProductRsp> productRsps;
	private ArrayAdapter<String> dataAdapter;

	private Integer nourut;

	public PilihTonase(Activity parentAct, List<ProductRsp> productRsps, Integer nourut)
	{
		super(parentAct);
		this.ParentAct = parentAct;
		this.productRsps = productRsps;
		this.nourut = nourut;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lay_pilihtonase);
		ButterKnife.bind(this);

		calendar = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

		String[] items = new String[productRsps.size()];

		for(int i = 0; i < productRsps.size(); i++)
		{
			items[i] = (i + 1) + ". " + productRsps.get(i).getProductcode().toString().trim() + " / " + productRsps.get(i).getProductname().toString().trim();
		}

		ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
		dataAdapter = new ArrayAdapter<>(ParentAct, android.R.layout.simple_spinner_item, lst);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spJenisBarang.setAdapter(dataAdapter);
	}

	@OnClick({R.id.ivCloseTonase, R.id.ivSaveTonase})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivSaveTonase:
				lstInput.clear();
				lstMsg.clear();
				lstInput.add(etTonase);
				lstMsg.add(context.getResources().getString(R.string.msgIsiTonase));

				if(Fungsi.CekInput(lstInput, lstMsg, context))
				{
					Timbang.initTimbang();
					Timbang.getInstance().setNourut(nourut);
					Timbang.getInstance().setTonasenetto(Integer.valueOf(etTonase.getText().toString()));
					Timbang.getInstance().setTanggal(df.format(calendar. getTime()));
					Timbang.getInstance().setProductcode(productRsps.get(spJenisBarang.getSelectedItemPosition()).getProductcode().trim());
					dismiss();
				}
				break;
			case R.id.ivCloseTonase:
				cancel();
			break;
		}
	}
}

