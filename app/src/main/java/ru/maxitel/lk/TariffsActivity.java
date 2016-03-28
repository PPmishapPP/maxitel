package ru.maxitel.lk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.maxitel.lk.fragments.TariffFragment;

public class TariffsActivity extends AppCompatActivity implements TariffFragment.OnFragmentInteractionListener {

    private static float dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariffs);
        dp =  getApplicationContext().getResources().getDisplayMetrics().density;

        ViewPager viewPager = (ViewPager)findViewById(R.id.tariff_viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tariff_tabLayout);
        setSupportActionBar((Toolbar)findViewById(R.id.tariff_toolbar));


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        setupViewPager(viewPager,adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
    }


    private void setupViewPager(ViewPager viewPager,ViewPagerAdapter adapter){
        adapter.add(TariffFragment.newInstance("Солнечный"), Tariffs.getTarif("Солнечный"));
        adapter.add(TariffFragment.newInstance("Сатурн"), Tariffs.getTarif("Сатурн"));
        adapter.add(TariffFragment.newInstance("Нептун"), Tariffs.getTarif("Нептун"));
        adapter.add(TariffFragment.newInstance("Уран"), Tariffs.getTarif("Уран"));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<Tariff> tariffsList = new ArrayList<>();
        private Context context;

        public ViewPagerAdapter(FragmentManager manager, Context context) {
            super(manager);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void add(Fragment fragment, Tariff tariff) {
            mFragmentList.add(fragment);
            tariffsList.add(tariff);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tariffsList.get(position).getName();
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);


            TextView tv = (TextView) v.findViewById(R.id.tabTextView);
            tv.setPadding((int) (10 * dp), 0, (int) (10 * dp), 0);
            tv.setText(tariffsList.get(position).getName());

            return v;
        }
    }
}
