package com.xelitexirish.scammerbingo.utils;

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

    public static final String URL_NUMBERS_RAW = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/numbersList.txt";
    public static final String URL_WEBSITES_RAW = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/websiteList.txt";
    public static final String URL_IPS_RAW = "https://raw.githubusercontent.com/TCDG/Scammer-Bingo---Android/master/data/ipsList.txt";

    public static void inflateLists() {
        new inflateOnlineLists().execute();
    }


    public static class inflateOnlineLists extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Scanner scannerNumbers = new Scanner(new URL(URL_NUMBERS_RAW).openStream());
                Scanner scannerWebsites = new Scanner(new URL(URL_WEBSITES_RAW).openStream());
                Scanner scannerIps = new Scanner(new URL(URL_IPS_RAW).openStream());

                while (scannerNumbers.hasNextLine()) {
                    String line = scannerNumbers.nextLine();
                    numbersList.add(line);
                }

                while (scannerWebsites.hasNextLine()) {
                    String line = scannerWebsites.nextLine();
                    if(line.startsWith("||")) {
                        websitesList.add(line.substring(2));
                    }
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