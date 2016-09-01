package com.xelitexirish.scammerbingo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

import java.util.List;

public class QrReaderActivity extends BaseThemedActivity{

    private CoordinatorLayout mCoordinatorLayout;
    private BeepManager mBeepManager;
    private CompoundBarcodeView mBarcodeView;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() != null){
                handleDecode(result);
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
    private static String SECRET_KEY = "awgawggthsfsefjseolifsifhsefjidfhysehufskfhesfukhsefkbakjdbawdkabwdkawd5322adawd";

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
        setContentView(R.layout.activity_qr_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_qr_reader);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_premium);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mBeepManager = new BeepManager(this);

        mBarcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        mBarcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBarcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBarcodeView.pause();
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

    private void handleDecode(BarcodeResult result) {
        mBarcodeView.pause();
        String rawText = result.getText();

        if (rawText.equals(SECRET_KEY)){
            if(PreferenceHandler.areSoundsEnabled(this)){
                mBeepManager.playBeepSoundAndVibrate();
            }

            showNotifyDialog();

        }else {
            mBarcodeView.resume();
        }
    }

    private void showNotifyDialog(){
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(this);
        dialog.title(getString(R.string.title_activity_premium));
        dialog.content("You have activated some of the premium features for Scammer Bingo! Enjoy!");
        dialog.positiveText(R.string.action_okay);
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
