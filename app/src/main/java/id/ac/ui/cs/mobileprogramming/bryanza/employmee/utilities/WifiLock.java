package id.ac.ui.cs.mobileprogramming.bryanza.employmee.utilities;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.*;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R;

import java.util.List;

public class WifiLock {

    String ssid = null;

    public void getCurrentSsid(@NonNull final Context context, String network1,
                                 String network2, String result, final DownloadManager manager,
                                 final String completed, String finish, String failed,
                                 String password) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//        if (networkInfo == null) {
//            return null;
//        }

        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (networkInfo.isConnected()) {
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
                if (ssid.contains(network1) || ssid.contains(network2)){
                    String urlDownload = "https://upload.wikimedia.org/wikipedia/commons/2/2c/A_new_map_of_Great_Britain_according_to_the_newest_and_most_exact_observations_%288342715024%29.jpg";
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));

                    request.setDescription("Testando");
                    request.setTitle("Download");
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "teste.zip");

//                    final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    final long downloadId = manager.enqueue(request);

//                    final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progressBar2);

                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            boolean downloading = true;

                            while (downloading) {

                                DownloadManager.Query q = new DownloadManager.Query();
                                q.setFilterById(downloadId);

                                Cursor cursor = manager.query(q);
                                cursor.moveToFirst();
//                                int bytes_downloaded = cursor.getInt(cursor
//                                        .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                                int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                                    downloading = false;
                                    Toast.makeText(context, completed, Toast.LENGTH_LONG).show();
                                }

//                                final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
//
//                                runOnUiThread(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        mProgressBar.setProgress((int) dl_progress);
//
//                                    }
//                                });

                                cursor.close();
                            }

                        }
                    }).start();
                }else{
                    forceConnect(context, result, wifiManager,
                            network1, network2, password,
                            finish, failed);
                }
            }
        }else{
            forceConnect(context, result, wifiManager,
                    network1, network2, password,
                    finish, failed);
        }
    }

    public void forceConnect(Context context, String result, WifiManager wifiManager,
                             String network1, String network2, String password,
                             String finish, String failed){
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        wifiManager.startScan();
        List <ScanResult> list = wifiManager.getScanResults();
        boolean flag = false;
        int netId = 0;

        for (ScanResult i : list){
//            if(i.SSID != null) {
//                if (i.SSID.equals("\"" + network1 + "\"")){
            if (i.SSID.contains(network1)){
                WifiConfiguration conf1 = new WifiConfiguration();
                conf1.SSID = "\"" + network1 + "\"";   // Please note the quotes. String should contain ssid in quotes
                netId = wifiManager.addNetwork(conf1);
                wifiManager.disconnect();
//                if (wifiManager.enableNetwork(netId, true)){
                    flag = true;
                    wifiManager.reconnect();
//                }else{
//                    flag = true;
//                    Toast.makeText(context, "wrong ssid", Toast.LENGTH_LONG).show();
//                    wifiManager.removeNetwork(netId);
                    conf1.preSharedKey = String.format("\"%s\"", password);
                    netId = wifiManager.addNetwork(conf1);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();
//                }
                break;
            }
//                else if (i.SSID.equals("\"" + network2 + "\"")){
            else if (i.SSID.contains(network2)){
                WifiConfiguration conf2 = new WifiConfiguration();
                conf2.SSID = "\"" + network2 + "\"";
                netId = wifiManager.addNetwork(conf2);
                wifiManager.disconnect();
                if (wifiManager.enableNetwork(netId, true)){
                    flag = true;
                    wifiManager.reconnect();
                }else{
                    flag = true;
                    wifiManager.removeNetwork(netId);
                    conf2.preSharedKey = String.format("\"%s\"", password);
                    netId = wifiManager.addNetwork(conf2);
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.reconnect();
                }
                break;
            }
//            }
        }
//        for( WifiConfiguration j : list ) {
//            if (j.SSID.contains(network1) || j.SSID.contains(network2)){
//                wifiManager.removeNetwork(j.networkId);
//                wifiManager.saveConfiguration();
//            }
//        }
//        list = wifiManager.getConfiguredNetworks();
//        for( WifiConfiguration i : list ) {
//        }
        if (flag){
//        ssid = connectionInfo.getSSID();
//        if (ssid.contains(network1) || ssid.contains(network2)){
            Toast.makeText(context, finish, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, failed, Toast.LENGTH_LONG).show();
        }
    }

}

//class DisconnectWifi extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context c, Intent intent) {
//        if(!intent.getParcelableExtra(wifi.EXTRA_NEW_STATE).toString().equals(SupplicantState.SCANNING)) wifi.disconnect();
//    }
//}
