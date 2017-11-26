package com.artolanggeng.purnamakertasindo.penjualan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.common.GlobalTimbang;
import com.artolanggeng.purnamakertasindo.service.FragJualLife;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.Preference;
import com.artolanggeng.purnamakertasindo.utils.RoleChecker;

public class MainJual extends AppCompatActivity
{
	@BindView(R.id.ivBackIcon)
	ImageView ivBackIcon;
	@BindView(R.id.vpPenjualan)
	ViewPager vpPenjualan;
	@BindView(R.id.tvMenuProses)
	TextView tvMenuProses;
	@BindView(R.id.llMenuProses)
	LinearLayout llMenuProses;
	@BindView(R.id.btnbawah1)
	Button btnbawah1;
	@BindView(R.id.btnbawah2)
	Button btnbawah2;
	@BindView(R.id.btnbawah3)
	Button btnbawah3;
	@BindView(R.id.tvHeader)
	TextView tvHeader;

	private PopupMessege popupMessege = new PopupMessege();
	private SectionsPenjualan swPenjualan;
	private Context context = this;
	private Activity activity = this;
	static String TAG = "[Main Proses]";
	private MenuBuilder menuBuilder;
	private MenuPopupHelper menuHelper;

	GlobalTimbang globalTimbang;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_mainjual);
		ButterKnife.bind(this);

		BindingView();
	}

	public void BindingView()
	{
		tvHeader.setText(getString(R.string.titlePenjualan));

		swPenjualan = new SectionsPenjualan(getSupportFragmentManager());
		vpPenjualan.setAdapter(swPenjualan);
		vpPenjualan.setCurrentItem(0);

		vpPenjualan.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
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
				globalTimbang.ProsesPageSelected(position, btnbawah1, btnbawah2, btnbawah3);

				Log.d(TAG, "onPageSelected: " + position);
				FragJualLife fragmentToShow = (FragJualLife) swPenjualan.instantiateItem(vpPenjualan, position);
				fragmentToShow.onResumeJualLife();
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

	@OnClick({R.id.ivBackIcon})
	public void onViewClicked(View view)
	{
		switch(view.getId())
		{
			case R.id.ivBackIcon:
				BackActivity();
			break;
		}
	}

	public class SectionsPenjualan extends FragmentPagerAdapter
	{
		public SectionsPenjualan(FragmentManager fm)
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
					fragment = isFragmentAdded(fragmentManager, Preference.PrefQRJual);
					if(fragment == null) fragment = new fragQRJual();
				break;
				case 1:
					fragment = isFragmentAdded(fragmentManager, Preference.PrefProsesJual);
					if(fragment == null) fragment = new fragProsesJual();
				break;
				case 2:
					fragment = isFragmentAdded(fragmentManager, Preference.PrefHistoryJual);
					if(fragment == null) fragment = new fragHistoryJual();
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
		RoleChecker roleChecker = new RoleChecker(MainJual.this, context);
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
