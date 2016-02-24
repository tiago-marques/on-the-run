package br.com.zephyrplace.ontherun;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends BaseActivity implements DownloadAppAsyncTask.DownloadAppAsyncTaskCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_fragment, new MainActivityFragment(), MainActivityFragment.class.getName())
                        .commit();
            }
        }, 4000);

    }

    @Override
    public void onPreExecute() {
        showLoading();
    }

    @Override
    public void onProgressUpdate(int percent) {
        ((TextView) findViewById(R.id.loadingPercent)).setText(percent + " %");
    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(String fileAbsolutePath) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("ONTHERUN", true);
        intent.setDataAndType(Uri.fromFile(new File(fileAbsolutePath)), "application/vnd.android.package-archive");
        startActivityForResult(intent, 5000);

    }

}
