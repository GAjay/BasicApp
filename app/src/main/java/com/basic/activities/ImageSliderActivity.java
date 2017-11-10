package com.basic.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.basic.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageSliderActivity extends AppCompatActivity {
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        ImageView crossImage = (ImageView) findViewById(R.id.ic_cross);
        crossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ArrayList<String> imageList = getIntent().getStringArrayListExtra("imageList");
        int pos = getIntent().getIntExtra("pos",0);

        DisplayImageOptions options;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ImageSliderActivity.this)
                .imageDownloader(new CustomImageDownloader(ImageSliderActivity.this))
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("StarRezUsername", "starrez.temp2");
        headers.put("StarRezPassword", "9591404d-1069-4290-b121-63b1a7e9e932");

        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .extraForDownloader(headers)
                .delayBeforeLoading(1000)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
        initSlider(imageList,pos);
    }

    private void initSlider(ArrayList<String> imageList,int pos) {


        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);


        final ImagePagerAdapter adapter = new ImagePagerAdapter(imageList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);


    }


    private class ImagePagerAdapter extends PagerAdapter {
        private ArrayList<String> pagerList;

        public ImagePagerAdapter(ArrayList<String> pagerItemList) {
            pagerList = pagerItemList;
        }


        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public int getCount() {
            return this.pagerList.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final ImageView imageView = new ImageView(ImageSliderActivity.this);
            imageView.setImageResource(R.drawable.ic_placeholder);

            final int padding = 1;
            imageView.setPadding(padding, padding, padding, padding);
            String url = ("https://starrez.centurionstudents.co.uk/StarRezREST/services/photo/RecordAttachment/"
                    +pagerList.get(position).trim());

            imageLoader.displayImage(url,imageView);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view == ((ImageView) object);
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
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            return conn;
        }
    }

}
