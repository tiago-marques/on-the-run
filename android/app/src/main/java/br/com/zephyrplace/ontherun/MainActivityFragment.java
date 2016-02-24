package br.com.zephyrplace.ontherun;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private DownloadAppAsyncTask.DownloadAppAsyncTaskCallbacks mCallback;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (DownloadAppAsyncTask.DownloadAppAsyncTaskCallbacks) activity;
    }

    private PackageManager manager;
    private List<AppDetail> apps;

    private void loadApps(View v) throws PackageManager.NameNotFoundException {
        manager = getActivity().getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(getString(R.string.app_category));

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        if (availableActivities.size() == 0) {
            showNoAppMessage(v);
        } else {
            for (ResolveInfo ri : availableActivities) {
                AppDetail app = new AppDetail();
                app.packageName = ri.activityInfo.packageName;
                app.name = ri.loadLabel(manager);
                app.icon = ri.activityInfo.loadIcon(manager);
                PackageInfo info = manager.getPackageInfo(app.packageName, 0);
                String version = info.versionName;
                app.label = version;
                apps.add(app);
            }
            if (hasUpdates())
                showUpdateMessage(v);
        }
    }

    private RecyclerView list;

    private void loadListView(final LayoutInflater inflater, View v) {
        list = (RecyclerView) v.findViewById(R.id.recycler);

        AppsAdapter appsAdapter = new AppsAdapter(apps.toArray());

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(appsAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        try {
            loadApps(v);
            loadListView(inflater, v);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return v;
    }

    private void showNoAppMessage(View v) {
        v.findViewById(R.id.messages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new DownloadAppAsyncTask(
                        view.getContext(), mCallback).execute(
                        new URL("http://tempsend.com/6E79B9665C/3BA5/appdebug.apk"));
                } catch (MalformedURLException e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });
        showMessage(v, getString(R.string.messageNoAppTitle),
                getString(R.string.messageNoAppContent));
    }

    private void showUpdateMessage(View v) {
        v.findViewById(R.id.messages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMessage(view);
            }
        });
        showMessage(v, getString(R.string.messageUpdateTitle),
                getString(R.string.messageUpdateContent));
    }

    private void showMessage(View v, String title, String message) {
        v.findViewById(R.id.messages).setVisibility(View.VISIBLE);

        TextView titleView = (TextView) v.findViewById(R.id.messageTitle);
        titleView.setText(title);

        TextView messageView = (TextView) v.findViewById(R.id.messageContent);
        messageView.setText(message);
    }

    private void hideMessage(View v) {
        v.findViewById(R.id.messages).setVisibility(View.GONE);

        TextView titleView = (TextView) v.findViewById(R.id.messageTitle);
        titleView.setText("");

        TextView messageView = (TextView) v.findViewById(R.id.messageContent);
        messageView.setText("");
    }

    private boolean hasUpdates() {
        return true;
    }

}
