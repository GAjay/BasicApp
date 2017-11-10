package com.basic.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.basic.R;
import com.basic.activities.DashboardActivity;
import com.basic.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonWebViewFragment extends Fragment {

    private ProgressDialog progressDialog;
    private WebView mWebview;
    int count = 1;
    public String starturl = null;
    private String url;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle fragmentBundle = this.getArguments();
        getActivity().setTitle(fragmentBundle.getInt("title"));
        View rootView = inflater.inflate(R.layout.common_web_view, container, false);
        ((DashboardActivity)getActivity()).setBackground(R.drawable.ic_resource_bg);
        progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_parcel_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        mWebview = (WebView) rootView.findViewById(R.id.webView);
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl(fragmentBundle.getString("url"));
        mWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        return rootView;
    }

    public class WebViewClient extends android.webkit.WebViewClient {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            CommonWebViewFragment.this.url=url;
            if (request.getUrl().toString().startsWith("tel:")){
                if (checkPermissions()){
                    callNumber(request.getUrl().toString());
                }
            }else {
                view.loadUrl(request.getUrl().toString());
            }

            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            CommonWebViewFragment.this.url=url;

            if (url.startsWith("tel:")){
                if (checkPermissions()){
                    callNumber(url);
                }
            }else if (url.startsWith("mailto:")){
                openEmail(url);
            }

            else {
                view.loadUrl(url);
            }
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            starturl = url;
            if(url.startsWith("tel:") || url.startsWith("mailto:")){


            }
            else{
                super.onPageStarted(view, url, favicon);
            }
            //super.onPageStarted(view, url, favicon);
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            progressDialog.dismiss();
            count++;
            Log.d("starturl", starturl);
            Log.d("My url", "Url isfdf" + url);
            if (!(url.contains("http://www.dwellstudent.co.uk/en/information/")
                    || url.contains("http://www.dwellstudent.co.uk/en/event/")
                    || url.contains("http://www.dwellstudent.co.uk/en/help/"))) {

                ((DashboardActivity) getActivity()).setActionbar(true);
            }
            else if(url.startsWith("tel:") || url.startsWith("mailto:")){


            }

            else {
                ((DashboardActivity) getActivity()).setActionbar(false);

            }
            super.onPageFinished(view, url);
        }

    }

    private void callNumber(String url) {
        // Note that this ACTION_CALL requires permission
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(url));
        startActivity(callIntent);
    }

    /**
     * Method to check all permissions are granted or not
     * @return : if any required permission is not granted then return false.
     */
    public boolean checkPermissions() {
        int result;
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    private void openEmail(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse(url);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isPemissionDenied=false;
        if (requestCode == 100) {

            for (int i:grantResults){
                if (i==PackageManager.PERMISSION_DENIED){
                    isPemissionDenied=true;
                    break;
                }
            }

            if (!isPemissionDenied){
                callNumber(url);
            }

        }
    }
    public void onBackPressedFragment() {
        if (mWebview.canGoBack())
            mWebview.goBack();
        else {
            ((DashboardActivity) getActivity()).superBackPressed();
        }

    }




}
