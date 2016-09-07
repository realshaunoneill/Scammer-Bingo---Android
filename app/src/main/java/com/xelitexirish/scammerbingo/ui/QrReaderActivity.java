package com.xelitexirish.scammerbingo.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.handler.FirebaseStringHandler;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

import java.util.List;

public class QrReaderActivity extends BaseThemedActivity {

    private CoordinatorLayout mCoordinatorLayout;
    private BeepManager mBeepManager;
    private CompoundBarcodeView mBarcodeView;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                handleDecode(result);
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;

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
        mBarcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        mBarcodeView.setStatusText("Waiting for QR-Code...");

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mBeepManager = new BeepManager(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startScanning();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScanning();
                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        }
    }

    private void startScanning() {
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

        if (rawText.equals(FirebaseStringHandler.getRemoveAdsSecret())) {
            if (PreferenceHandler.areSoundsEnabled(this)) {
                mBeepManager.playBeepSoundAndVibrate();
            }

            if(PreferenceHandler.areAdsEnabled(this)) {
                showNotifyDialog();
            }else{
                showAlreadyActivitedDialog();
            }

            PreferenceHandler.enableAds(this, false);
        } else {
            mBarcodeView.resume();
        }
    }

    private void showNotifyDialog() {
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(this);
        dialog.title(getString(R.string.title_activity_premium));
        dialog.content("You have activated some of the premium features for Scammer Bingo! Enjoy!");
        dialog.positiveText(R.string.action_okay);
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void showAlreadyActivitedDialog() {
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(this);
        dialog.title(getString(R.string.title_activity_premium));
        dialog.content("You have already activated the features associated with that code.");
        dialog.positiveText(R.string.action_okay);
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }
}
