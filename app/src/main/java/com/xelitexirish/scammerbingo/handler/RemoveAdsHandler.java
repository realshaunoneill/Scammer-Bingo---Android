package com.xelitexirish.scammerbingo.handler;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xelitexirish.scammerbingo.R;
import com.xelitexirish.scammerbingo.billing.IabHelper;
import com.xelitexirish.scammerbingo.billing.IabResult;
import com.xelitexirish.scammerbingo.billing.Inventory;
import com.xelitexirish.scammerbingo.billing.Purchase;
import com.xelitexirish.scammerbingo.prefs.PreferenceHandler;
import com.xelitexirish.scammerbingo.utils.Constants;

public class RemoveAdsHandler {

    // Debug tag, for logging
    static final String TAG = "RemoveAdsHandler";

    static final String SKU_REMOVE_ADS = "remove_ads";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10111;

    Activity activity;

    // The helper object
    IabHelper mHelper;

    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvVJswtU9lvDBE1CnTNZm9j8eg3RmeCgoqDwSgl+Zw3jhZXFHepXkJ5XvlYBi0lFxefdG7rMgZFjKEe/VqJmr+GuxnKU6hZjxXR5RqmO+gmAnZ6LhQ6djvdaG87/cp4vy+Hy2r7gRAj4+K9X5stnuuTsx6R1g0c2XdvbHobCcnKiKaN7/Xqvxd+PgRBtmfxj5mBgDb9tKWlF3Dml/hdt9UPhVHmXr0OG43Ev9TSWQjIng6UCq//AkTmSoj9FaFywGMs+NO8lfv2ZA5N56Z0rbPSL4bcF5fVaOk0e2K0/8l4ocseZ6ZVPELwZBqDmlJ1YeaLtiV3qtqVIu/WsGrcE/BQIDAQAB";
    String payload = "ANY_PAYLOAD_STRING";

    public RemoveAdsHandler(Activity activity) {
        this.activity = activity;
    }

    public void onCreate() {

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(activity, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem. complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed off in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null)
                return;

            // Is it a failure?
            if (result.isFailure()) {
                // complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            Purchase removeAdsPurchase = inventory.getPurchase(SKU_REMOVE_ADS);
            Constants.isAdsDisabled = (removeAdsPurchase != null && verifyDeveloperPayload(removeAdsPurchase));
            removeAds();

            Log.d(TAG, "User has " + (Constants.isAdsDisabled ? "REMOVED ADS" : "NOT REMOVED ADS"));

            // setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");

        }
    };

    // User clicked the "Remove Ads" button.
    public void purchaseRemoveAds() {

        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                mHelper.launchPurchaseFlow(activity, SKU_REMOVE_ADS, RC_REQUEST, mPurchaseFinishedListener, payload);

            }

        });
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null)
            return true;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            return false;
        } else {

            Log.d(TAG, "onActivityResult handled by IABUtil.");

            return true;
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_REMOVE_ADS)) {
                // bought the premium upgrade!
                removeAds();

            }
        }
    };

    private void removeAds() {
        PreferenceHandler.enableAds(activity, false);
    }

    // We're being destroyed. It's important to dispose of the helper here!

    public void onDestroy() {

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(final String message) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                MaterialDialog.Builder bld = new MaterialDialog.Builder(activity);
                bld.content(message);
                bld.neutralText("OK");
                Log.d(TAG, "Showing alert dialog: " + message);
                bld.show();
            }
        });
    }
}
