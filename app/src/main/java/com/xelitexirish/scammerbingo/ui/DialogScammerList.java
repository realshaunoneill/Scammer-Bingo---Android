package com.xelitexirish.scammerbingo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.util.DataHelper;

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

        TextView textViewHeader;
        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.textViewHeader = (TextView) view.findViewById(R.id.textViewHeader);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            this.textViewHeader.setText("Known phone numbers for scammers.");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.numbersList);
            this.listViewScammers.setAdapter(arrayAdapter);

            this.listViewScammers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String phoneNumber = DataHelper.numbersList.get(position);

                    final AlertDialog.Builder alertDialogConfirm = new AlertDialog.Builder(getContext());
                    alertDialogConfirm.setTitle("Are you sure you want to call this phone number?");
                    alertDialogConfirm.setMessage("This number is known for contacting scammers, are you sure you want to call?");
                    alertDialogConfirm.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                startActivity(intent);
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Unable to call phone number", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                    alertDialogConfirm.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    alertDialogConfirm.show();
                }
            });
        }

        public static NumbersTab newInstance() {
            NumbersTab numbersTab = new NumbersTab();
            return numbersTab;
        }
    }

    public static class WebsitesTab extends Fragment {

        TextView textViewHeader;
        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.textViewHeader = (TextView) view.findViewById(R.id.textViewHeader);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            this.textViewHeader.setText("Known scammer websites, be careful!");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.websitesList);
            this.listViewScammers.setAdapter(arrayAdapter);

            this.listViewScammers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String url = DataHelper.websitesList.get(position);

                    final AlertDialog.Builder alertDialogConfirm = new AlertDialog.Builder(getContext());
                    alertDialogConfirm.setTitle("Are you sure you want to open this URL?");
                    alertDialogConfirm.setMessage("This website is known for advertising scammers, are you sure you want to open it?");
                    alertDialogConfirm.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                                getContext().startActivity(intent);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                getContext().startActivity(intent);
                            }
                        }
                    });
                    alertDialogConfirm.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss
                        }
                    });
                    alertDialogConfirm.show();
                }
            });
        }

        public static WebsitesTab newInstance() {
            WebsitesTab websitesTab = new WebsitesTab();
            return websitesTab;
        }
    }

    public static class IpTab extends Fragment {

        TextView textViewHeader;
        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.textViewHeader = (TextView) view.findViewById(R.id.textViewHeader);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            this.textViewHeader.setText("Know IP address's for scammers.");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.ipsList);
            this.listViewScammers.setAdapter(arrayAdapter);
        }

        public static IpTab newInstance() {
            IpTab ipTab = new IpTab();
            return ipTab;
        }
    }
}
