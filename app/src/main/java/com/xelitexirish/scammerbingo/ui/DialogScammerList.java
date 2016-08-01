package com.xelitexirish.scammerbingo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.util.DataHelper;

public class DialogScammerList extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout tabLayout;
    private Toolbar mToolbar;
    private CoordinatorLayout mCoordinatorLayout;

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

        mToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(mToolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_search);

        new MaterialDialog.Builder(this)
                .title("Warning")
                .content("The information you see here is scammer information such as phone numbers, websites, and IP addresses. Please be careful.")
                .positiveText("I Understand")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Dismiss Dialog
                    }
                })
                .negativeText("Go Back")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Go Back
                        finish();
                    }
                }).show();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setTag(NUMBERS_TAG).setText(NUMBERS_TITLE));
        tabLayout.addTab(tabLayout.newTab().setTag(WEBSITES_TAG).setText(WEBSITES_TITLE));
        tabLayout.addTab(tabLayout.newTab().setTag(IP_TAG).setText(IP_TITLE));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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

        if(!isNetworkConnected()) {
            Snackbar.make(mCoordinatorLayout, "No Internet", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.numbersList);
            this.listViewScammers.setAdapter(arrayAdapter);

            this.listViewScammers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String phoneNumber = DataHelper.numbersList.get(position);

                    final MaterialDialog.Builder alertDialogConfirm = new MaterialDialog.Builder(getContext());
                    alertDialogConfirm.title("Are you sure you want to call this phone number?");
                    alertDialogConfirm.content("This number is known for contacting scammers, are you sure you want to call?");
                    alertDialogConfirm.positiveText("Call");
                    alertDialogConfirm.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Unable to call phone number", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                    alertDialogConfirm.negativeText("Dismiss");
                    alertDialogConfirm.onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.websitesList);
            this.listViewScammers.setAdapter(arrayAdapter);

            this.listViewScammers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String url = DataHelper.websitesList.get(position);

                    final MaterialDialog.Builder alertDialogConfirm = new MaterialDialog.Builder(getContext());
                    alertDialogConfirm.title("Are you sure you want to open this URL?");
                    alertDialogConfirm.content("This website is known for advertising scammers, are you sure you want to open it?");
                    alertDialogConfirm.positiveText("Open");
                    alertDialogConfirm.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                                getContext().startActivity(intent);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                getContext().startActivity(intent);
                            }
                        }
                    });
                    alertDialogConfirm.negativeText("Dismiss");
                    alertDialogConfirm.onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_scammer_list_view, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_list_bullet, DataHelper.ipsList);
            listViewScammers.setAdapter(arrayAdapter);
        }

        public static IpTab newInstance() {
            IpTab ipTab = new IpTab();
            return ipTab;
        }
    }
}
