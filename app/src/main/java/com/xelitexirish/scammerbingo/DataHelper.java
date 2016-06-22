package com.xelitexirish.scammerbingo;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHelper {

    public static ArrayList<String> numbersList = new ArrayList<>();
    public static ArrayList<String> websitesList = new ArrayList<>();
    public static ArrayList ipsList = new ArrayList();

    public static void inflateLists() {
        new InflateListData().execute();
    }

    private static class InflateListData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL downloadUrlNumbers = new URL("https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/numbersList.txt");
                URL downloadUrlWebsites = new URL("https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/numbersList.txt");
                URL downloadUrlIps = new URL("https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/websiteList.txt");

                Scanner scannerNumbers = new Scanner(downloadUrlNumbers.openStream());
                Scanner scannerWebsites = new Scanner(downloadUrlWebsites.openStream());
                Scanner scannerIps = new Scanner(downloadUrlIps.openStream());

                while (scannerNumbers.hasNextLine()) {
                    String line = scannerNumbers.nextLine();
                    numbersList.add(line);
                }

                while (scannerWebsites.hasNextLine()) {
                    String line = scannerWebsites.nextLine();
                    websitesList.add(line);
                }

                while (scannerIps.hasNextLine()) {
                    String line = scannerIps.nextLine();
                    ipsList.add(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}