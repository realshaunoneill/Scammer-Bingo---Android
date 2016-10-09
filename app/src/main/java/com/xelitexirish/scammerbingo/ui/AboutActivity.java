package com.xelitexirish.scammerbingo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;
import com.xelitexirish.scammerbingo.BuildConfig;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.utils.BaseThemedActivity;

public class AboutActivity extends BaseThemedActivity {

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
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_about);

        mVersion = (TextView) findViewById(R.id.version_text);
        mVersion.setText("Version: " + appVersion);

        //XeliteXirish
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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://xelitexirish.com"));
                startActivity(intent);
            }
        });

        //Newtsrock
        mDesignerGithub = (Button) findViewById(R.id.designer_git);
        mDesignerGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/Newtsrock"));
                startActivity(intent);
            }
        });
        mDesignerTwitter = (Button) findViewById(R.id.designer_twitter);
        mDesignerTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/newtsrock"));
                startActivity(intent);
            }
        });
        mDesignerWebsite = (Button) findViewById(R.id.designer_site);
        mDesignerWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://newtsrock.github.io"));
                startActivity(intent);
            }
        });

        //JoeTheHuman
        mPromo1Github = (Button) findViewById(R.id.promo_1_git);
        mPromo1Github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JoeTheHuman"));
                startActivity(intent);
            }
        });

        mPromo1YouTube = (Button) findViewById(R.id.promo_1_youtube);
        mPromo1YouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCF2tv9jPDtgmCSvg05-C21A"));
                startActivity(intent);
            }
        });

        mPromo1Twitter = (Button) findViewById(R.id.promo_1_twitter);
        mPromo1Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/joethehumandev"));
                startActivity(intent);
            }
        });

        //Hexxium
        mPromo2Github = (Button) findViewById(R.id.promo_2_git);
        mPromo2Github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Hexxium"));
                startActivity(intent);
            }
        });

        mPromo2YouTube = (Button) findViewById(R.id.promo_2_youtube);
        mPromo2YouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCeBAWpe_uezAQTzz_fCO6EQ"));
                startActivity(intent);
            }
        });

        mPromo2Twitter = (Button) findViewById(R.id.promo_2_twitter);
        mPromo2Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/HexxiumGaming"));
                startActivity(intent);
            }
        });

        //Dazza
        mPromo3YouTube = (Button) findViewById(R.id.promo_3_youtube);
        mPromo3YouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCzVm0R21dxt8wM2mrZNwOZw"));
                startActivity(intent);
            }
        });

        mPromo3Twitter = (Button) findViewById(R.id.promo_3_twitter);
        mPromo3Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/OhDxzza"));
                startActivity(intent);
            }
        });

        //Lewis
        mPromo4YouTube = (Button) findViewById(R.id.promo_4_youtube);
        mPromo4YouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/LewissTech"));
                startActivity(intent);
            }
        });

        mPromo4Twitter = (Button) findViewById(R.id.promo_4_twitter);
        mPromo4Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/lewisstechYT"));
                startActivity(intent);
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
