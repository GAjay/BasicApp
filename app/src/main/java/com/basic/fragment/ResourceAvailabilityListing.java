package com.basic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.basic.R;
import com.basic.activities.ContainerActivity;
import com.basic.adapters.AvailabilityResourceAdapter;
import com.basic.models.response.searchresource.Record;
import com.basic.utils.AppConstants;

import java.util.ArrayList;

/**
 * Created by ajay on 27/9/16.
 */

public class ResourceAvailabilityListing extends Fragment {

    private ArrayList<Record> searchResource;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.book_resource);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_avaiable_resource_avail, container, false);
        TextView headingTextView = (TextView) rootView.findViewById(R.id.heading);
        getActivity().setTitle(R.string.book_resource);

        headingTextView.setGravity(Gravity.CENTER);

        ((ContainerActivity)getActivity()).setBackground(R.drawable.ic_resource_bg);
        Bundle bundle = getArguments();
        if(null!=bundle){
            searchResource = bundle.getParcelableArrayList("availabilityResource");
            final String dateFrom = bundle.getString("dateFrom");
            final String dateTo = bundle.getString("dateTo");
            ListView listView = (ListView)rootView.findViewById(R.id.search_resource_list_view);
            //System.out.println("size"+searchResource.size());
            if(null != searchResource){
          AvailabilityResourceAdapter availableResourceAdapter = new AvailabilityResourceAdapter(getActivity(),searchResource);
            listView.setAdapter(availableResourceAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                    Bundle bundle = new Bundle();
                    bundle.putString("resourceId",searchResource.get(pos).getmResourceID());
                    bundle.putInt("screenName", AppConstants.AVAILABILITY__DETAIL_RESOURCE);
                    bundle.putString("resourceName",searchResource.get(pos).getmDescription());
                    bundle.putString("dateFrom", dateFrom);
                    bundle.putString("dateTo",dateTo);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ResourceAvailabilityDetailListing fragment = new ResourceAvailabilityDetailListing();

                    fragment.setArguments(bundle);
                    fragmentTransaction.add(R.id.content_frame_container, fragment);
                    fragmentTransaction.hide(com.basic.fragment.ResourceAvailabilityListing.this);
                    fragmentTransaction.addToBackStack(com.basic.fragment.ResourceAvailabilityListing.class.getName());
                    //System.out.println("framgemt Counts"+getFragmentManager().getBackStackEntryCount());
                    fragmentTransaction.commit();

                }
            });}
        }

        return rootView;
    }


}
