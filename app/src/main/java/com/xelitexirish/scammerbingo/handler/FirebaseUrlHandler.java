package com.xelitexirish.scammerbingo.handler;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.zxing.client.android.BuildConfig;
import com.xelitexirish.scammerbingo.R;

public class FirebaseUrlHandler {

    private static final String TAG = FirebaseUrlHandler.class.getSimpleName();

    private static FirebaseRemoteConfig mFirebaseRemoteConfig;

    private static final String KEY_URL_NUMBERS = "url_numbers_raw";
    private static final String KEY_WEBSITES_NUMBERS = "url_websites_raw";
    private static final String KEY_IPS_NUMBERS = "url_ips_raw";

    private static final String URL_NUMBERS_RAW_DEFAULT = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/numbersList.txt";
    private static final String URL_WEBSITES_RAW_DEFAULT = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/websiteList.txt";
    private static final String URL_IPS_RAW_DEFAULT = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/ipsList.txt";

    public static void init(){
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);

        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        mFirebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i(TAG, "Fetch Succeeded");
                    mFirebaseRemoteConfig.activateFetched();
                }else {
                    Log.e(TAG, "Fetch Failed, please check your internet connection!");
                }
            }
        });
    }

    public static String getUrlNumbers(){
        String url = mFirebaseRemoteConfig.getString(KEY_URL_NUMBERS);
        if (url == null){
            return URL_NUMBERS_RAW_DEFAULT;
        }else {
            return url;
        }
    }

    public static String getUrlWebsites(){
        String url = mFirebaseRemoteConfig.getString(KEY_WEBSITES_NUMBERS);
        if (url == null){
            return URL_WEBSITES_RAW_DEFAULT;
        }else {
            return url;
        }
    }

    public static String getUrlIps(){
        String url = mFirebaseRemoteConfig.getString(KEY_IPS_NUMBERS);
        if (url == null){
            return URL_IPS_RAW_DEFAULT;
        }else {
            return url;
        }
    }
}
