package com.xelitexirish.scammerbingo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;
import com.xelitexirish.scammerbingo.utils.DataHelper;

public class SearchActivity extends BaseThemedActivity {

    private ImageButton mClear, mBack;
    private TabLayout mTabLayout;
    private TextView mSearchView;
    private ViewPager mViewPager;

    private final String NUMBERS_TAG = "numbers";
    private final String WEBSITES_TAG = "websites";
    private final String IP_TAG = "ipAddresses";

    private final String NUMBERS_TITLE = "Numbers";
    private final String WEBSITES_TITLE = "Websites";
    private final String IP_TITLE = "IPs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!ATE.config(this, "light_theme").isConfigured(4)) {
            ATE.config(this, "light_theme")
                    .activityTheme(R.style.AppTheme)
                    .primaryColorRes(R.color.colorPrimaryLightDefault)
                    .accentColorRes(R.color.colorAccentLightDefault)
                    .coloredNavigationBar(false)
                    .navigationViewSelectedIconRes(R.color.colorAccentLightDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentLightDefault)
                    .commit();
        }
        if (!ATE.config(this, "dark_theme").isConfigured(4)) {
            ATE.config(this, "dark_theme")
                    .activityTheme(R.style.AppThemeDark)
                    .primaryColorRes(R.color.colorPrimaryDarkDefault)
                    .accentColorRes(R.color.colorAccentDarkDefault)
                    .coloredNavigationBar(true)
                    .navigationViewSelectedIconRes(R.color.colorAccentDarkDefault)
                    .navigationViewSelectedTextRes(R.color.colorAccentDarkDefault)
                    .commit();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (TextView) findViewById(R.id.edit_text_search_legacy);

        mClear = (ImageButton) findViewById(R.id.clear_search_legacy);
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchView.getText().toString().length() == 0) {
                } else {
                    mSearchView.setText("");
                    ((InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        mBack = (ImageButton) findViewById(R.id.back_search_legacy);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.viewpager_search_legacy);
        mTabLayout = (TabLayout) findViewById(R.id.search_tabs_legacy);

        mTabLayout.addTab(mTabLayout.newTab().setTag(NUMBERS_TAG).setText(NUMBERS_TITLE));
        mTabLayout.addTab(mTabLayout.newTab().setTag(WEBSITES_TAG).setText(WEBSITES_TITLE));
        mTabLayout.addTab(mTabLayout.newTab().setTag(IP_TAG).setText(IP_TITLE));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final PageAdapterLegacy pageAdapter = new PageAdapterLegacy(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        DataHelper.inflateLists();
    }

    public class PageAdapterLegacy extends FragmentPagerAdapter {
        private int numTabs;

        public PageAdapterLegacy(FragmentManager fragmentManager, int tabs) {
            super(fragmentManager);
            numTabs = tabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NumbersTabLegacy.newInstance();
                case 1:
                    return WebsitesTabLegacy.newInstance();
                case 2:
                    return IpTabLagacy.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numTabs;
        }
    }

    public static class NumbersTabLegacy extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.scammer_listview_legacy, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, DataHelper.numbersList);
            this.listViewScammers.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

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

        public static NumbersTabLegacy newInstance() {
            NumbersTabLegacy numbersTab = new NumbersTabLegacy();
            return numbersTab;
        }
    }

    public static class WebsitesTabLegacy extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.scammer_listview_legacy, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            this.listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, DataHelper.websitesList);
            this.listViewScammers.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

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

        public static WebsitesTabLegacy newInstance() {
            WebsitesTabLegacy websitesTab = new WebsitesTabLegacy();
            return websitesTab;
        }
    }

    public static class IpTabLagacy extends Fragment {

        ListView listViewScammers;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.scammer_listview_legacy, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, DataHelper.ipsList);
            listViewScammers.setAdapter(arrayAdapter);
        }

        public static IpTabLagacy newInstance() {
            IpTabLagacy ipTab = new IpTabLagacy();
            return ipTab;
        }
    }
}