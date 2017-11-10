package com.basic.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.activities.ContainerActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.basic.R;
import com.basic.activities.ImageSliderActivity;
import com.basic.models.response.maintenanceListResponse.Entry;
import com.basic.network.ApiClient;
import com.basic.utils.CustomProgressDialog;
import com.basic.utils.DateUtils;
import com.basic.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Maintenance Detail fragment to show the detail of any maintenance request.
 */
public class MaintenanceDetailFragment extends Fragment {
    ArrayList<String> listImageIds = new ArrayList<>();

    public MaintenanceDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.maintenance_detail));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_maintenance_detail, container, false);
        ((ContainerActivity)getActivity()).setBackground(R.drawable.ic_maintance_bg);
        Bundle bundle = getArguments();
        if (null != bundle) {
            Entry maintenanceDetail = (Entry) bundle.getParcelable("entryMaintenanceDetail");
            setData(rootView, maintenanceDetail);
            getMaintanceSliderImages(rootView,maintenanceDetail.getContent().getRecord().getRoomSpaceMaintenanceId());
        }else{
            ((LinearLayout)rootView.findViewById(R.id.image_slider_layout)).setVisibility(View.GONE);
        }
        return rootView;
    }

    private void setImages(View rootView) {
        DisplayImageOptions options;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
        .imageDownloader(new CustomImageDownloader(getActivity()))
        .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("StarRezUsername", "starrez.temp2");
        headers.put("StarRezPassword", "9591404d-1069-4290-b121-63b1a7e9e932");

        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .extraForDownloader(headers)
                .delayBeforeLoading(1000)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout image_slider_layout = (LinearLayout)rootView.findViewById(R.id.image_slider_layout);
        int deviceWidth = getActivity().getResources().getDisplayMetrics().widthPixels;

        for(int i=0;i<listImageIds.size();i++){
            View view = inflater.inflate(R.layout.partial_maintenance_deail_image_slider, null);
            final ImageView imageView = (ImageView)view.findViewById(R.id.slider_image_view);

            imageView.getLayoutParams().width = (int)(deviceWidth*0.30);
            imageView.getLayoutParams().height = (int)(deviceWidth*0.30);
            final int finalI1 = i;
            imageLoader.displayImage("https://starrez.centurionstudents.co.uk/StarRezREST/services/photo/RecordAttachment/"
                    +listImageIds.get(i).trim()
                    ,imageView,options);


            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ImageSliderActivity.class);
                intent.putExtra("pos", finalI1);
                intent.putStringArrayListExtra("imageList",listImageIds);
                startActivity(intent);
            }
        });

            //imageView.loadUrl("https://starrez.centurionstudents.co.uk/StarRezREST/services/photo/RecordAttachment/5969", headers);

            image_slider_layout.addView(view);
            image_slider_layout.invalidate();
        }
    }

    /**
     * Method to show the data on screen.
     *
     * @param rootView          : is the root view of this fragment, holds all the child views.
     * @param maintenanceDetail : Object to hold the details of this particular parcel.
     */
    private void setData(final View rootView, final Entry maintenanceDetail) {
        TextView maintenance_title_text_view = (TextView) rootView.findViewById(R.id.maintenance_title_text_view);
        TextView maintenance_date_reported_value_text_view = (TextView) rootView.findViewById(R.id.maintenance_date_reported_value_text_view);
        TextView maintenance_date_closed_value_text_view = (TextView) rootView.findViewById(R.id.maintenance_date_closed_value_text_view);
        TextView status_of_job__value_text_view = (TextView) rootView.findViewById(R.id.status_of_job__value_text_view);
        TextView category_value_text_view = (TextView) rootView.findViewById(R.id.category_value_text_view);
        TextView maintenance_description_value_text_view = (TextView) rootView.findViewById(R.id.maintenance_description_value_text_view);
        TextView maintenance_cause_value_text_view = (TextView) rootView.findViewById(R.id.maintenance_cause_value_text_view);
        TextView maintenance_comments_value_text_view = (TextView) rootView.findViewById(R.id.maintenance_comments_value_text_view);
        TextView maintenance_status_text_view = (TextView) rootView.findViewById(R.id.maintenance_status_text_view);

        View view_dotted_main_detail_divider_title = (View) rootView.findViewById(R.id.view_dotted_main_detail_divider_title);

        view_dotted_main_detail_divider_title.getBackground().setColorFilter(getResources().getColor
                (R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        if(TextUtils.isEmpty(maintenanceDetail.getContent().getRecord().getSubCategory())){
            maintenance_title_text_view.setText("No title available");
        }
        else {
            maintenance_title_text_view.setText(maintenanceDetail.getContent().getRecord().getSubCategory());
        }
        if(TextUtils.isEmpty(maintenanceDetail.getContent()
                .getRecord().getDateReported())){
            maintenance_date_reported_value_text_view.setText("NA");
        }
        else {
            maintenance_date_reported_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime(maintenanceDetail.getContent()
                    .getRecord().getDateReported().replace("T", " "))));
        }
        if(!TextUtils.isEmpty(maintenanceDetail.getContent().getRecord().getCompleteDate()) ||
                maintenanceDetail.getContent().getRecord().getStatus().equalsIgnoreCase("job completed")||
                maintenanceDetail.getContent().getRecord().getStatus().equalsIgnoreCase("Closed by student")){
            if(TextUtils.isEmpty(maintenanceDetail.getContent().
                    getRecord().getCompleteDate())){
                maintenance_date_closed_value_text_view.setText("NA");}

            else{maintenance_date_closed_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime(maintenanceDetail.getContent().
                    getRecord().getCompleteDate().replace("T", " "))));}


            maintenance_status_text_view.setText("Closed");
            maintenance_status_text_view.setAlpha(0.5f);
            maintenance_status_text_view.setClickable(false);
            maintenance_status_text_view.setEnabled(false);
        }else{
            maintenance_date_closed_value_text_view.setText("NA");
            maintenance_status_text_view.setText("Cancel");
        }

        status_of_job__value_text_view.setText(maintenanceDetail.getContent().getRecord().getStatus());
        category_value_text_view.setText(maintenanceDetail.getContent().getRecord().getMainCategory());
        maintenance_description_value_text_view.setText(maintenanceDetail.getContent().getRecord().getTitle());
        if(TextUtils.isEmpty(maintenanceDetail.getContent().getRecord().getCause())){
            maintenance_cause_value_text_view.setText("NA");
        }
        else{
            maintenance_cause_value_text_view.setText(maintenanceDetail.getContent().getRecord().getCause());
        }
        //maintenance_cause_value_text_view.setText(maintenanceDetail.getContent().getRecord().getCause());
        if (TextUtils.isEmpty(maintenanceDetail.getContent().getRecord().getComments())) {
            maintenance_comments_value_text_view.setText("NA");
        } else {
            maintenance_comments_value_text_view.setText(maintenanceDetail.getContent().getRecord().getComments());
        }


        maintenance_status_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getConfirmationDialogWithCallback(getActivity(),
                        getResources().getString(R.string.sure_want_close_job),
                        getResources().getString(R.string.alert),
                        getResources().getString(R.string.alert_yes_button_text),
                        getResources().getString(R.string.alert_no_button_text),
                        new DialogUtils.ResendCallBack() {
                            @Override
                            public void onClick() {
                                getCloseMaintenanceRequest(rootView, maintenanceDetail.getContent()
                                        .getRecord().getRoomSpaceMaintenanceId());
                            }

                            @Override
                            public void onResend() {
                                Intent intent = new Intent();
                                intent.putExtra("position",2);
                                getActivity().setResult(1,intent);
                                getActivity().finish();
                            }
                        });
            }
        });
    }

    private void getCloseMaintenanceRequest(final View rootView, final String roomSpaceMaintenanceId) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_maintienance_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "<RoomSpaceMaintenance>\n" +
                "    <JobStatus>Cancelled by Student</JobStatus>\n" +
                "<CompleteDate>"+DateUtils.getCurrentDate()+"</CompleteDate>"+
                "</RoomSpaceMaintenance>";

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.closeMaintenanceRequest(roomSpaceMaintenanceId, requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    try {
                        String s = response.body().string();
                        //System.out.println("response is:" + s);
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = XML.toJSONObject(s);
                            Log.d("JSON", jsonObj.toString());
                            JSONObject jsonObject = jsonObj.getJSONObject("feed");
                            try {
                                String jobStatus = jsonObject.getJSONObject("entry").getJSONObject("content")
                                        .getJSONObject("RoomSpaceMaintenance").getString("JobStatus");
                                handleCloseRequestResponse(jobStatus, rootView);
                                Log.d("job status is", jobStatus);
                            } catch (JSONException e) {
                            }
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                            handleWebserviceFailureResponse(null, rootView, roomSpaceMaintenanceId);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        handleWebserviceFailureResponse(null, rootView, roomSpaceMaintenanceId);
                    }
                    //setData(rootView, entryArrayFilteredList);
                } else {
                    handleWebserviceFailureResponse(null, rootView, roomSpaceMaintenanceId);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(null, rootView, roomSpaceMaintenanceId);
            }
        });
    }

    private void handleCloseRequestResponse(String jobStatus, final View rootView) {
        if (jobStatus.equalsIgnoreCase("Cancelled by Student")) {
            DialogUtils.getConfirmationDialogAlert(getActivity(),
                    getResources().getString(R.string.maintenance_request_closed),
                    getResources().getString(R.string.alert),
                    getResources().getString(R.string.ok),
                    new DialogUtils.successCallback() {
                        @Override
                        public void onClick() {


                            Intent intent = new Intent();
                            intent.putExtra("position",2);
                            getActivity().setResult(1,intent);
                            getActivity().finish();
                        }
                    });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(),
                    getResources().getString(R.string.some_error),
                    getResources().getString(R.string.alert),
                    getResources().getString(R.string.ok));
        }
    }


    /**
     * Method to handle the all situation when web service calling get failed.
     *
     * @param t                      : Error thrown by network libray in web service calling.
     * @param rootView               : root view of the fragment.
     * @param roomSpaceMaintenanceId
     */
    private void handleWebserviceFailureResponse(Throwable t, final View rootView, final String roomSpaceMaintenanceId) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getCloseMaintenanceRequest(rootView, roomSpaceMaintenanceId);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(), getResources().getString(R.string.some_error),
                    getResources().getString(R.string.alert), getResources().getString(R.string.ok));
        }
    }

    private void getMaintanceSliderImages(final View rootView, final String roomSpaceMaintenanceId) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_maintienance_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "SELECT [RecordAttachmentID] FROM [RecordAttachment] WHERE [TableId]" +
                " = "+roomSpaceMaintenanceId+" and [TableName] = 'RoomSpaceMaintenance' ";

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.callAPI(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    try {
                        String s = response.body().string();
                        //System.out.println("response is:" + s);
                        JSONObject jsonObj = null;

                        try {
                            jsonObj = XML.toJSONObject(s);
                            Log.d("JSON", jsonObj.toString());
                            JSONObject jsonObject = jsonObj.getJSONObject("feed");
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = jsonObject.getJSONArray("entry");
                                Gson gson = new Gson();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    com.basic.models.response.maintaincesliderids.Entry feed
                                            = gson.fromJson(jsonObject1.toString(), com.basic
                                            .models.response.maintaincesliderids.Entry.class);
                                    listImageIds.add(feed.getContent().getRecord().
                                            getmRecordAttachmentID());
                                }

                                setImages(rootView);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                                Gson gson = new Gson();
                                com.basic.models.response.maintaincesliderids.Entry feed
                                        = gson.fromJson(jsonObject1.toString(), com.basic
                                        .models.response.maintaincesliderids.Entry.class);
                                listImageIds.add(feed.getContent().getRecord().
                                        getmRecordAttachmentID());
                                setImages(rootView);
                            }
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        handleWebserviceImageIdsFailureResponse(null, rootView, roomSpaceMaintenanceId);
                    }
                    //setData(rootView, entryArrayFilteredList);
                } else {
                    handleWebserviceImageIdsFailureResponse(null, rootView, roomSpaceMaintenanceId);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceImageIdsFailureResponse(null, rootView, roomSpaceMaintenanceId);
            }
        });
    }


    /**
     * Method to handle the all situation when web service calling get failed.
     *
     * @param t                      : Error thrown by network libray in web service calling.
     * @param rootView               : root view of the fragment.
     * @param roomSpaceMaintenanceId
     */
    private void handleWebserviceImageIdsFailureResponse(Throwable t, final View rootView, final String roomSpaceMaintenanceId) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getMaintanceSliderImages(rootView, roomSpaceMaintenanceId);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(), getResources().getString(R.string.some_error),
                    getResources().getString(R.string.alert), getResources().getString(R.string.ok));
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
