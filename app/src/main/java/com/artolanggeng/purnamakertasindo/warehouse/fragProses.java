package com.artolanggeng.purnamakertasindo.warehouse;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.adapter.Proses_Adp;
import com.artolanggeng.purnamakertasindo.data.Proses;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.sending.ProsesHolder;
import com.artolanggeng.purnamakertasindo.service.DataLink;
import com.artolanggeng.purnamakertasindo.service.FragMainLife;
import com.artolanggeng.purnamakertasindo.utils.FixValue;
import com.artolanggeng.purnamakertasindo.utils.Fungsi;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragProses extends Fragment implements FragMainLife
{
	@BindView(R.id.rvPekerjaan)
	ListView rvPekerjaan;

	static String TAG = "[fragProses]";
	private PopupMessege popupMessege = new PopupMessege();
	static ProgressDialog progressDialog;

	private Unbinder unbinder;
	private TextView tvMenuProses;

	public fragProses()
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.lay_fragproses, container, false);
		// Inflate the layout for this fragment

		unbinder = ButterKnife.bind(this, root);
		return root;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public void onResumeFragMainLife()
	{
		tvMenuProses = (TextView) ((MainProses) getActivity()).findViewById(R.id.tvMenuProses);
		String MenuProses = tvMenuProses.getText().toString().trim();

		if(MenuProses.matches(getResources().getString(R.string.titleSemua)))
			AmbilDataPekerjaan(0, 0);
		else
		if(MenuProses.matches(getResources().getString(R.string.titleTimbang)))
			AmbilDataPekerjaan(2, 1);
		else
		if(MenuProses.matches(getResources().getString(R.string.titleQuality)))
			AmbilDataPekerjaan(1, 2);
	}

	@OnClick(R.id.ivRefreshProses)
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivRefreshProses:
				onResumeFragMainLife();
			break;
		}
	}

	private void AmbilDataPekerjaan(Integer Permintaan, Integer Pekerjaan)
	{
/*
		progressDialog = ProgressDialog.show(getContext(), getContext().getResources().getString(R.string.msgHarapTunggu),
			getContext().getResources().getString(R.string.msgPekerjaan));
		progressDialog.setCancelable(false);
*/
		if(Fungsi.isNetworkAvailable(getContext()) == FixValue.TYPE_NONE)
		{
//			progressDialog.dismiss();
			popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgKoneksiError));
			return;
		}

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

		Proses proses = new Proses();
		proses.setPermintaan(Permintaan);
		proses.setPekerjaan(Pekerjaan);
		proses.setTglRequest(df.format(calendar.getTime()));
//		proses.setBisnisUnit("kbns");
		proses.setUserid(Fungsi.getIntFromSharedPref(getContext(), Preference.prefUserID));
		proses.setBisnisUnit(Fungsi.getStringFromSharedPref(getContext(), Preference.prefKodeWarehouse));

		ProsesHolder prosesHolder = new ProsesHolder(proses);
		DataLink dataLink = Fungsi.BindingData();

		final Call<ProsesPojo> ReceivePojo = dataLink.DataProsesService(prosesHolder);

		ReceivePojo.enqueue(new Callback<ProsesPojo>()
		{
			@Override
			public void onResponse(Call<ProsesPojo> call, Response<ProsesPojo> response)
			{
//				progressDialog.dismiss();

				if(response.isSuccessful())
				{
					if((response.body().getCoreResponse().getKode() == FixValue.intError) || (response.body().getCoreResponse().getKode() == FixValue.intEmpty))
						Toast.makeText(getContext(), response.body().getCoreResponse().getPesan(), Toast.LENGTH_SHORT).show();
					else
					{
						rvPekerjaan.invalidate();
						Proses_Adp adapter = new Proses_Adp(getContext(), response.body().getCustomerRsps(), getActivity());
						rvPekerjaan.setAdapter(adapter);
					}
				}
				else
					popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgServerData));
			}

			@Override
			public void onFailure(Call<ProsesPojo> call, Throwable t)
			{
//				progressDialog.dismiss();
				popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgServerFailure));
			}
		});
	}
}
