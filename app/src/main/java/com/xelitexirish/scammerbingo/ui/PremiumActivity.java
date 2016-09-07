package com.xelitexirish.scammerbingo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.handler.RemoveAdsHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

public class PremiumActivity extends BaseThemedActivity {

    public RemoveAdsHandler adsHandler = new RemoveAdsHandler(this);

    private CoordinatorLayout mCoordinatorLayout;
    private Button mButtonRemoveAds;

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
        setContentView(R.layout.activity_premium);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_premium);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_premium);

        mButtonRemoveAds = (Button) findViewById(R.id.buttonRemoveAds);

        mButtonRemoveAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsHandler.purchaseRemoveAds();
                //Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        adsHandler.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsHandler.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adsHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_premium, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_qr_reader:
                Intent qrReaderIntent = new Intent(this, QrReaderActivity.class);
                startActivity(qrReaderIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
