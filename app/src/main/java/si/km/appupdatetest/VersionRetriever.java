package si.km.appupdatetest;

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

    public VersionRetriever() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String version = Get(GGlobals.CURRENT_VERSION_ADDRESS);
                        int ver = Integer.parseInt(version);
                        Log.d(TAG, "some");
                    }
                    catch (Exception ex) {
                        Log.d(TAG, "some");
                    }
                }
            }).start();
    }

    private static String Get(String url) throws IOException {
        try {
            Log.d(TAG, "KURAC1");
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

            Log.d(TAG, "KURAC3");

            HttpEntity entity = null;
            if (response != null) {
                entity = response.getEntity();
            } else {
                Log.d(TAG, "response was empty");
            }

            Log.d(TAG, "KURAC4");

            InputStream inputStream = null;
            try {
                inputStream = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "KURAC5");

            String content = IOUtils.toString(inputStream, "UNICODE");
            Log.d(TAG, content);
            return content;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage() + ex.getStackTrace());
            throw ex;
        }
    }

}
