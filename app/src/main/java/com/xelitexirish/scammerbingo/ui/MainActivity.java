package com.xelitexirish.scammerbingo.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;
import com.xelitexirish.scammerbingo.utils.DataHelper;
import com.xelitexirish.scammerbingo.utils.InitiateSearch;
import com.xelitexirish.scammerbingo.utils.IntroManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends BaseThemedActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CardView mSearchCardView;
    private DrawerLayout mDrawerLayout;
    private EditText mSearchText;
    private ImageButton mSearchBack, mSearchClear;
    private ImageView mRandPeripheral;
    private IntroManager mIntroManager;
    private NavigationView mNavigationView;
    private RelativeLayout mSearchContainer;
    private TabLayout mSearchTabs;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    Button button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9,
            button10,
            button11,
            button12,
            button13,
            button14,
            button15,
            button16,
            button17,
            button18,
            button19,
            button20;

    public static int score = 0;
    public static Button[] allButtons;
    private static int[] randPeripherals;
    private static String[] randPeripheralsStrings;

    private final String NUMBERS_TAG = "numbers";
    private final String WEBSITES_TAG = "websites";
    private final String IP_TAG = "ipAddresses";

    private final String NUMBERS_TITLE = "Numbers";
    private final String WEBSITES_TITLE = "Websites";
    private final String IP_TITLE = "IPs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_main);

        mIntroManager = new IntroManager(this);
        if (mIntroManager.isFirstTimeLaunch()) {
            startActivity(new Intent(MainActivity.this, IntroStepperActivity.class));
            finish();
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button13 = (Button) findViewById(R.id.button13);
        button14 = (Button) findViewById(R.id.button14);
        button15 = (Button) findViewById(R.id.button15);
        button16 = (Button) findViewById(R.id.button16);
        button17 = (Button) findViewById(R.id.button17);
        button18 = (Button) findViewById(R.id.button18);
        button19 = (Button) findViewById(R.id.button19);
        button20 = (Button) findViewById(R.id.button20);

        allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20};
        randPeripherals = new int[]{R.drawable.ic_peripheral_01, R.drawable.ic_peripheral_02, R.drawable.ic_peripheral_03, R.drawable.ic_peripheral_04, R.drawable.ic_peripheral_05, R.drawable.ic_peripheral_06};
        randPeripheralsStrings = getResources().getStringArray(R.array.periphArray);
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        mSearchCardView = (CardView) findViewById(R.id.card_search);
        mSearchContainer = (RelativeLayout) findViewById(R.id.view_search);
        mSearchText = (EditText) findViewById(R.id.edit_text_search);
        mSearchBack = (ImageButton) findViewById(R.id.image_search_back);
        mSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InitiateSearch.handleSearchBar(MainActivity.this, mSearchCardView, mToolbar, mSearchContainer, mSearchText, mSearchBack, mSearchClear, mSearchTabs, mDrawerLayout);
            }
        });
        mSearchClear = (ImageButton) findViewById(R.id.clearSearch);
        mSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSearchText.getText().toString().length() == 0) {
                } else {
                    mSearchText.setText("");
                    ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        mSearchTabs = (TabLayout) findViewById(R.id.search_tabs);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview_main);
        mNavigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSearchTabs = (TabLayout) findViewById(R.id.search_tabs);

        mSearchTabs.addTab(mSearchTabs.newTab().setTag(NUMBERS_TAG).setText(NUMBERS_TITLE));
        mSearchTabs.addTab(mSearchTabs.newTab().setTag(WEBSITES_TAG).setText(WEBSITES_TITLE));
        mSearchTabs.addTab(mSearchTabs.newTab().setTag(IP_TAG).setText(IP_TITLE));

        mSearchTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        final PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), mSearchTabs.getTabCount());
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mSearchTabs));

        for (int x = 0; x < allButtons.length; x++) {
            final Button button = allButtons[x];
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed(button);

                }
            });
        }

        DataHelper.inflateLists();

        mRandPeripheral = (ImageView) findViewById(R.id.rand_peripheral);
        int peripheralId = (int) (Math.random() * randPeripherals.length);
        mRandPeripheral.setBackgroundResource(randPeripherals[peripheralId]);
        mRandPeripheral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int peripheralId = (int) (Math.random() * randPeripherals.length);
                mRandPeripheral.setBackgroundResource(randPeripherals[peripheralId]);

                String peripheralStringId = randPeripheralsStrings[new Random().nextInt(randPeripheralsStrings.length)];
                Toast.makeText(MainActivity.this, peripheralStringId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mSearchCardView.getVisibility() == View.VISIBLE) {
            InitiateSearch.handleSearchBar(MainActivity.this, mSearchCardView, mToolbar, mSearchContainer, mSearchText, mSearchBack, mSearchClear, mSearchTabs, mDrawerLayout);
            ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    InitiateSearch.handleSearchBar(MainActivity.this, mSearchCardView, mToolbar, mSearchContainer, mSearchText, mSearchBack, mSearchClear, mSearchTabs, mDrawerLayout);
                } else {
                    Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(searchIntent);
                }
                break;
            case R.id.action_reset:
                Snackbar.make(mDrawerLayout, "Are you sure you want to reset your score of " + score + "?", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resetScore();
                            }
                        }).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onButtonPressed(Button pressedButton) {
        if (score != allButtons.length) {
            score++;
        } else {
            //Snackbar.make(mCoordinatorLayout, "Error: Please reset score", Snackbar.LENGTH_SHORT).show();
        }
        pressedButton.setEnabled(false);
        updateScore();

        if (PreferenceHandler.areSoundsEnabled(this)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button_click);
            mediaPlayer.seekTo(300);
            mediaPlayer.start();
        }
    }

    public void updateScore() {
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        if (score == allButtons.length) {
            MaterialDialog.Builder alertDialogComplete = new MaterialDialog.Builder(this);
            alertDialogComplete.title(getString(R.string.bingo_complete_title));
            alertDialogComplete.content(getString(R.string.bingo_complete_msg));
            alertDialogComplete.positiveText(getString(R.string.action_okay));
            alertDialogComplete.show();
            if (PreferenceHandler.enableAutoReset(this)) {
                resetScore();
            }
        } else if (score == allButtons.length / 2) {
            MaterialDialog.Builder alertDialogHalf = new MaterialDialog.Builder(this);
            alertDialogHalf.title(getString(R.string.bingo_half_title));
            alertDialogHalf.content(getString(R.string.bingo_half_msg));
            alertDialogHalf.positiveText(getString(R.string.action_okay));
            alertDialogHalf.show();
        }
    }

    public void resetScore() {
        score = 0;
        updateScore();
        setButtonsEnabled();
        Snackbar.make(mDrawerLayout, "Reset", Snackbar.LENGTH_SHORT).show();
    }

    public void setButtonsEnabled() {
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }

    public static ArrayList<String> getCurrentButtonTexts() {
        ArrayList<String> currentButtonTexts = new ArrayList<>();

        if (currentButtonTexts.isEmpty()) {
            for (int x = 0; x < allButtons.length; x++) {
                Button button = allButtons[x];
                String buttonText = button.getText().toString();
                currentButtonTexts.add(buttonText);
            }
        }
        return currentButtonTexts;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                break;

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
            return inflater.inflate(R.layout.scammer_listview_legacy, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            listViewScammers = (ListView) view.findViewById(R.id.listViewScammers);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, DataHelper.ipsList);
            listViewScammers.setAdapter(arrayAdapter);
        }

        public static IpTab newInstance() {
            IpTab ipTab = new IpTab();
            return ipTab;
        }
    }
}
