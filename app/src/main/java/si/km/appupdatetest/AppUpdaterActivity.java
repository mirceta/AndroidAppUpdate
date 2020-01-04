package si.km.appupdatetest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;

public class AppUpdaterActivity extends AppCompatActivity {

    static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boilerplate();

        if (hasWriteExternalStoragePermission() && hasReadExternalStoragePermission()) {
            downloadAndInstallUpdate();
        } else {
            PermissionUtils.requestPermission(this, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
        }
    }

    private void downloadAndInstallUpdate() {
        UpdateApp some = new UpdateApp();
        some.setContext(getApplicationContext());
        some.execute(GGlobals.LATEST_APK);
    }

    // region // permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION && PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtils.requestPermission(this, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION,
                    Manifest.permission.READ_EXTERNAL_STORAGE, true);
        }

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION && PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            downloadAndInstallUpdate();
        }

    }

    private boolean hasWriteExternalStoragePermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasReadExternalStoragePermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
    }
    //endregion

    //region // boilerplate //
    private void boilerplate() {
        setContentView(R.layout.activity_app_updater);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //endregion

}
