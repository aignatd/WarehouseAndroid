package com.artolanggeng.purnamakertasindo.warehouse;


import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.artolanggeng.purnamakertasindo.adapter.History_Adp;
import com.artolanggeng.purnamakertasindo.data.User;
import com.artolanggeng.purnamakertasindo.pojo.ProsesPojo;
import com.artolanggeng.purnamakertasindo.sending.LoginHolder;
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
 * A simple {@link Fragment} subclass.
 */
public class fragHistory extends Fragment implements FragMainLife
{
	@BindView(R.id.rvFragHistory)
	RecyclerView rvFragHistory;
	Unbinder unbinder;

	public fragHistory()
	{
		// Required empty public constructor
	}

	static String TAG = "[fragHistory]";
	private PopupMessege popupMessege = new PopupMessege();
	static ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.lay_fraghistory, container, false);
		unbinder = ButterKnife.bind(this, root);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		rvFragHistory.setLayoutManager(layoutManager);
		return root;
	}

	@Override
	public void onResumeFragMainLife()
	{
		AmbilDataHistory();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		unbinder.unbind();
	}

	private void AmbilDataHistory()
	{
/*
		progressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.msgHarapTunggu),
			getContext().getResources().getString(R.string.msgDataHistory));
		progressDialog.setCancelable(false);
*/
		if(Fungsi.isNetworkAvailable(getContext()) == FixValue.TYPE_NONE)
		{
//			progressDialog.dismiss();
			popupMessege.ShowMessege1(getContext(), getContext().getResources().getString(R.string.msgKoneksiError));
			return;
		}

		User user = new User();
		user.setUserID(Fungsi.getIntFromSharedPref(getContext(), Preference.prefUserID));

		LoginHolder loginHolder = new LoginHolder(user, null);
		DataLink dataLink = Fungsi.BindingData();

		final Call<ProsesPojo> ReceivePojo = dataLink.DataHistoryService(loginHolder);

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
						rvFragHistory.invalidate();
						History_Adp history_adp = new History_Adp(getActivity(), getContext(), response.body().getCustomerRsps());
						rvFragHistory.setAdapter(history_adp);
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
