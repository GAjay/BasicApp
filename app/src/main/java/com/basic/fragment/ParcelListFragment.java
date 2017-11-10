package com.basic.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.basic.activities.DashboardActivity;
import com.google.gson.Gson;
import com.basic.R;
import com.basic.activities.ContainerActivity;
import com.basic.activities.FilterDialogActivity;
import com.basic.adapters.ParcelListAdapter;
import com.basic.models.FilterModel;
import com.basic.models.response.parcellistresponse.Entry;
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
public class ParcelListFragment extends Fragment {

    ParcelListAdapter adapter;
    ArrayList<Entry> entryArrayList = new ArrayList<Entry>();
    ArrayList<FilterModel> filterModelArrayList = new ArrayList<FilterModel>();
    ArrayList<Entry> entryArrayFilteredList = new ArrayList<Entry>();
    Menu menu;

    public interface filterItemClickedInterface {
        public void filterSelected(int i);
    }

    public ParcelListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.parcel_list);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.parcel_list);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_parcel_menu, menu);  // Use filter.xml from step 1
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
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
            int filterId = -1;
            entryArrayFilteredList.clear();

            int pos = data.getIntExtra("pos", 0);

            for (int i = 0; i < filterModelArrayList.size(); i++) {
                if (i == pos) {
                    filterModelArrayList.get(i).setChecked(true);
                    filterId = filterModelArrayList.get(i).getFilterId();
                } else {
                    filterModelArrayList.get(i).setChecked(false);
                }
            }

            if (filterId == -1) {
                // show all records
                entryArrayFilteredList.addAll(entryArrayList);
            } else {
                for (Entry model : entryArrayList) {
                    if (model.getContent().getRecord().getParcelStatusEnum() == filterId) {
                        entryArrayFilteredList.add(model);
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_parcel_list, container, false);
        ((DashboardActivity)getActivity()).setBackground(R.drawable.ic_parcel_bg);
        if (Utils.isNetworkAvailable(getActivity())) {
            if (null != getActivity()) {
                getParcelList(rootView);
            }

        } else {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getParcelList(rootView);
                }
            });
        }

        return rootView;
    }

    /**
     * Method to fetch the parcel list from web service.
     *
     * @param rootView : root view of the fragment.
     */
    private void getParcelList(final View rootView) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity(),
                ContextCompat.getColor(getActivity(), R.color.sidebar_parcel_title_color));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();

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
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    filterModelArrayList.clear();
                    entryArrayList.clear();

                    filterModelArrayList.add(new FilterModel(getActivity().getResources().getString(R.string.all),
                            AppConstants.PARCEL_STAUS_ALL, true));
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
                                    getFilterList(feed);
                                    entryArrayList.add(feed);
                                    entryArrayFilteredList.add(feed);
                                    Log.d("response ::::", feed.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                                Gson gson = new Gson();
                                Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                                getFilterList(feed);
                                entryArrayList.add(feed);
                                entryArrayFilteredList.add(feed);
                                Log.d("response ::::", feed.toString());
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
                    handleWebserviceFailureResponse(null, rootView);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                if (null != progressDialog) {
                    progressDialog.dismiss();
                }
                handleWebserviceFailureResponse(t, rootView);
            }
        });
    }

    /**
     * Method to handle the all situation when web service calling get failed.
     *
     * @param t        : Error thrown by network libray in web service calling.
     * @param rootView : root view of the fragment.
     */
    private void handleWebserviceFailureResponse(Throwable t, final View rootView) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.
        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(getActivity(), new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getParcelList(rootView);
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
            ListView parcelListView = (ListView) rootView.findViewById(R.id.parcel_list_view);
            adapter = new ParcelListAdapter(getActivity(), entryArrayList);
            parcelListView.setAdapter(adapter);
            parcelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Entry selectedEntry = entryArrayList.get(position);
                    // redirect to filter activity. which is a dialog activity to show
                    // the list of filter.
                    Intent intent = new Intent(getActivity(), ContainerActivity.class);
                    intent.putExtra("entryParcelDetail", selectedEntry);
                    intent.putExtra("screenName", AppConstants.PARCEL);
                    startActivity(intent);
                }
            });
        } else {
            if (null != menu) {
                menu.getItem(0).setEnabled(false);
            }
            TextView emptyTextView = (TextView) rootView.findViewById(R.id.empty_list_text_view);
            emptyTextView.setText("No parcel available.");
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
            if (model.getFilterId() == feed.getContent().getRecord().getParcelStatusEnum()) {
                isFound = true;
            }
        }
        // condition to check if already included in filter list if not then add
        // new filter other wise ignore if already exist in list.
        if (!isFound) {
            switch (feed.getContent().getRecord().getParcelStatusEnum()) {
                case AppConstants.PARCEL_STAUS_RECEIVED:
                    filterModelArrayList.add(new FilterModel("New Parcel",
                            AppConstants.PARCEL_STAUS_RECEIVED, false));
                    break;
                case AppConstants.PARCEL_STATUS_RETURNED:
                    filterModelArrayList.add(new FilterModel(feed.getContent().getRecord().getStatusDescription(),
                            AppConstants.PARCEL_STATUS_RETURNED, false));
                    break;
                case AppConstants.PARCEL_STATUS_FORWARD:
                    filterModelArrayList.add(new FilterModel(feed.getContent().getRecord().getStatusDescription(),
                            AppConstants.PARCEL_STATUS_FORWARD, false));
                    break;
                case AppConstants.PARCEL_STATUS_ISSUED:
                    filterModelArrayList.add(new FilterModel("Collected",
                            AppConstants.PARCEL_STATUS_ISSUED, false));
                    break;
            }
        }


    }

}
