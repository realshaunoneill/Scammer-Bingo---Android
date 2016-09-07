package com.xelitexirish.scammerbingo.utils;

import android.os.AsyncTask;

import com.xelitexirish.scammerbingo.handler.FirebaseUrlHandler;

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
        new inflateOnlineLists().execute();
    }


    public static class inflateOnlineLists extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Scanner scannerNumbers = new Scanner(new URL(FirebaseUrlHandler.getUrlNumbers()).openStream());
                Scanner scannerWebsites = new Scanner(new URL(FirebaseUrlHandler.getUrlWebsites()).openStream());
                Scanner scannerIps = new Scanner(new URL(FirebaseUrlHandler.getUrlIps()).openStream());

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