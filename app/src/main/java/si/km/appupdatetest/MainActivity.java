package si.km.appupdatetest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    void onCreateImportantWork() {
        final boolean isMostRecent = VersionRetriever.isMostRecent(getApplicationContext());
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isMostRecent) {
                    Toast.makeText(MainActivity.this, "Up to date", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Update available", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, AppUpdaterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //region  // unimportant boilerplate //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doUnimportantBoilerplate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                onCreateImportantWork();
            }
        }).start();
    }

    private void doUnimportantBoilerplate() {
        setContentView(R.layout.activity_main);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
