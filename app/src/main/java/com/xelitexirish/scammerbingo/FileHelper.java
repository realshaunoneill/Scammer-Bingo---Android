package com.xelitexirish.scammerbingo;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

public class FileHelper {

    public static Context context;
    public static DownloadManager downloadManager;

    public static final String fileNameNumbers = "numbersList.txt";
    public static final String fileNameWebsites = "websitesList.txt";
    public static final String fileNameIps = "ipsList.txt";

    public static final String downloadUrlNumbers = "https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/numbersList.txt";
    public static final String downloadUrlWebsites = "https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/numbersList.txt";
    public static final String downloadUrlIps = "https://raw.githubusercontent.com/XeliteXirish/ScammerBingoApp/master/data/websiteList.txt";

    public static File localFileNumbers;
    public static File localFileWebsites;
    public static File localFileIps;

    public static String[] fileNames = new String[]{fileNameNumbers, fileNameWebsites, fileNameIps};
    public static String[] downloadUrls = new String[]{downloadUrlNumbers, downloadUrlWebsites, downloadUrlIps};
    public static File[] localFiles = new File[]{localFileNumbers, localFileWebsites, localFileIps};

    public static long downloadID;

    public FileHelper(Context context){
        this.context = context;
        this.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        this.localFileNumbers = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileNameNumbers);
        this.localFileWebsites = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileNameWebsites);
        this.localFileIps = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileNameIps);
    }

    public static void checkLocalFiles(){
        if(localFileNumbers.exists() && localFileWebsites.exists() && localFileIps.exists()){
            showAlertBox();
        }else{
            downloadFiles(downloadUrls);
        }
    }

    public static void downloadFiles(String[] urls){
        for(int x = 0; x < urls.length; x++){
            if(hasConnection()){
                String url = urls[x];
                if(url != null && url.startsWith("http")){
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    }
                    request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileNames[x]);
                    request.setTitle(fileNames[x]);
                    request.setVisibleInDownloadsUi(true);

                    downloadID = downloadManager.enqueue(request);
                    Toast.makeText(context, "Downloading localFile...", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "No internet connection found", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadID, 0);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    try {
                        Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
                        //what to do when finished
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case DownloadManager.STATUS_FAILED:
                    break;
            }
        }
    }

    public static void showAlertBox(){
        // FileType 0 = paper 1, 1 = paper 2, 2 = marking scheme
        AlertDialog.Builder alerBox = new AlertDialog.Builder(context);
        alerBox.setTitle("Local version found").setMessage("Do you want to use local file or download a new version?");

        alerBox.setPositiveButton("Use local", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alerBox.setNegativeButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int x = 0; x < localFiles.length; x++){
                    File file = localFiles[x];
                    file.delete();
                    downloadFiles(downloadUrls);
                }
                dialog.dismiss();
            }
        });
        alerBox.show();
    }

    public static boolean hasConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
}
