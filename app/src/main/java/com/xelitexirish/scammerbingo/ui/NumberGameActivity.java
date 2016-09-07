package com.xelitexirish.scammerbingo.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class NumberGameActivity extends BaseThemedActivity {

    private CoordinatorLayout mCoordinatorLayout;

    Button button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9,
            button10;
    ImageView mButtonNumbersHelp;

    AdView mAdViewFooter;

    public static int score = 0;
    public static Button[] allButtons;

    private static int NUMBERS_RANGE = 200;

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
        setContentView(R.layout.activity_number_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_numbers);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_numbers);

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
        mButtonNumbersHelp = (ImageView) findViewById(R.id.imageViewHelp);

        mAdViewFooter = (AdView) findViewById(R.id.footerAdViewNumbers);

        allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10};
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        Random randomButtonTitle = new Random();
        Set set = new HashSet<Integer>(allButtons.length);
        while (set.size() < allButtons.length){
            while (!set.add(randomButtonTitle.nextInt(NUMBERS_RANGE)));
        }

        for (int x = 0; x < set.size(); x++){
            Button button = allButtons[x];
            Object[] numbers = set.toArray();
            button.setText(numbers[x].toString());
        }

        for (final Button button : allButtons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonPressed(button);
                }
            });
        }

        mButtonNumbersHelp.setBackgroundResource(R.drawable.ic_about_nav);
        mButtonNumbersHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpMessage();
            }
        });

        doAppAds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_numbers_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_reset:
                Snackbar.make(mCoordinatorLayout, "Are you sure you want to reset your score of " + score + "?", Snackbar.LENGTH_INDEFINITE).setDuration(Snackbar.LENGTH_SHORT)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                resetScore();
                            }
                        }).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Button pressedButton){
        if (score != allButtons.length) {
            score++;
        }
        pressedButton.setEnabled(false);
        updateScore();

        if (PreferenceHandler.areSoundsEnabled(this)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button_click);
            mediaPlayer.seekTo(300);
            mediaPlayer.start();
        }
    }

    public void updateScore(){
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);
    }

    public void resetScore(){
        score = 0;
        updateScore();
        setButtonsEnabled();
        Snackbar.make(mCoordinatorLayout, "Reset", Snackbar.LENGTH_SHORT).setDuration(Snackbar.LENGTH_LONG).show();
    }

    public void setButtonsEnabled() {
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }

    public void showHelpMessage(){
        MaterialDialog.Builder helpDialog = new MaterialDialog.Builder(this);
        helpDialog.title(R.string.numbers_dialog_title);
        helpDialog.content(R.string.numbers_dialog_message);
        helpDialog.positiveText(R.string.action_okay);
        helpDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        helpDialog.show();
    }

    private void doAppAds() {
        if(PreferenceHandler.areAdsEnabled(this)) {
            MobileAds.initialize(getApplicationContext(), getString(R.string.ad_footer_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdViewFooter.loadAd(adRequest);
        }else {
            mAdViewFooter.setVisibility(View.GONE);
        }
    }
}
