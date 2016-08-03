package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
        getSupportActionBar().setSubtitle("Score: " + score + "/" + allButtons.length);

        if(score == allButtons.length){

        }else if(score == allButtons.length / 2){

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
