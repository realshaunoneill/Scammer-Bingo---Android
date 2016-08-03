package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    public static int score = 0;
    public static Button[] allButtons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigationview_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
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

        }
        pressedButton.setEnabled(false);
        updateScore();
    }

    public void updateScore() {
        getSupportActionBar().setSubtitle(getString(R.string.score) + ": " + score + "/" + allButtons.length);

        if (score == allButtons.length) {
            MaterialDialog.Builder alertDialogComplete = new MaterialDialog.Builder(this);
            alertDialogComplete.title(getString(R.string.bingo_complete_title));
            alertDialogComplete.content(getString(R.string.bingo_complete_msg));
            alertDialogComplete.positiveText(getString(R.string.action_okay));
            alertDialogComplete.show();
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
    }

    public void setButtonsEnabled() {
        for (int x = 0; x < allButtons.length; x++) {
            Button button = allButtons[x];
            button.setEnabled(true);
        }
    }
}
