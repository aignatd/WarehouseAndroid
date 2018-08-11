package com.artolanggeng.purnamakertasindo.timbanganKecil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.common.GlobalTimbang;
import com.artolanggeng.purnamakertasindo.service.FragKecilLife;
import com.artolanggeng.purnamakertasindo.service.FragMainLife;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.utils.RoleChecker;
import com.artolanggeng.purnamakertasindo.warehouse.fragArrived;
import com.artolanggeng.purnamakertasindo.warehouse.fragHistory;
import com.artolanggeng.purnamakertasindo.warehouse.fragProses;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class formTimbangKecil extends AppCompatActivity
{
	@BindView(R.id.vpProsesKecil)
	ViewPager vpProsesKecil;
	@BindView(R.id.btnKecil1)
	Button btnKecil1;
	@BindView(R.id.btnKecil2)
	Button btnKecil2;
	@BindView(R.id.tvHeader)
	TextView tvHeader;

	private PopupMessege popupMessege = new PopupMessege();
	private SectionsTimbangKecil swTimbangKecil;
	private Context context = this;
	private Activity activity = this;
	static String TAG = "[Form Timbang Kecil]";
	private MenuBuilder menuBuilder;
	private MenuPopupHelper menuHelper;

	GlobalTimbang globalTimbang;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_timbangkecil);
		ButterKnife.bind(this);

		BindingView();
	}

	public void BindingView()
	{
		tvHeader.setText(getString(R.string.HintTimbangKecil));

		swTimbangKecil = new SectionsTimbangKecil(getSupportFragmentManager());
		vpProsesKecil.setAdapter(swTimbangKecil);
		vpProsesKecil.setCurrentItem(0);

		vpProsesKecil.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrollStateChanged(int state)
			{
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
			{
			}

			@Override
			public void onPageSelected(int position)
			{
				globalTimbang = new GlobalTimbang(context, activity);
				globalTimbang.PageKecilSelected(position, btnKecil1, btnKecil2);

				Log.d(TAG, "onPageSelected: " + position);

				FragKecilLife fragmentToShow = (FragKecilLife) swTimbangKecil.instantiateItem(vpProsesKecil, position);
				fragmentToShow.onResumeFragKecilLife();
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

	@OnClick({R.id.ivBackIcon, R.id.ivPelKecil, R.id.tvPelKecil, R.id.llPelKecil,
						R.id.ivRiKecil, R.id.tvRiKecil, R.id.llRiKecil})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivPelKecil:
			case R.id.tvPelKecil:
			case R.id.llPelKecil:
				vpProsesKecil.setCurrentItem(0);
			break;
			case R.id.ivRiKecil:
			case R.id.tvRiKecil:
			case R.id.llRiKecil:
				vpProsesKecil.setCurrentItem(1);
			break;
			case R.id.ivBackIcon:
				BackActivity();
			break;
		}
	}

	public class SectionsTimbangKecil extends FragmentPagerAdapter
	{
		public SectionsTimbangKecil(FragmentManager fm)
		{
			super(fm);
		}

		// BEGIN_INCLUDE (fragment_pager_adapter_getitem)

		/**
		 * Get fragment corresponding to a specific position. This will be used to populate the
		 * contents of the {@link ViewPager}.
		 *
		 * @param position Position to fetch fragment for.
		 * @return Fragment for specified position.
		 */
		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			FragmentManager fragmentManager = getSupportFragmentManager();
			Fragment fragment = new Fragment();

			switch(position)
			{
				case 0:
					fragment = isFragmentAdded(fragmentManager, Preference.PrefArrKecil);
					if(fragment == null) fragment = new fragmentPelanggan();
				break;
				case 1:
					fragment = isFragmentAdded(fragmentManager, Preference.PrefHisKecil);
					if(fragment == null) fragment = new fragmentRiwayat();
				break;
			}

			return fragment;
		}
		// END_INCLUDE (fragment_pager_adapter_getitem)

		// BEGIN_INCLUDE (fragment_pager_adapter_getcount)

		/**
		 * Get number of pages the {@link ViewPager} should render.
		 *
		 * @return Number of fragments to be rendered as pages.
		 */
		@Override
		public int getCount()
		{
			return 3;
		}
		// END_INCLUDE (fragment_pager_adapter_getcount)

		// BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)

		/**
		 * Get title for each of the pages. This will be displayed on each of the tabs.
		 *
		 * @param position Page to fetch title for.
		 * @return Title for specified page.
		 */
		@Override
		public CharSequence getPageTitle(int position)
		{
			return null;
		}

		// END_INCLUDE (fragment_pager_adapter_getpagetitle)
	}

	private void BackActivity()
	{
		RoleChecker roleChecker = new RoleChecker(formTimbangKecil.this, context);
		if(roleChecker.RoleTimbangan() == 0)
			popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
	}

	private Fragment isFragmentAdded(FragmentManager fragmentManager, String tag)
	{
		Fragment f = fragmentManager.findFragmentByTag(tag);

		if(f == null)
			return null;
		else
			return f;
	}
}
