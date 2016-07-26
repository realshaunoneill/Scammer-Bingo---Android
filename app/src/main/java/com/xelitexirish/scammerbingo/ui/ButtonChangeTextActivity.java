package com.xelitexirish.scammerbingo.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xelitexirish.scammerbingo.MainActivity;
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ButtonChangeTextActivity.this);
                alertDialog.setTitle("Change button text");
                alertDialog.setMessage("Enter the text you wish to appear on the button");

                final EditText editTextInput = new EditText(ButtonChangeTextActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                editTextInput.setLayoutParams(layoutParams);
                editTextInput.setHint("Enter the text you wish to appear on the button");
                alertDialog.setView(editTextInput);

                alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputText = editTextInput.getText().toString();
                        button.setText(inputText);
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
