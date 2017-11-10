package com.basic.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.basic.activities.DashboardActivity;
import com.google.gson.Gson;
import com.basic.R;
import com.basic.activities.ContainerActivity;
import com.basic.activities.FilterDialogActivity;
import com.basic.adapters.MaintenanceListAdapter;
import com.basic.interfaces.RoomsSpaceCallback;
import com.basic.models.FilterModel;
import com.basic.models.response.maintenanceListResponse.Entry;
import com.basic.network.ApiClient;
import com.basic.utils.AppConstants;
import com.basic.utils.CustomProgressDialog;
import com.basic.utils.DialogUtils;
import com.basic.utils.SharedPreference;
import com.basic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaientanceListFragment extends Fragment implements View.OnClickListener {

    MaintenanceListAdapter adapter;
    ArrayList<Entry> entryArrayList = new ArrayList<>();
    ArrayList<FilterModel> filterModelArrayList = new ArrayList<FilterModel>();
    ArrayList<Entry> entryArrayFilteredList = new ArrayList<Entry>();
    View rootView;
    Menu menu;
    private ProgressDialog progressDialog;

    public MaientanceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.maintenance);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_maintenance_menu, menu);  // Use filter.xml from step 1
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_filter_maintenance) {
            Collections.sort(filterModelArrayList, new Comparator<FilterModel>() {
                @Override
                public int compare(FilterModel s1, FilterModel s2) {
                    return s1.getFilterName().compareToIgnoreCase(s2.getFilterName());
                }
            });
            Intent intent = new Intent(getActivity(), FilterDialogActivity.class);
            intent.putParcelableArrayListExtra("filterList", filterModelArrayList);
            startActivityForResult(intent, 105);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 105 && resultCode == Activity.RESULT_OK) {
            String filterName = "";
            entryArrayFilteredList.clear();

            int pos = data.getIntExtra("pos", 0);

            for (int i = 0; i < filterModelArrayList.size(); i++) {
                if (i == pos) {
                    filterModelArrayList.get(i).setChecked(true);
                    filterName = filterModelArrayList.get(i).getFilterName();
                } else {
                    filterModelArrayList.get(i).setChecked(false);
                }
            }

            if (pos == 0) {
                // show all records
                entryArrayFilteredList.addAll(entryArrayList);
            } else {
                for (Entry model : entryArrayList) {
                    if (model.getContent().getRecord().getStatus().equalsIgnoreCase(filterName)) {
                        entryArrayFilteredList.add(model);
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }
        if (requestCode == 106 && resultCode == Activity.RESULT_OK) {
            getMaintenanceItemList(rootView, progressDialog);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_maientance_list, container, false);
        progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_maintienance_title_color));
        ((DashboardActivity) getActivity()).setBackground(R.drawable.ic_maintance_bg);
        ImageView newMaintenanceRequestFab =
                (ImageView) rootView.
                        findViewById(R.id.add_new_maintenance_request_fab);
        newMaintenanceRequestFab.setOnClickListener(this);

        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        Utils.getRoomSpaceId(new SharedPreference(getActivity()).getRoomSpaceId(), progressDialog, getContext(), new RoomsSpaceCallback() {
            @Override
            public void onsuccess(com.basic.models.response.roomspaceresponse.Entry roomSpaceRecord) {
                if (Utils.isNetworkAvailable(getActivity())) {
                    if(null != menu){
                    menu.getItem(0).setEnabled(true);}
                    getMaintenanceItemList(rootView, progressDialog);
                } else {
                    DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                        @Override
                        public void onClick() {
                            getMaintenanceItemList(rootView, progressDialog);
                        }
                    });
                }
            }

            @Override
            public void onfailure(String s) {
                if(null != menu){
                menu.getItem(0).setEnabled(false);}
                if(null != progressDialog){
                progressDialog.dismiss();}
                TextView emptyTextView = (TextView) rootView.findViewById(R.id.maintenance_empty_list_text_view);
                emptyTextView.setText("No jobs available.");
                emptyTextView.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    private void getMaintenanceItemList(final View rootView, final ProgressDialog progressDialog) {


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
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    filterModelArrayList.clear();
                    entryArrayList.clear();
                    entryArrayFilteredList.clear();
                    filterModelArrayList.add(new FilterModel(getString(R.string.all), 0, true));
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
                                    Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                                    if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus()) && !TextUtils
                                            .isEmpty(feed.getContent().getRecord().getCompleteDate())) {
                                        feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.closed));
                                    } else if (TextUtils.isEmpty(feed.getContent().getRecord().getStatus())) {
                                        feed.getContent().getRecord().setStatus(getActivity().getResources().getString(R.string.job_submitted));

                                    }
                                    getFilterList(feed);
                                    entryArrayList.add(feed);
                                    entryArrayFilteredList.add(feed);
                                    //System.out.println("response ::::" + feed.toString());
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
                                entryArrayFilteredList.add(feed);
                                //System.out.println("response ::::" + feed.toString());
                            }
                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setData(rootView, entryArrayFilteredList);
                } else {
                    handleWebserviceFailureResponse(null, rootView, progressDialog);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(t, rootView, progressDialog);
            }
        });
    }

    /**
     * Method to handle the all situation when web service calling get failed.
     *
     * @param t              : Error thrown by network libray in web service calling.
     * @param rootView       : root view of the fragment.
     * @param progressDialog
     */
    private void handleWebserviceFailureResponse(Throwable t, final View rootView, final ProgressDialog progressDialog) {
        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.

        if (null != progressDialog) {
            progressDialog.dismiss();
        }
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getMaintenanceItemList(rootView, progressDialog);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(getActivity(), getResources().getString(R.string.some_error),
                    getResources().getString(R.string.alert), getResources().getString(R.string.ok));
        }
    }

    /**
     * Method to set data in list view.
     *
     * @param rootView       : root view of the parent
     * @param entryArrayList : parcle list received form web service.
     */
    private void setData(View rootView, final ArrayList<Entry> entryArrayList) {
        if (entryArrayList.size() > 0) {
            if (null != menu) {
                menu.getItem(0).setEnabled(true);
            }
            ListView parcelListView = (ListView) rootView.findViewById(R.id.maintenance_list_view);
            adapter = new MaintenanceListAdapter(getActivity(), entryArrayList);
            parcelListView.setAdapter(adapter);
            parcelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Entry selectedEntry = entryArrayList.get(position);

                    // redirect to filter activity. which is a dialog activity to show
                    // the list of filter.
                    Intent intent = new Intent(getActivity(), ContainerActivity.class);
                    intent.putExtra("entryMaintenanceDetail", selectedEntry);
                    intent.putExtra("screenName", AppConstants.MAIENTANCE);
                    startActivityForResult(intent, 106);
                }
            });
        } else {
            if (null != menu) {
                menu.getItem(0).setEnabled(false);
            }
            TextView emptyTextView = (TextView) rootView.findViewById(R.id.maintenance_empty_list_text_view);
            emptyTextView.setText("No jobs available.");
            emptyTextView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Method to prepare dynamic filter options on the basis of items in parcel list.
     *
     * @param feed : parcel item.
     */
    private void getFilterList(Entry feed) {
        boolean isFound = false;
        for (FilterModel model : filterModelArrayList) {
            if (model.getFilterName().equalsIgnoreCase(feed.getContent().getRecord().getStatus())) {
                isFound = true;
            }
        }
        // condition to check if already included in filter list if not then add
        // new filter other wise ignore if already exist in list.
        if (!isFound) {

            filterModelArrayList.add(new FilterModel(feed.getContent().getRecord().getStatus(),
                    filterModelArrayList.size(), false));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_maintenance_request_fab:
                Intent intent = new Intent(getActivity(), ContainerActivity.class);
                intent.putExtra("screenName", AppConstants.MAINTENANCE_NEW);
                startActivityForResult(intent, 106);
                break;
        }
    }
}
