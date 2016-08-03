package com.xelitexirish.scammerbingo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xelitexirish.scammerbingo.BuildConfig;
import com.xelitexirish.scammerbingo.R;

public class AboutActivity extends AppCompatActivity{

    private Button mAuthorGithub,
            mAuthorTwitter,
            mAuthorWebsite,
            mDesignerGithub,
            mDesignerTwitter,
            mDesignerWebsite,
            mPromo1Github,
            mPromo1YouTube,
            mPromo1Twitter,
            mPromo2Github,
            mPromo2YouTube,
            mPromo2Twitter,
            mPromo3YouTube,
            mPromo3Twitter,
            mPromo4YouTube,
            mPromo4Twitter;

    private CoordinatorLayout mCoordinatorLayout;
    private String appVersion = BuildConfig.VERSION_NAME;
    private TextView mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_about);

        mVersion = (TextView) findViewById(R.id.version_text);
        mVersion.setText("Version: " + appVersion);

        // XeliteXirish
        mAuthorGithub = (Button) findViewById(R.id.author_git);
        mAuthorGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/XeliteXirish"));
                startActivity(intent);
            }
        });
        mAuthorTwitter = (Button) findViewById(R.id.author_twitter);
        mAuthorTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/xelitexirish"));
                startActivity(intent);
            }
        });
        mAuthorWebsite = (Button) findViewById(R.id.author_site);
        mAuthorWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mCoordinatorLayout, "Coming soon, I promise", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
