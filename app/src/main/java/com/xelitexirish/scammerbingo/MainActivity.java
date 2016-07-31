package com.xelitexirish.scammerbingo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.ui.AboutDialog;
import com.xelitexirish.scammerbingo.ui.ButtonChangeTextActivity;
import com.xelitexirish.scammerbingo.ui.DialogScammerList;
import com.xelitexirish.scammerbingo.ui.SettingsActivity;
import com.xelitexirish.scammerbingo.util.DataHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textViewCredits;

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

    AdView footerAdview;

    public static int score = 0;
    public static Button[] allButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        textViewCredits = (TextView) findViewById(R.id.textViewCredits);

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

        this.footerAdview = (AdView) findViewById(R.id.footerAdview);

        this.allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20};

        getSupportActionBar().setSubtitle("Score: " + score + "/" + allButtons.length);

        if(savedInstanceState != null){
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

        this.textViewCredits.setOnClickListener(new View.OnClickListener() {
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
        int id = item.getItemId();

        if(id == R.id.action_scammer_details){
            Intent intent = new Intent(this, DialogScammerList.class);
            startActivity(intent);

        }else if (id == R.id.action_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "I scored a massive " + score + " / " + allButtons.length + " If you are getting scammed see what score you get too!");
            intent.setType("text/plain");
            startActivity(intent);

        }else if(id == R.id.action_sub_lounge){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.discord.me/ScammerSubLounge"));
            startActivity(intent);

        }else if(id == R.id.action_report){
            Toast.makeText(this, "Coming soon, I promise!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.action_reset) {
            resetScore();
            return true;

        }else if (id == R.id.action_button_text){
            Intent intent = new Intent(this, ButtonChangeTextActivity.class);
            startActivity(intent);

        }else if(id == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_about) {
            AboutDialog aboutDialog = new AboutDialog(this);
            aboutDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Button pressedButton) {

        if(score != allButtons.length) {
            score++;
        }else{
            Toast.makeText(this, "Error: Please reset score", Toast.LENGTH_SHORT).show();
        }
        pressedButton.setEnabled(false);
        updateScore();

        // Play Sound if enabled
        if(PreferenceHandler.areSoundsEnabled(this)) {
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
            alertDialogComplete.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
            alertDialogComplete.show();

            if(PreferenceHandler.enableAutoReset(this)){
                resetScore();
            }

        } else if (score == allButtons.length / 2) {
            MaterialDialog.Builder alertDialogHalf = new MaterialDialog.Builder(this);
            alertDialogHalf.title("Half way there");
            alertDialogHalf.content("You're half way there! You can rat out the scammer now if it's taking too long but still make sure to report them!.");
            alertDialogHalf.positiveText("Okay");
            alertDialogHalf.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                }
            });
            alertDialogHalf.show();
        }
    }

    public void resetScore(){
        score = 0;
        Toast.makeText(this, "Score reset", Toast.LENGTH_SHORT).show();
        updateScore();
        setButtonsEnabled();
    }

    public static ArrayList<String> getCurrentButtonTexts() {
        ArrayList<String> currentButtonTexts = new ArrayList<>();

        if(currentButtonTexts.isEmpty()) {
            for (int x = 0; x < allButtons.length; x++) {
                Button button = allButtons[x];
                String buttonText = button.getText().toString();
                currentButtonTexts.add(buttonText);
            }
        }
        return currentButtonTexts;
    }
}
