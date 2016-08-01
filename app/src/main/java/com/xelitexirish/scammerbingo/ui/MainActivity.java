package com.xelitexirish.scammerbingo.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.util.DataHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    CoordinatorLayout mCoordinatorLayout;
    TextView mTextViewCredits;
    Button mButton1,
            mButton2,
            mButton3,
            mButton4,
            mButton5,
            mButton6,
            mButton7,
            mButton8,
            mButton9,
            mButton10,
            mButton11,
            mButton12,
            mButton13,
            mButton14,
            mButton15,
            mButton16,
            mButton17,
            mButton18,
            mButton19,
            mButton20;

    private Toolbar mToolbar;

    AdView footerAdview;

    public static int score = 0;
    public static Button[] allButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mTextViewCredits = (TextView) findViewById(R.id.textViewCredits);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_main);

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton6 = (Button) findViewById(R.id.button6);
        mButton7 = (Button) findViewById(R.id.button7);
        mButton8 = (Button) findViewById(R.id.button8);
        mButton9 = (Button) findViewById(R.id.button9);
        mButton10 = (Button) findViewById(R.id.button10);
        mButton11 = (Button) findViewById(R.id.button11);
        mButton12 = (Button) findViewById(R.id.button12);
        mButton13 = (Button) findViewById(R.id.button13);
        mButton14 = (Button) findViewById(R.id.button14);
        mButton15 = (Button) findViewById(R.id.button15);
        mButton16 = (Button) findViewById(R.id.button16);
        mButton17 = (Button) findViewById(R.id.button17);
        mButton18 = (Button) findViewById(R.id.button18);
        mButton19 = (Button) findViewById(R.id.button19);
        mButton20 = (Button) findViewById(R.id.button20);

        footerAdview = (AdView) findViewById(R.id.footerAdview);

        allButtons = new Button[]{mButton1,
                mButton2,
                mButton3,
                mButton4,
                mButton5,
                mButton6,
                mButton7,
                mButton7,
                mButton7,
                mButton8,
                mButton9,
                mButton10,
                mButton11,
                mButton12,
                mButton13,
                mButton14,
                mButton15,
                mButton16,
                mButton17,
                mButton18,
                mButton19,
                mButton20};

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

        mTextViewCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://xelitexirish.com"));
                startActivity(intent);
            }
        });

        DataHelper.inflateLists();
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
        getMenuInflater().inflate(R.menu.menu_header, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scammer_details:
                Intent searchIntent = new Intent(this, DialogScammerList.class);
                startActivity(searchIntent);
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
}
