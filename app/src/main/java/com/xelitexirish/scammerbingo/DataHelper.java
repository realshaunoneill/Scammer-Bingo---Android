package com.xelitexirish.scammerbingo;

import android.content.Context;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHelper {

    public static ArrayList<String> numbersList = new ArrayList<>();
    public static ArrayList<String> websitesList = new ArrayList<>();
    public static ArrayList ipsList = new ArrayList();

    public static void inflateLists(Context context) {
        inflateLocalListData(context);
    }

    public static void inflateLocalListData(Context context) {

        try {
            InputStream inputStreamNumbers = context.getResources().openRawResource(R.raw.numbers_list);
            InputStream inputStreamWebsite = context.getResources().openRawResource(R.raw.website_list);
            InputStream inputStreamIps = context.getResources().openRawResource(R.raw.ips_list);

            Scanner scannerNumbers = new Scanner(inputStreamNumbers);
            Scanner scannerWebsites = new Scanner(inputStreamWebsite);
            Scanner scannerIps = new Scanner(inputStreamIps);

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
        } catch (Exception e) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
        }
    }
}