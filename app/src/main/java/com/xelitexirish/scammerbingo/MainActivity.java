package com.xelitexirish.scammerbingo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.ui.AboutDialog;
import com.xelitexirish.scammerbingo.ui.DialogScammerList;
import com.xelitexirish.scammerbingo.ui.SettingsActivity;
import com.xelitexirish.scammerbingo.util.DataHelper;

public class MainActivity extends AppCompatActivity {

    TextView textViewScore;
    TextView textViewCredits;

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;

    public static int score = 0;
    public Button[] allButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textViewScore = (TextView) findViewById(R.id.textViewScore);
        this.textViewCredits = (TextView) findViewById(R.id.textViewCredits);

        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.button9 = (Button) findViewById(R.id.button9);
        this.button10 = (Button) findViewById(R.id.button10);
        this.button11 = (Button) findViewById(R.id.button11);
        this.button12 = (Button) findViewById(R.id.button12);

        this.allButtons = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12};

        if(savedInstanceState != null){
            score = savedInstanceState.getInt("KEY_CURRENT_SCORE");
        }

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
            score = 0;
            Toast.makeText(this, "Score reset", Toast.LENGTH_SHORT).show();
            updateScore();
            setButtonsEnabled();
            return true;

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

        score++;
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
        this.textViewScore.setText(score + " / " + allButtons.length);

        if (score == allButtons.length) {
            AlertDialog.Builder alertDialogComplete = new AlertDialog.Builder(this);
            alertDialogComplete.setMessage("Well done, you played the scammer for a really long time! Make sure to report them!");
            alertDialogComplete.setTitle("Bingo!");
            alertDialogComplete.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss
                }
            });
            alertDialogComplete.show();

        } else if (score == allButtons.length / 2) {
            AlertDialog.Builder alertDialogHalf = new AboutDialog.Builder(this);
            alertDialogHalf.setMessage("You're half way there! You can rat out the scammer now if it's taking too long but still make sure to report them!.");
            alertDialogHalf.setTitle("Half way there");
            alertDialogHalf.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss
                }
            });
            alertDialogHalf.show();
        }
    }
}
