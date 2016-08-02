package com.xelitexirish.scammerbingo.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xelitexirish.scammerbingo.InitiateSearch;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.util.DataHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    CoordinatorLayout mCoordinatorLayout;
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

    private Toolbar mToolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;
    private InitiateSearch initiateSearch;
    private CardView card_search;
    private ImageView image_search_back, clearSearch;
    private EditText edit_text_search;
    private RelativeLayout view_search;
    private DrawerLayout mDrawerLayout;
    private AppBarLayout mSearchToolbar;

    AdView footerAdview;

    public static int score = 0;
    public static Button[] allButtons;

    public final String NUMBERS_TAG = "numbers";
    public final String WEBSITES_TAG = "websites";
    public final String IP_TAG = "ipAddresses";

    public final String NUMBERS_TITLE = "Numbers";
    public final String WEBSITES_TITLE = "Websites";
    public final String IP_TITLE = "IPs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_main);
        mSearchToolbar = (AppBarLayout) findViewById(R.id.search_toolbar);

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

        footerAdview = (AdView) findViewById(R.id.footerAdview);

        this.allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20};

        getSupportActionBar().setSubtitle("Score: " + score + "/" + allButtons.length);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("KEY_CURRENT_SCORE");
        }

        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_footer_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        footerAdview.loadAd(adRequest);

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

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.search_tabs);

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

        edit_text_search = (EditText) findViewById(R.id.edit_text_search);
        card_search = (CardView) findViewById(R.id.card_search);
        image_search_back = (ImageView) findViewById(R.id.image_search_back);
        clearSearch = (ImageView) findViewById(R.id.clearSearch);
        view_search = (RelativeLayout) findViewById(R.id.view_search);

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_text_search.getText().toString().length() == 0) {
                } else {
                    edit_text_search.setText("");
                    ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });

        image_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateSearch.handleSearchBar(MainActivity.this, card_search, mToolbar, view_search, edit_text_search, image_search_back, clearSearch, mSearchToolbar, tabLayout);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (card_search.getVisibility() == View.VISIBLE) {
            initiateSearch.handleSearchBar(MainActivity.this, card_search, mToolbar, view_search, edit_text_search, image_search_back, clearSearch, mSearchToolbar, tabLayout);
            ((InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("KEY_CURRENT_SCORE", score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scammer_details:
                initiateSearch.handleSearchBar(MainActivity.this, card_search, mToolbar, view_search, edit_text_search, image_search_back, clearSearch, mSearchToolbar, tabLayout);
                break;

            case R.id.action_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I scored a massive " + score + " / " + allButtons.length + " If you are getting scammed see what score you get too!");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                break;

            case R.id.action_sub_lounge:
                Intent discordIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.discord.me/ScammerSubLounge"));
                startActivity(discordIntent);
                break;

            case R.id.action_report:
                Snackbar.make(mCoordinatorLayout, "Coming soon, I promise", Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.action_reset:
                Snackbar.make(mCoordinatorLayout, "Are you sure you want to reset your score of " + score + "?", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resetScore();
                            }
                        }).show();
                break;

            case R.id.action_button_text:
                Intent changeIntent = new Intent(this, ButtonChangeTextActivity.class);
                startActivity(changeIntent);
                break;

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Button pressedButton) {

        if (score != allButtons.length) {
            score++;
        } else {
            Snackbar.make(mCoordinatorLayout, "Error: Please reset score", Snackbar.LENGTH_SHORT).show();
        }
        pressedButton.setEnabled(false);
        updateScore();

        // Play Sound if enabled
        if (PreferenceHandler.areSoundsEnabled(this)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button_click);
            mediaPlayer.seekTo(300);
            mediaPlayer.start();
        }
    }

    public void setButtonsEnabled() {
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }

    public void updateScore() {
        getSupportActionBar().setSubtitle("Score: " + score + "/" + allButtons.length);

        if (score == allButtons.length) {
            MaterialDialog.Builder alertDialogComplete = new MaterialDialog.Builder(this);
            alertDialogComplete.title("Bingo!");
            alertDialogComplete.content("Well done, you played the scammer for a really long time! Make sure to report them!");
            alertDialogComplete.positiveText("Okay");
            alertDialogComplete.show();

            if (PreferenceHandler.enableAutoReset(this)) {
                resetScore();
            }

        } else if (score == allButtons.length / 2) {
            MaterialDialog.Builder alertDialogHalf = new MaterialDialog.Builder(this);
            alertDialogHalf.title("Half way there");
            alertDialogHalf.content("You're half way there! You can rat out the scammer now if it's taking too long but still make sure to report them!.");
            alertDialogHalf.positiveText("Okay");
            alertDialogHalf.show();
        }
    }

    public void resetScore() {
        score = 0;
        updateScore();
        setButtonsEnabled();
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
