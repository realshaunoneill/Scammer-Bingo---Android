package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;

import java.util.ArrayList;

public class ButtonChangeTextActivity extends AppCompatActivity {

    public ListView listViewButtonTexts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_change_text);

        this.listViewButtonTexts = (ListView) findViewById(R.id.listViewButtonTexts);

        ArrayList<String> currentButtonTexts = MainActivity.getCurrentButtonTexts();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_list_bullet, currentButtonTexts);
        this.listViewButtonTexts.setAdapter(arrayAdapter);

        this.listViewButtonTexts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get clicked button
                final Button button = MainActivity.allButtons[position];

                new MaterialDialog.Builder(ButtonChangeTextActivity.this)
                        .title("Change button text")
                        .content("Enter the text you wish to appear on the button")
                        .positiveText("Okay")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .negativeText("Cancel")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                button.setText(input.toString());
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
