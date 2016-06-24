package com.xelitexirish.scammerbingo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutDialog extends AlertDialog{

    TextView textViewAbout;
    TextView textViewCredits;
    TextView textViewAuthor;

    public AboutDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_about);
        this.getActionBar().setDisplayHomeAsUpEnabled(true);

        this.textViewAbout = (TextView) findViewById(R.id.textViewAbout);
        this.textViewCredits = (TextView) findViewById(R.id.textViewCredits);
        this.textViewAuthor = (TextView) findViewById(R.id.textViewAuthor);

        this.textViewAbout.setText(Html.fromHtml(buildAboutMessage()));
        this.textViewCredits.setText(Html.fromHtml(buildCreditsMessage()));
        this.textViewAuthor.setText(Html.fromHtml(buildAuthorMessage()));

        this.textViewAbout.setMovementMethod(LinkMovementMethod.getInstance());
        this.textViewCredits.setMovementMethod(LinkMovementMethod.getInstance());
        this.textViewAuthor.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public String buildAboutMessage() {
        String appVersion = BuildConfig.VERSION_NAME;
        return String.format(getContext().getString(R.string.about_fragment_msg_description), appVersion);
    }

    public String buildCreditsMessage() {
        return getContext().getString(R.string.about_fragment_msg_credits_title) + getContext().getString(R.string.about_fragment_msg_credits);
    }

    public String buildAuthorMessage() {
        return getContext().getString(R.string.about_fragment_author_title) + getContext().getString(R.string.about_fragment_author);
    }
}
