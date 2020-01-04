[1mdiff --git a/app/build.gradle b/app/build.gradle[m
[1mindex d1449ee..67dd39c 100644[m
[1m--- a/app/build.gradle[m
[1m+++ b/app/build.gradle[m
[36m@@ -27,8 +27,6 @@[m [mdependencies {[m
     androidTestImplementation 'androidx.test:runner:1.1.1'[m
     androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'[m
 [m
[31m-[m
[31m-    implementation 'com.squareup.okhttp3:okhttp:3.14.1'[m
     implementation 'cz.msebera.android:httpclient:4.4.1.1'[m
 [m
     implementation 'commons-net:commons-net:3.5'[m
[1mdiff --git a/app/src/main/AndroidManifest.xml b/app/src/main/AndroidManifest.xml[m
[1mindex c0addf9..f40c312 100644[m
[1m--- a/app/src/main/AndroidManifest.xml[m
[1m+++ b/app/src/main/AndroidManifest.xml[m
[36m@@ -2,6 +2,8 @@[m
 <manifest xmlns:android="http://schemas.android.com/apk/res/android"[m
     package="si.km.appupdatetest">[m
 [m
[32m+[m[32m    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />[m
[32m+[m[32m    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />[m
     <uses-permission android:name="android.permission.INTERNET" />[m
 [m
     <application[m
[36m@@ -11,6 +13,10 @@[m
         android:roundIcon="@mipmap/ic_launcher_round"[m
         android:supportsRtl="true"[m
         android:theme="@style/AppTheme">[m
[32m+[m[32m        <activity[m
[32m+[m[32m            android:name=".AppUpdaterActivity"[m
[32m+[m[32m            android:label="@string/title_activity_app_updater"[m
[32m+[m[32m            android:theme="@style/AppTheme.NoActionBar"></activity>[m
         <activity[m
             android:name=".MainActivity"[m
             android:label="@string/app_name"[m
[36m@@ -22,7 +28,15 @@[m
             </intent-filter>[m
         </activity>[m
 [m
[31m-[m
[32m+[m[32m        <provider[m
[32m+[m[32m            android:name="androidx.core.content.FileProvider"[m
[32m+[m[32m            android:authorities="${applicationId}.provider"[m
[32m+[m[32m            android:exported="false"[m
[32m+[m[32m            android:grantUriPermissions="true">[m
[32m+[m[32m            <meta-data[m
[32m+[m[32m                android:name="android.support.FILE_PROVIDER_PATHS"[m
[32m+[m[32m                android:resource="@xml/provider_paths" />[m
[32m+[m[32m        </provider>[m
     </application>[m
 [m
[31m-</manifest>[m
[32m+[m[32m</manifest>[m
\ No newline at end of file[m
[1mdiff --git a/app/src/main/java/si/km/appupdatetest/AppUpdaterActivity.java b/app/src/main/java/si/km/appupdatetest/AppUpdaterActivity.java[m
[1mindex c438077..3d1aac1 100644[m
[1m--- a/app/src/main/java/si/km/appupdatetest/AppUpdaterActivity.java[m
[1m+++ b/app/src/main/java/si/km/appupdatetest/AppUpdaterActivity.java[m
[36m@@ -1,17 +1,24 @@[m
 package si.km.appupdatetest;[m
 [m
[32m+[m[32mimport android.Manifest;[m
[32m+[m[32mimport android.content.pm.PackageManager;[m
 import android.os.Bundle;[m
 [m
 import com.google.android.material.floatingactionbutton.FloatingActionButton;[m
 import com.google.android.material.snackbar.Snackbar;[m
 [m
[32m+[m[32mimport androidx.annotation.NonNull;[m
 import androidx.appcompat.app.AppCompatActivity;[m
 import androidx.appcompat.widget.Toolbar;[m
[32m+[m[32mimport androidx.core.content.ContextCompat;[m
 [m
 import android.view.View;[m
 [m
 public class AppUpdaterActivity extends AppCompatActivity {[m
 [m
[32m+[m[32m    static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;[m
[32m+[m[32m    static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 2;[m
[32m+[m
     @Override[m
     protected void onCreate(Bundle savedInstanceState) {[m
         super.onCreate(savedInstanceState);[m
[36m@@ -27,6 +34,49 @@[m [mpublic class AppUpdaterActivity extends AppCompatActivity {[m
                         .setAction("Action", null).show();[m
             }[m
         });[m
[32m+[m
[32m+[m
[32m+[m[32m        if (hasWriteExternalStoragePermission() && hasReadExternalStoragePermission()) {[m
[32m+[m[32m            downloadAndInstallUpdate();[m
[32m+[m[32m        } else {[m
[32m+[m[32m            PermissionUtils.requestPermission(this, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION,[m
[32m+[m[32m                    Manifest.permission.WRITE_EXTERNAL_STORAGE, true);[m
[32m+[m[32m        }[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m
[32m+[m[32m    private void downloadAndInstallUpdate() {[m
[32m+[m[32m        UpdateApp some = new UpdateApp();[m
[32m+[m[32m        some.setContext(getApplicationContext());[m
[32m+[m[32m        some.execute(GGlobals.LATEST_APK);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    // region // permissions[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,[m
[32m+[m[32m                                           @NonNull int[] grantResults) {[m
[32m+[m[32m        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION && PermissionUtils.isPermissionGranted(permissions, grantResults,[m
[32m+[m[32m                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {[m
[32m+[m[32m            PermissionUtils.requestPermission(this, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION,[m
[32m+[m[32m                    Manifest.permission.READ_EXTERNAL_STORAGE, true);[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION && PermissionUtils.isPermissionGranted(permissions, grantResults,[m
[32m+[m[32m                    Manifest.permission.READ_EXTERNAL_STORAGE)) {[m
[32m+[m[32m            downloadAndInstallUpdate();[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private boolean hasWriteExternalStoragePermission() {[m
[32m+[m[32m        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)[m
[32m+[m[32m                == PackageManager.PERMISSION_GRANTED);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private boolean hasReadExternalStoragePermission() {[m
[32m+[m[32m        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)[m
[32m+[m[32m                == PackageManager.PERMISSION_GRANTED);[m
     }[m
[32m+[m[32m    //endregion[m
 [m
 }[m
[1mdiff --git a/app/src/main/java/si/km/appupdatetest/GGlobals.java b/app/src/main/java/si/km/appupdatetest/GGlobals.java[m
[1mindex 859cc45..d6496e0 100644[m
[1m--- a/app/src/main/java/si/km/appupdatetest/GGlobals.java[m
[1m+++ b/app/src/main/java/si/km/appupdatetest/GGlobals.java[m
[36m@@ -2,5 +2,6 @@[m [mpackage si.km.appupdatetest;[m
 [m
 public class GGlobals {[m
 [m
[31m-    public static String CURRENT_VERSION_ADDRESS = "https://raw.githubusercontent.com/mirceta/data/master/readme.md";[m
[32m+[m[32m    public static String LATEST_VERSION_ADDRESS = "https://raw.githubusercontent.com/mirceta/data/master/readme.md";[m
[32m+[m[32m    public static String LATEST_APK = "https://github.com/mirceta/data/blob/master/app-debug.apk?raw=true";[m
 }[m
[1mdiff --git a/app/src/main/java/si/km/appupdatetest/MainActivity.java b/app/src/main/java/si/km/appupdatetest/MainActivity.java[m
[1mindex 7ca5b96..f2c0f5a 100644[m
[1m--- a/app/src/main/java/si/km/appupdatetest/MainActivity.java[m
[1m+++ b/app/src/main/java/si/km/appupdatetest/MainActivity.java[m
[36m@@ -1,5 +1,6 @@[m
 package si.km.appupdatetest;[m
 [m
[32m+[m[32mimport android.content.Intent;[m
 import android.os.Bundle;[m
 [m
 import com.google.android.material.floatingactionbutton.FloatingActionButton;[m
[36m@@ -11,6 +12,7 @@[m [mimport androidx.appcompat.widget.Toolbar;[m
 import android.view.View;[m
 import android.view.Menu;[m
 import android.view.MenuItem;[m
[32m+[m[32mimport android.widget.Toast;[m
 [m
 public class MainActivity extends AppCompatActivity {[m
 [m
[36m@@ -20,7 +22,21 @@[m [mpublic class MainActivity extends AppCompatActivity {[m
         setContentView