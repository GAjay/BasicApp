package com.basic.fragment;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.basic.R;
import com.basic.activities.ContainerActivity;

import com.basic.adapters.AvailabilityResourceDetailAdapter;
import com.basic.models.response.resourceavailabilityresponse.Entry;
import com.basic.models.response.resourceavailabilityresponse.Record;
import com.basic.network.ApiClient;
import com.basic.utils.CustomProgressDialog;

import com.basic.utils.DateUtils;
import com.basic.utils.DialogUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ajay on 25/1/17.
 */
public class ResourceAvailabilityDetailListing extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.book__rersource_detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_resource_availability_detail_listing, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.resource_title_text_view);
        View view = (View) rootView.findViewById(R.id.view_dotted_resource_detail_divider_title);
        Bundle bundle = getArguments();
        String dateFrom = bundle.getString("dateFrom");
        String dateTo = bundle.getString("dateTo");
        String resourceId = bundle.getString("resourceId");
        String resourceName = bundle.getString("resourceName");
        textView.setText(resourceName);
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        ((ContainerActivity) getActivity()).setBackground(R.drawable.ic_resource_bg);
        if (null != bundle) {
            callWebService(resourceId, dateFrom, dateTo, rootView);
        }
        return rootView;
    }

    public void callWebService(final String resourceId, final String dateFrom, String dateTo, final View rootView) {

        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_parcel_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    final ArrayList<Record> entryArrayList = new ArrayList<>();
        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            dateTo = output.format(formatter.parse(
                    dateTo.trim()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "SELECT [ResourceBookingID],[ResourceID],[ResourceBookingStatusEnum]" +
                ",[ResourceBooking].[Description],[DateStart],[DateEnd],[Resource].[Description] " +
                "FROM [ResourceBooking] LEFT JOIN [Resource] ON [Resource].[ResourceID]=" +
                "[ResourceBooking].[ResourceID] WHERE [ResourceBooking].[ResourceID] =" +
                resourceId+" AND (([DateStart] >="+DateUtils.getTimeInServerTimeZone(dateFrom)
                +":00"+" AND [DateEnd] <="+dateTo +"T"+"23:55" +":00"+") OR ([DateStart] <="+
                dateTo +"T"+"23:55:00"+" AND"+" [DateEnd] >= "+DateUtils.getTimeInServerTimeZone(dateFrom) +
                ":00))";

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.getParcelList(requestBody);
        final String finalDateTo1 = dateTo;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    entryArrayList.clear();
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
                                    com.basic.models.response.resourceavailabilityresponse.Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                                    entryArrayList.add(feed.getContent().getRecord());
                                    Log.d("response ::::", feed.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                                Gson gson = new Gson();
                                Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);

                                entryArrayList.add(feed.getContent().getRecord());
                                Log.d("response ::::", feed.toString());
                            }
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setData(rootView, entryArrayList);
                } else {
                    handleWebserviceFailureResponse(null, resourceId, rootView, dateFrom, finalDateTo1);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(t, resourceId,rootView,dateFrom, finalDateTo1);
            }
        });

    }

    private void setData(View rootView, ArrayList<Record> entryArrayList) {
        ListView resourceDatail = (ListView) rootView.findViewById(R.id.resource_availability_detail);
        AvailabilityResourceDetailAdapter adapter ;
        adapter = new AvailabilityResourceDetailAdapter(getActivity(), entryArrayList);
        resourceDatail.setAdapter(adapter);
        ImageView newResourceRequestFab =
                (ImageView) rootView.
                        findViewById(R.id.resource_request_fab);
        newResourceRequestFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            }
        });
    }

    /**
     * Method to handle the all situation when web service calling get failed.
     * @param t        : Error thrown by network libray in web service calling.
     * @param resourceId
     * @param rootView : root view of the fragment.
     * @param dateFrom
     * @param dateTo
     */
    private void handleWebserviceFailureResponse(Throwable t, final String resourceId, final View rootView, final String dateFrom, final String dateTo) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    callWebService(resourceId,dateFrom,dateTo,rootView);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(), getResources().getString(R.string.some_error),
                    getResources().getString(R.string.alert), getResources().getString(R.string.ok));
        }
    }

}
