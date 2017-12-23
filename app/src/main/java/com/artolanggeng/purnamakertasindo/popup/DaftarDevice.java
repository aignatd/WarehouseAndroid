package com.artolanggeng.purnamakertasindo.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.data.Device;
import com.artolanggeng.purnamakertasindo.model.WarehouseRsp;
import com.artolanggeng.purnamakertasindo.pojo.WarehousePojo;
import com.artolanggeng.purnamakertasindo.sending.PasswordHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class DaftarDevice extends Dialog
{
	@BindView(R.id.spDataWarehouse)
	Spinner spDataWarehouse;
	@BindView(R.id.tvNamaDevice)
	TextView tvNamaDevice;
	@BindView(R.id.tvJenisOS)
	TextView tvJenisOS;
	@BindView(R.id.tvJenisDevice)
	TextView tvJenisDevice;

	private ArrayAdapter<String> dataAdapter;
	private String TAG = "[DaftarDevice]";
	private ProgressDialog progressDialog;
	private PopupMessege popupMessege = new PopupMessege();
	private Context context = getContext();
	private List<WarehouseRsp> warehouseRsps;

	public Activity ParentAct;

	public DaftarDevice(Activity parentAct, List<WarehouseRsp> warehouseRsps)
	{
		super(parentAct);
		this.ParentAct = parentAct;
		this.warehouseRsps = warehouseRsps;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lay_daftardevice);
		ButterKnife.bind(this);

		tvNamaDevice.setText(Fungsi.DeviceName());
		tvJenisDevice.setText(Fungsi.DeviceTipe(context));
		tvJenisOS.setText(Fungsi.AndroidVersion());

		String[] items = new String[warehouseRsps.size()];

		for(int i = 0; i < warehouseRsps.size(); i++)
		{
			items[i] = (i + 1) + ". " + warehouseRsps.get(i).getKode().trim() + " / " + warehouseRsps.get(i).getName().trim();
		}

		ArrayList<String> lst = new ArrayList<>(Arrays.asList(items));
		dataAdapter = new ArrayAdapter<>(ParentAct, android.R.layout.simple_spinner_item, lst);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spDataWarehouse.setAdapter(dataAdapter);
	}

	@OnClick({R.id.btnSubmitDaftar, R.id.btnBatalDaftar})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.btnSubmitDaftar:
				KirimDataDevice();
			break;
			case R.id.btnBatalDaftar:
				dismiss();
			break;
		}
	}

	private void KirimDataDevice()
	{
		progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.msgHarapTunggu),
			context.getResources().getString(R.string.msgDataDevice));
		progressDialog.setCancelable(false);

		if(Fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
		{
			progressDialog.dismiss();
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgKoneksiError));
			return;
		}

		Device device = new Device();
		device.setDeviceid(Fungsi.DeviceInfo(context, 0));
		device.setDevice(tvNamaDevice.getText().toString());
		device.setOs(tvJenisOS.getText().toString());
		device.setTipe(Fungsi.DeviceTipe(context));
		device.setBusinessunit(warehouseRsps.get(spDataWarehouse.getSelectedItemPosition()).getId());

		PasswordHolder passwordHolder = new PasswordHolder(null, device);
		DataLink dataLink = Fungsi.BindingData();
		final Call<WarehousePojo> ReceivePojo = dataLink.DataDeviceService(passwordHolder);

		ReceivePojo.enqueue(new Callback<WarehousePojo>()
		{
			@Override
			public void onResponse(Call<WarehousePojo> call, Response<WarehousePojo> response)
			{
				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if(response.body().getCoreResponse().getKode() == FixValue.intError)
						popupMessege.ShowMessege1(context, response.body().getCoreResponse().getPesan());
					else
						dismiss();
				}
				else
					popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<WarehousePojo> call, Throwable t)
			{
				progressDialog.dismiss();
				popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}

