package br.com.zephyrplace.ontherun;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tmarques on 23/02/16.
 */
public class DownloadAppAsyncTask extends AsyncTask<URL, Integer, String> {

    private Context mContext;
    private DownloadAppAsyncTaskCallbacks mCallback;

    interface DownloadAppAsyncTaskCallbacks {
        void onPreExecute();

        void onProgressUpdate(int percent);

        void onCancelled();

        void onPostExecute(String fileAbsolutePath);
    }

    public DownloadAppAsyncTask(Context context, DownloadAppAsyncTaskCallbacks callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        mCallback.onPreExecute();

    }

    @Override
    protected String doInBackground(URL... urls) {

        int count;
        try {

            URL url = urls[0];
            String[] files = url.getFile().split("/");
            File f = new File(mContext.getExternalCacheDir(), files[files.length-1]);

            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream(f);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
            return f.getAbsolutePath();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mCallback.onProgressUpdate(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.onPostExecute(result);
    }


}
