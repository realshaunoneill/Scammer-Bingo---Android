package com.xelitexirish.scammerbingo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DialogScammerList extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout tabLayout;

    public final String NUMBERS_TAG = "numbers";
    public final String WEBSITES_TAG = "websites";
    public final String IP_TAG = "ipAddresses";

    public final String NUMBERS_TITLE = "Numbers";
    public final String WEBSITES_TITLE = "Websites";
    public final String IP_TITLE = "IPs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_scammer_list);

        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setTag(NUMBERS_TAG).setText(NUMBERS_TITLE));
        tabLayout.addTab(tabLayout.newTab().setTag(WEBSITES_TAG).setText(WEBSITES_TITLE));
        tabLayout.addTab(tabLayout.newTab().setTag(IP_TAG).setText(IP_TITLE));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                setTabIndicatorColour(tabLayout);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        setTabIndicatorColour(tabLayout);
    }

    public void setTabIndicatorColour(TabLayout tabLayout) {
        if (tabLayout.getSelectedTabPosition() == 0) {
            tabLayout.setSelectedTabIndicatorColor(Color.RED);
        } else if (tabLayout.getSelectedTabPosition() == 1) {
            tabLayout.setSelectedTabIndicatorColor(Color.GREEN);
        } else if (tabLayout.getSelectedTabPosition() == 2) {
            tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
        }
    }

    public class PageAdapter extends FragmentPagerAdapter {
        private int numTabs;

        public PageAdapter(FragmentManager fragmentManager, int tabs) {
            super(fragmentManager);
            numTabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NumbersTab.newInstance();
                case 1:
                    return WebsitesTab.newInstance();
                case 2:
                    return IpTab.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numTabs;
        }
    }

    public static class NumbersTab extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, DataHelper.numbersList);
            this.listViewScammers.setAdapter(arrayAdapter);
        }

        public static NumbersTab newInstance() {
            NumbersTab numbersTab = new NumbersTab();
            return numbersTab;
        }
    }

    public static class WebsitesTab extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, DataHelper.websitesList);
            this.listViewScammers.setAdapter(arrayAdapter);
        }

        public static WebsitesTab newInstance() {
            WebsitesTab websitesTab = new WebsitesTab();
            return websitesTab;
        }
    }

    public static class IpTab extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, DataHelper.ipsList);
            this.listViewScammers.setAdapter(arrayAdapter);
        }

        public static IpTab newInstance() {
            IpTab ipTab = new IpTab();
            return ipTab;
        }
    }
}
