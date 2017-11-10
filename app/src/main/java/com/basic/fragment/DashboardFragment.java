package com.basic.fragment;


import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.gson.Gson;
import com.basic.R;
import com.basic.activities.DashboardActivity;
import com.basic.interfaces.RoomsSpaceCallback;
import com.basic.models.response.maintenanceListResponse.Entry;
import com.basic.network.ApiClient;
import com.basic.utils.AppConstants;
import com.basic.utils.CustomProgressDialog;
import com.basic.utils.DateUtils;
import com.basic.utils.DialogUtils;
import com.basic.utils.SharedPreference;
import com.basic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener {

    View rootView;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.dashboard);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((DashboardActivity) getActivity()).setBackground(R.drawable.ic_dashboard_login_bg);
        final ArrayList<Entry> entryArrayList =
                new ArrayList<>();

        final ArrayList<com.basic.models.response.parcellistresponse.Entry> parcelEntryArrayList =
                new ArrayList<>();
        entryArrayList.clear();

        parcelEntryArrayList.clear();

        enableSecondLayout(rootView);

        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_maintienance_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);


        ((DashboardActivity) getActivity()).setActionbar(false);


        return rootView;
    }

    /**
     * Method for get data of Maintenance list
     *
     * @param rootView
     * @param progressDialog       : Object of common progress bar
     * @param entryArrayList       : Array list of Maintenance list
     * @param parcelEntryArrayList : Array list of Parcel list
     */

    private void getMaintenanceItemList(final View rootView, final ProgressDialog progressDialog, final ArrayList<Entry> entryArrayList, final ArrayList<com.basic.models.response.parcellistresponse.Entry> parcelEntryArrayList) {
        entryArrayList.clear();

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "SELECT rm.[JobStatus] as status,rm.[RoomSpaceMaintenanceID], rm.[Description] as title, " +
                "rm.[Cause], rm.[DateReported], rm.[CompleteDate], rsm.[Description] AS sub_category , rsc.[Description]" +
                " AS main_category, rm.[OccupantPresent] , rm.[RepairDescription] as description, rm.[OccupantPresentReason]" +
                " as comments FROM [Booking] as bk LEFT JOIN [RoomSpaceMaintenance] as rm ON bk.[RoomSpaceID] " +
                "= rm.[RoomSpaceID] LEFT JOIN [RoomSpaceMaintenanceItem] AS rsm ON rsm.[RoomSpaceMaintenanceItemID] = " +
                "rm.[RoomSpaceMaintenanceItemID] LEFT JOIN [RoomSpaceMaintenanceCategory] AS rsc ON " +
                "rsc.[RoomSpaceMaintenanceCategoryID] = rm.[RoomSpaceMaintenanceCategoryID]  WHERE bk.[EntryID] = "
                + new SharedPreference(getActivity()).getEntryId() + " AND rm.[ViewOnWeb] = '1' AND rm.[RoomSpaceID] = " +
                "" + new SharedPreference(getActivity()).getRoomSpaceId() + " ORDER BY rm.[DateModified] DESC";

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.getParcelList(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    entryArrayList.clear();
                    try {
                        String s = response.body().string();
                        //System.out.println("response is:" + s);
                        JSONObject jsonObj = null;
                        jsonObj = XML.toJSONObject(s);
                        Log.d("JSON", jsonObj.toString());
                        JSONObject jsonObject = jsonObj.getJSONObject("feed");
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = jsonObject.getJSONArray("entry");
                            Gson gson = new Gson();
                            int count = jsonArray.length() > 3 ? 3 : jsonArray.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                                if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus()) && !TextUtils
                                        .isEmpty(feed.getContent().getRecord().getCompleteDate())) {
                                    feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.closed));
                                } else if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus())) {
                                    feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.job_submitted));

                                }
                                entryArrayList.add(feed);
                                Log.d("String Data Response", "response ::::" + feed.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                            Gson gson = new Gson();
                            Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                            if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus()) && !TextUtils
                                    .isEmpty(feed.getContent().getRecord().getCompleteDate())) {
                                feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.closed));
                            } else if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus())) {
                                feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.job_submitted));

                            }

                            //getFilterList(feed);
                            entryArrayList.add(feed);
                            Log.d("Response", "response ::::" + feed.toString());
                        }
                        Log.d("size", "size of maintenance list:" + entryArrayList.size());
                        getParcelList(rootView, progressDialog, entryArrayList, parcelEntryArrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    handleWebserviceFailureResponse(null, rootView, progressDialog, entryArrayList,
                            parcelEntryArrayList);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed

                handleWebserviceFailureResponse(t, rootView, progressDialog, entryArrayList,
                        parcelEntryArrayList);
                getParcelList(rootView, progressDialog, entryArrayList, parcelEntryArrayList);
            }
        });
    }

    /**
     * Method for get data of parcel list
     *
     * @param rootView
     * @param progressDialog       : Object of common progress bar
     * @param entryArrayList       : Array list of Maintenance list
     * @param parcelEntryArrayList : Array list of Parcel list
     */
    private void getParcelList(final View rootView, final ProgressDialog progressDialog, final ArrayList<Entry> entryArrayList, final ArrayList<com.basic.models.response.parcellistresponse.Entry> parcelEntryArrayList) {
        parcelEntryArrayList.clear();
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "SELECT ep.[EntryParcelID], pt.[Description] as parcel_type_val, st.[Description] " +
                "as shipping_type_val, ep.[AddressTypeID],ep.[ParcelStatusEnum], ps.[Description] AS status_desc," +
                " ep.[IssueDate] , ep.[ReceiptDate], ep.[TrackingNumber], ep.[Comments] , at.[Description]" +
                " AS address_val, ep.[Description] FROM [EntryParcel] AS ep LEFT JOIN [ParcelStatusEnum] AS" +
                " ps ON ps.[ParcelStatusEnum] = ep.[ParcelStatusEnum] LEFT JOIN [ParcelType] AS pt ON " +
                "pt.[ParcelTypeID] = ep.[ParcelTypeID] LEFT JOIN [ShippingType] AS st ON st.[ShippingTypeID]" +
                " = ep.[ShippingTypeID] LEFT JOIN [AddressType] as at ON at.[AddressTypeID] = ep.[AddressTypeID]" +
                " WHERE ep.[EntryID] = " + new SharedPreference(getActivity()).getEntryId() + " ORDER BY ep.[DateModified] desc";

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.getParcelList(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    parcelEntryArrayList.clear();
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
                                int count = jsonArray.length() > 3 ? 3 : jsonArray.length();
                                for (int i = 0; i < count; i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    com.basic.models.response.parcellistresponse.Entry feed =
                                            gson.fromJson(jsonObject1.toString(),
                                                    com.basic.models.response
                                                            .parcellistresponse.Entry.class);
                                    parcelEntryArrayList.add(feed);
                                    Log.d("response ::::", feed.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                                Gson gson = new Gson();
                                com.basic.models.response.parcellistresponse.Entry feed =
                                        gson.fromJson(jsonObject1.toString(),
                                                com.basic.models.response
                                                        .parcellistresponse.Entry.class);
                                parcelEntryArrayList.add(feed);
                                Log.d("response ::::", feed.toString());
                            }
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    setData(rootView, entryArrayList, parcelEntryArrayList);

                } else {
                    handleWebserviceFailureResponse(null, rootView, progressDialog, entryArrayList,
                            parcelEntryArrayList);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(t, rootView, progressDialog, entryArrayList,
                        parcelEntryArrayList);
            }
        });
    }


    /**
     * Method to handle the all situation when web service calling get failed.
     *
     * @param t                    : Error thrown by network libray in web service calling.
     * @param rootView             : root view of the fragment.
     * @param entryArrayList
     * @param parcelEntryArrayList
     */
    private void handleWebserviceFailureResponse(Throwable t, final View rootView, final ProgressDialog progressDialog, final ArrayList<Entry> entryArrayList, final ArrayList<com.basic.models.response.parcellistresponse.Entry> parcelEntryArrayList) {
        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != progressDialog) {
            Log.d("Progress", "dashboarddismis");
            progressDialog.dismiss();
        }
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getMaintenanceItemList(rootView, progressDialog, entryArrayList,
                            parcelEntryArrayList);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(), getResources().getString
                    (R.string.some_error), getResources().getString(R.string.alert), getResources().getString(R.string.ok));
        }
    }

    /**
     * Method for set data in layout screen.
     *
     * @param rootView
     * @param entryArrayList       : Maintenance List Data
     * @param parcelEntryArrayList : Parcel List Data
     */
    private void setData(View rootView, ArrayList<Entry> entryArrayList, ArrayList<com.basic.models.response.parcellistresponse.Entry> parcelEntryArrayList) {
        LinearLayout maintenance_list_layout = (LinearLayout) rootView.findViewById(R.id.maintenance_list_layout);
        LinearLayout parcel_list_layout = (LinearLayout) rootView.findViewById(R.id.parcel_list_layout);
        TextView user_name_layout = (TextView) rootView.findViewById(R.id.username_dashbaord);
        View view = (View) rootView.findViewById(R.id.view_dotted_divider_parcel);

        user_name_layout.setVisibility(View.VISIBLE);
        user_name_layout.setText("Hello " + new SharedPreference(getActivity()).getFirstName()
                + " " + new SharedPreference(getActivity()).getLastName());
        maintenance_list_layout.setVisibility(View.VISIBLE);
        parcel_list_layout.setVisibility(View.VISIBLE);
        maintenance_list_layout.setOnClickListener(this);
        parcel_list_layout.setOnClickListener(this);

        if (entryArrayList.size() == 0) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listView = inflater.inflate(R.layout.partial_dashboard_item_view, null);
            listView.findViewById(R.id.partial_dashboard_data).setVisibility(View.GONE);
            listView.findViewById(R.id.partial_dashbord_divider).setVisibility(View.GONE);
            listView.findViewById(R.id.no_data).setVisibility(View.VISIBLE);
            TextView no_data = (TextView) listView.findViewById(R.id.no_data);
            no_data.setText("No job available.");
            view.getBackground().setColorFilter(getResources().getColor(R.color.dashboard_maintenance_screen_bg), PorterDuff.Mode.SRC_ATOP);
            maintenance_list_layout.addView(listView);
            maintenance_list_layout.invalidate();

        } else {
            //System.out.println("count is:" + entryArrayList.size());
            for (int i = 0; i < entryArrayList.size(); i++) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View listView = inflater.inflate(R.layout.partial_dashboard_item_view, null);
                view.getBackground().setColorFilter(getResources().getColor(R.color.dashboard_maintenance_screen_bg), PorterDuff.Mode.SRC_ATOP);

                TextView dashboard_item_title_text_view = (TextView) listView.findViewById(R.id.dashboard_item_title_text_view);
                TextView dashboard_item_date_text_view = (TextView) listView.findViewById(R.id.dashboard_item_date_text_view);
                TextView dashboard_item_status_text_view = (TextView) listView.findViewById(R.id.dashboard_item_status_text_view);
                if (TextUtils.isEmpty(entryArrayList.get(i).getContent().getRecord().getSubCategory())) {
                    dashboard_item_title_text_view.setText("No title available");
                } else {
                    dashboard_item_title_text_view.setText(entryArrayList.get(i).getContent().getRecord().getSubCategory());
                }
                if (TextUtils.isEmpty(entryArrayList.get(i).getContent().
                        getRecord().getDateReported())) {
                    dashboard_item_date_text_view.setText("NA");
                } else {
                    dashboard_item_date_text_view.setText((DateUtils.getDate(DateUtils.convertToDeviceTime(entryArrayList.get(i).getContent().
                            getRecord().getDateReported().replace("T", " ")))));
                }

                dashboard_item_status_text_view.setText(entryArrayList.get(i).getContent().getRecord().getStatus());

                if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Job Submitted")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.dashboard_green));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Awaiting for Parts")
                        || entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Awaiting for Contractor")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_red));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Job Scheduled")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_blue));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Job in Progress")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_yellow));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Job Received")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_orange));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Please contact office")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.olive_green));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Job Completed")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.purple));
                } else if (entryArrayList.get(i).getContent().getRecord().getStatus().equalsIgnoreCase("Closed")) {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.grey_color));
                } else {
                    dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.gray_status));
                }

                if ((i + 1) == entryArrayList.size()) {
                    listView.findViewById(R.id.partial_dashbord_divider).setVisibility(View.GONE);
                }
                maintenance_list_layout.addView(listView);
                maintenance_list_layout.invalidate();
            }
        }
        if (parcelEntryArrayList.size() == 0) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listView = inflater.inflate(R.layout.partial_dashboard_item_view, null);
            view.getBackground().setColorFilter(getResources().getColor(R.color.dasboard_parcel), PorterDuff.Mode.SRC_ATOP);

            listView.findViewById(R.id.partial_dashboard_data).setVisibility(View.GONE);
            listView.findViewById(R.id.partial_dashbord_divider).setVisibility(View.GONE);
            listView.findViewById(R.id.no_data).setVisibility(View.VISIBLE);
            TextView no_data = (TextView) listView.findViewById(R.id.no_data);
            no_data.setText("No parcel available.");
            parcel_list_layout.addView(listView);
            parcel_list_layout.invalidate();

        } else {
            Log.d("parcel size", "count is:" + parcelEntryArrayList.size());
            for (int i = 0; i < parcelEntryArrayList.size(); i++) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View listView = inflater.inflate(R.layout.partial_dashboard_item_view, null);
                view.getBackground().setColorFilter(getResources().getColor(R.color.dasboard_parcel), PorterDuff.Mode.SRC_ATOP);

                TextView dashboard_item_title_text_view = (TextView) listView.findViewById(R.id.dashboard_item_title_text_view);
                TextView dashboard_item_date_text_view = (TextView) listView.findViewById(R.id.dashboard_item_date_text_view);
                TextView dashboard_item_status_text_view = (TextView) listView.findViewById(R.id.dashboard_item_status_text_view);

                //System.out.println("content is:" + parcelEntryArrayList.get(i).getContent().getRecord().getDescription());
                if (TextUtils.isEmpty(parcelEntryArrayList.
                        get(i).getContent().getRecord().getDescription())) {
                    dashboard_item_title_text_view.setText("No title available");

                } else {
                    dashboard_item_title_text_view.setText(parcelEntryArrayList.get(i).getContent().getRecord().getDescription());
                }
                if (TextUtils.isEmpty(parcelEntryArrayList.get(i).getContent().
                        getRecord().getReceiptDate())) {
                    dashboard_item_date_text_view.setText("NA");
                } else {
                    dashboard_item_date_text_view.setText((DateUtils.getDate(DateUtils.convertToDeviceTime(parcelEntryArrayList.get(i).getContent().
                            getRecord().getReceiptDate().replace("T", " ")))));
                }

                int status = parcelEntryArrayList.get(i).getContent().getRecord().getParcelStatusEnum();
                switch (status) {
                    case AppConstants.PARCEL_STAUS_RECEIVED:
                        dashboard_item_status_text_view.setText("New Parcel");
                        dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.dashboard_green));
                        break;
                    case AppConstants.PARCEL_STATUS_RETURNED:
                        dashboard_item_status_text_view.setText(parcelEntryArrayList.get(i).getContent().getRecord().getStatusDescription());
                        dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_red));
                        break;
                    case AppConstants.PARCEL_STATUS_FORWARD:
                        dashboard_item_status_text_view.setText(parcelEntryArrayList.get(i).getContent().getRecord().getStatusDescription());
                        dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_yellow));
                        break;
                    case AppConstants.PARCEL_STATUS_ISSUED:
                        dashboard_item_status_text_view.setText("Collected");
                        dashboard_item_status_text_view.setTextColor(getResources().getColor(R.color.transparent_orange));
                        break;
                }
                if ((i + 1) == parcelEntryArrayList.size()) {
                    listView.findViewById(R.id.partial_dashbord_divider).setVisibility(View.GONE);
                }
                parcel_list_layout.addView(listView);
                parcel_list_layout.invalidate();
            }
        }

    }

    /**
     * Method for enable second layout of screen.
     *
     * @param rootView : Main layout
     */
    private void enableSecondLayout(View rootView) {
        LinearLayout second_row_layout = (LinearLayout) rootView.findViewById(R.id.second_row_layout);

        LinearLayout maintenance_layout = (LinearLayout) rootView.findViewById(R.id.maintenance_layout);
        LinearLayout parcel_layout = (LinearLayout) rootView.findViewById(R.id.parcel_layout);
        LinearLayout resource_layout = (LinearLayout) rootView.findViewById(R.id.resource_layout);
        LinearLayout moreOptionLayout = (LinearLayout) rootView.findViewById(R.id.more_option_layout);
        LinearLayout events_layout = (LinearLayout) rootView.findViewById(R.id.events_layout);
        LinearLayout information_layout = (LinearLayout) rootView.findViewById(R.id.information_layout);
        LinearLayout help_layout = (LinearLayout) rootView.findViewById(R.id.help_layout);
        LinearLayout logout_layout = (LinearLayout) rootView.findViewById(R.id.logout_layout);

        int deviceWidth = getResources().getDisplayMetrics().widthPixels;

        maintenance_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        maintenance_layout.invalidate();
        parcel_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        parcel_layout.invalidate();
        resource_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        resource_layout.invalidate();
        moreOptionLayout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        moreOptionLayout.invalidate();
        events_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        events_layout.invalidate();
        information_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        information_layout.invalidate();
        help_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        help_layout.invalidate();
        logout_layout.getLayoutParams().height = (int) (deviceWidth / 4.2);
        logout_layout.invalidate();
        maintenance_layout.setOnClickListener(this);
        parcel_layout.setOnClickListener(this);
        resource_layout.setOnClickListener(this);
        moreOptionLayout.setOnClickListener(this);
        events_layout.setOnClickListener(this);
        information_layout.setOnClickListener(this);
        help_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        TextView moreOptionTextView = (TextView) rootView.findViewById(R.id.more_option_text_view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_option_layout:
                TextView moreOptionTextView = (TextView) rootView.findViewById(R.id.more_option_text_view);
                LinearLayout second_row_layout = (LinearLayout) rootView.findViewById(R.id.second_row_layout);
                ImageView ic_more_icon = (ImageView) rootView.findViewById(R.id.ic_more_icon);

                if (moreOptionTextView.getText().toString().trim().equalsIgnoreCase(getResources().getString(R.string.more))) {
                    second_row_layout.setVisibility(View.VISIBLE);
                    moreOptionTextView.setText(getResources().getString(R.string.less));
                    ic_more_icon.setImageResource(R.drawable.ic_dashboard_less_icon);
                } else {
                    second_row_layout.setVisibility(View.GONE);
                    moreOptionTextView.setText(getResources().getString(R.string.more));
                    ic_more_icon.setImageResource(R.drawable.ic_dashboard_more_icon);
                }
                break;

            case R.id.maintenance_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.MAIENTANCE);
                break;
            case R.id.parcel_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.PARCEL);
                break;
            case R.id.resource_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.RESOURCES);
                break;
            case R.id.events_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.EVENTS);
                break;
            case R.id.information_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.INFORMATION);
                break;
            case R.id.maintenance_list_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.MAIENTANCE);
                break;
            case R.id.parcel_list_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.PARCEL);
                break;
            case R.id.help_layout:
                ((DashboardActivity) getActivity()).selectSideBarItem(AppConstants.HELP);
                break;
            case R.id.logout_layout:
                ((DashboardActivity) getActivity()).getLogout();
        }
    }

}
