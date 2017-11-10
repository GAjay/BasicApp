package com.basic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;

import com.basic.utils.SendLogActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 13/9/16.
 */
public class AppController extends Application {
    private static com.basic.AppController controller;

    public static com.basic.AppController getController() {
        return controller;
    }
    //public static ImageLoaderConfiguration config;
    public static int deviceWidth;


    @Override
    public void onCreate() {
        super.onCreate();

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        deviceWidth = displayMetrics.widthPixels;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("StarRezUsername", "starrez.temp2");
        headers.put("StarRezPassword", "9591404d-1069-4290-b121-63b1a7e9e932");

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .imageDownloader(new CustomImageDownloader(getApplicationContext()))
                .memoryCacheExtraOptions((int)(deviceWidth/2),(int)(deviceWidth/2)) // default = device screen dimensions
                .diskCacheExtraOptions((int)(deviceWidth/2),(int)(deviceWidth/2), null)
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(500)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) //
                .build();

        ImageLoader.getInstance().init(config);

    }

    /** Method will be get called when some uncaught exception occur in application.
     * @param thread : current thread.
     * @param e : error object.
     */
    public void handleUncaughtException(Thread thread, Throwable e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream( baos );
        e.printStackTrace(stream);
        stream.flush();
        try {
            FileOutputStream trace = openFileOutput("stack.trace", Context.MODE_PRIVATE);
            trace.write(new String(baos.toByteArray()).getBytes());
            trace.close();

            Intent intent = new Intent(getApplicationContext(), SendLogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(1);

        } catch(IOException ioe) {
            //System.exit(1);
        }finally {
            //System.exit(1);
        }
    }


    public class CustomImageDownloader extends BaseImageDownloader {

        public CustomImageDownloader(Context context) {
            super(context);
        }

        public CustomImageDownloader(Context context, int connectTimeout, int readTimeout) {
            super(context, connectTimeout, readTimeout);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
            HttpURLConnection conn = super.createConnection(url, extra);
            Map<String, String> headers = (Map<String, String>) extra;
            headers.put("StarRezUsername", "starrez.temp2");
            headers.put("StarRezPassword", "9591404d-1069-4290-b121-63b1a7e9e932");
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            return conn;
        }
    }

}
