package com.xelitexirish.scammerbingo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHelper {

    public static ArrayList<String> numbersList = new ArrayList<>();
    public static ArrayList<String> websitesList = new ArrayList<>();
    public static ArrayList ipsList = new ArrayList();

    public static File fileNumbers = FileHelper.localFileNumbers;
    public static File fileWebsites = FileHelper.localFileWebsites;
    public static File fileIps = FileHelper.localFileIps;

    public static void inflateLists(){

        FileHelper.checkLocalFiles();

        try {
            Scanner scannerNumbers = new Scanner(fileNumbers);
            Scanner scannerWebsites = new Scanner(fileWebsites);
            Scanner scannerIps = new Scanner(fileIps);

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }
}
