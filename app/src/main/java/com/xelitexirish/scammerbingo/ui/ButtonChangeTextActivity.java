package com.xelitexirish.scammerbingo.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

import java.util.ArrayList;

/**
 * Created by XeliteXirish on 06/10/2016 (www.xelitexirish.com)
 */
public class ButtonChangeTextActivity extends BaseThemedActivity {

    private ListView listViewButtonTexts;

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
        setContentView(R.layout.activity_change_button_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_button_charge);
        setSupportActionBar(toolbar);

        this.listViewButtonTexts = (ListView) findViewById(R.id.texts_listview_buttons);

        ArrayList<String> currentButtonStrings = MainActivity.getCurrentButtonTexts();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.item_list_bullet, currentButtonStrings);
        this.listViewButtonTexts.setAdapter(arrayAdapter);

        this.listViewButtonTexts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final Button button = MainActivity.allButtons[position];

                new MaterialDialog.Builder(ButtonChangeTextActivity.this)
                        .title(R.string.dialog_change_button_texts_title)
                        .content(R.string.dialog_change_button_texts_content)
                        .positiveText(R.string.action_okay)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .negativeText(R.string.md_cancel_label)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                button.setText(input.toString());
                                PreferenceHandler.saveButtonTextsSet(ButtonChangeTextActivity.this, MainActivity.getCurrentButtonTexts());
                                Toast.makeText(ButtonChangeTextActivity.this, "Now set to: " + input.toString(), Toast.LENGTH_SHORT).show();
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
