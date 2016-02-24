package br.com.zephyrplace.ontherun;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by tmarques on 23/02/16.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG_LOADING_FRAGMENT = "loading_fragment";

    private LoadingFragment mLoadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        mLoadingFragment = (LoadingFragment) fm.findFragmentByTag(TAG_LOADING_FRAGMENT);

        if (mLoadingFragment == null) {
            mLoadingFragment = new LoadingFragment();
            fm.beginTransaction()
                    .replace(R.id.content_fragment, mLoadingFragment, TAG_LOADING_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }

    }

    protected void showLoading(){
        FragmentManager fm = getSupportFragmentManager();
        mLoadingFragment = (LoadingFragment) fm.findFragmentByTag(TAG_LOADING_FRAGMENT);

        if (mLoadingFragment == null) {
            mLoadingFragment = new LoadingFragment();
            fm.beginTransaction()
                    .replace(R.id.content_fragment, mLoadingFragment, TAG_LOADING_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }else{
            fm.beginTransaction()
                    .replace(R.id.content_fragment, mLoadingFragment, TAG_LOADING_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
