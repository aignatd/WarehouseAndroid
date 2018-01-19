package com.artolanggeng.purnamakertasindo.timbanganKecil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.artolanggeng.purnamakertasindo.R;
import com.artolanggeng.purnamakertasindo.utils.PopupMessege;
import com.artolanggeng.purnamakertasindo.utils.RoleChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Heru Permana on 10/25/2017.
 */

public class formTimbanganKecil extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context = this;
    private PopupMessege popupMessege = new PopupMessege();
    private Integer intTimbang;
    private PopupMessege pesan = new PopupMessege();
    ImageView ivBackFormulirKecilMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_timbangankecil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Timbang Kecil");
        ivBackFormulirKecilMain = (ImageView)findViewById(R.id.ivBackFormulirKecilMain);
        ivBackFormulirKecilMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            BackActivity();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        createViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Pelanggan");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.deliverytrucks, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
//
//        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
//        tabTwo.setText("Semua");
//        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.proses, 0, 0, 0);
//        tabLayout.getTabAt(1).setCustomView(tabTwo);
//
        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Riwayat");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.riwayat, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabThree);
    }
    private void BackActivity() {
        RoleChecker roleChecker = new RoleChecker(formTimbanganKecil.this, context);
        if(roleChecker.RoleTimbangan() == 0)
            popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
    }
    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new fragmentPelanggan(), "Pelanggan");
//        adapter.addFrag(new fragmentSemua(), "Semua");
        adapter.addFrag(new fragmentRiwayat(), "Riwayat");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed()
    {
        RoleChecker roleChecker = new RoleChecker(formTimbanganKecil.this, context);
        if(roleChecker.RoleTimbangan() == 0)
            popupMessege.ShowMessege1(context, context.getResources().getString(R.string.msgOtorisasi));
    }
}
