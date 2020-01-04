package si.km.appupdatetest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;



public class VersionRetriever {

    static final String TAG = "VersionRetriever_K";


    public static boolean isMostRecent(Context context) {

        long appVersion = getAppVersion(context);
        int latestVersion = Get();
        if (appVersion < latestVersion)
            return false;
        return true;
    }

    public static int Get() {
        try {
            String version = GetUrl(GGlobals.LATEST_VERSION_ADDRESS);
            int ver = Integer.parseInt(version);
            return ver;
        } catch (Exception ex) {
            return -1;
        }
    }

    private static String GetUrl(String url) throws IOException {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            get.addHeader("User-Agent", "Mozilla/5.0");
            //get.addHeader("content-type", "application/json");

            Log.d(TAG, url);
            CloseableHttpResponse response = null;
            try {
                response = client.execute(get);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HttpEntity entity = null;
            if (response != null) {
                entity = response.getEntity();
            } else {
                Log.d(TAG, "response was empty");
            }

            InputStream inputStream = null;
            try {
                inputStream = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String content = IOUtils.toString(inputStream, "UNICODE");
            Log.d(TAG, content);
            return content;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage() + ex.getStackTrace());
            throw ex;
        }
    }

    private static long getAppVersion(Context context) {
        long latestVersion = -9219423;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            latestVersion = pInfo.getLongVersionCode();
            return latestVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return latestVersion;
    }

}
