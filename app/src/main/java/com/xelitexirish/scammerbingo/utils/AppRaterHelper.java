package com.xelitexirish.scammerbingo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;

public class AppRaterHelper {

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;

    public static void appLaunched(Context context){
        SharedPreferences prefs = context.getSharedPreferences("apprater", 0);
        SharedPreferences.Editor editor = prefs.edit();
        if(!neverDisplayDialog(context)) {
            // Increment launch counter
            long launch_count = prefs.getLong("launch_count", 0) + 1;
            editor.putLong("launch_count", launch_count);

            // Get date of first launch
            Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
            if (date_firstLaunch == 0) {
                date_firstLaunch = System.currentTimeMillis();
                editor.putLong("date_firstlaunch", date_firstLaunch);
            }

            // Wait at least n days before opening
            if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
                if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                    showRateDialog(context);
                }
            }

            editor.apply();
        }
    }

    private static void showRateDialog(final Context context){
        final MaterialDialog.Builder rateDialog = new MaterialDialog.Builder(context);
        rateDialog.title("Rate Scammer Bingo?");
        rateDialog.content("If you enjoy using Scammer Bingo, would you like to take the time to rate this app on the Google Play Store? Thank you!");
        rateDialog.positiveText(R.string.action_okay);
        rateDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.xelitexirish.scammerbingo"));
                try{
                    context.startActivity(intent);
                }catch (Exception e){
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.xelitexirish.scammerbingo"));
                    context.startActivity(intent);
                }
            }
        });
        rateDialog.neutralText(R.string.action_no);
        rateDialog.onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
        rateDialog.negativeText(R.string.action_never);
        rateDialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("NEVER_SHOW_RATE_DIALOG", true);
                editor.apply();
            }
        });
    }

    private static boolean neverDisplayDialog(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("NEVER_SHOW_RATE_DIALOG", false);
    }
}
