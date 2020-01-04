package si.km.appupdatetest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateApp extends AsyncTask<String,Void,Void> {

    private Context context;
    public void setContext(Context contextf){
        context = contextf;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            //c.setDoOutput(true);
            c.connect();

            method2(c);

        } catch (Exception e) {
            Log.e("UpdateAPP", "Update error! " + e.getMessage());
        }
        return null;
    }

    private void method1(HttpURLConnection c) throws Exception {
        File updateFile = new File(Environment.getExternalStorageDirectory() + "/download/si.km.appupdatetest.apk");
        String AUTHORITY = "si.km.appupdatetest.provider";
        Uri apkUri = FileProvider.getUriForFile(context, AUTHORITY, updateFile);

        writeToFile(c, updateFile.getAbsolutePath());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        apkUri = Uri.fromFile(updateFile);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION); // without this flag an
        context.startActivity(intent);
    }

    private void method2(HttpURLConnection c) throws Exception {

        String fileName = "update.apk";
        File directory = context.getExternalFilesDir(null);
        File file = new File(directory, fileName);
        writeToFile(c, file.getAbsolutePath());
        Uri fileUri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider",
                    file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    private void writeToFile(HttpURLConnection c, String file) throws IOException {
        // write app
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = c.getInputStream();
        byte[] buffer = new byte[1024];
        int len1 = 0;
        while ((len1 = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len1);
        }
        fos.close();
        is.close();
    }
}

