package com.xelitexirish.scammerbingo.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.appthemeengine.ATE;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

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

    public static int score = 0;
    public static Button[] allButtons;

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

        allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10};
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        for(final Button button : allButtons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonPressed(button);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_numbers_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_reset:
                Snackbar.make(mCoordinatorLayout, "Are you sure you want to reset your score of " + score + "?", Snackbar.LENGTH_INDEFINITE)
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

    }

    public void resetScore(){
        score = 0;
        updateScore();
        setButtonsEnabled();
        Snackbar.make(mCoordinatorLayout, "Reset", Snackbar.LENGTH_SHORT).show();
    }

    public void setButtonsEnabled() {
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }
}
