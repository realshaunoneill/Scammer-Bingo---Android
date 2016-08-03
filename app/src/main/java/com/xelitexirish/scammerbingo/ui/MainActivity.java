package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;

public class MainActivity extends AppCompatActivity {

    public static int score = 0;
    public static Button[] allButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onButtonPressed(Button pressedButton){
        if(score != allButtons.length){
            score++;
        }else{

        }
        pressedButton.setEnabled(false);
        updateScore();
    }

    public void updateScore(){
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        if(score == allButtons.length){
            MaterialDialog.Builder alertDialogComplete = new MaterialDialog.Builder(this);
            alertDialogComplete.title(getString(R.string.bingo_complete_title));
            alertDialogComplete.content(getString(R.string.bingo_complete_msg));
            alertDialogComplete.positiveText(getString(R.string.action_okay));
            alertDialogComplete.show();
        }else if(score == allButtons.length / 2){
            MaterialDialog.Builder alertDialogHalf = new MaterialDialog.Builder(this);
            alertDialogHalf.title(getString(R.string.bingo_half_title));
            alertDialogHalf.content(getString(R.string.bingo_half_msg));
            alertDialogHalf.positiveText(getString(R.string.action_okay));
            alertDialogHalf.show();
        }
    }

    public void resetScore(){
        score = 0;
        updateScore();
        setButtonsEnabled();
    }

    public void setButtonsEnabled(){
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }
}
