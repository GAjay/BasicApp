package com.basic.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.basic.R;
import com.basic.fragment.MaintenanceDetailFragment;
import com.basic.fragment.ParcelDetailFragment;
import com.basic.fragment.ResourceAvailabilityDetailListing;
import com.basic.fragment.ResourceAvailabilityListing;
import com.basic.fragment.ResourceDetailFragment;
import com.basic.utils.AppConstants;

/**
 * Activity named container activity, use to hold only
 * different kind of projects.
 */
public class ContainerActivity extends AppCompatActivity {
    private Bundle resourceDataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_container);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int screenName = getIntent().getIntExtra("screenName",0);
        loadScreen(screenName);
    }

    public void loadScreen(int screenName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        android.support.v4.app.Fragment fragment = null;
        Bundle bundle = new Bundle();

        switch (screenName){
            case AppConstants.PARCEL:
                com.basic.models.response.parcellistresponse.Entry selectedEntry =
                        getIntent().getParcelableExtra("entryParcelDetail");
                fragment = new ParcelDetailFragment();
                bundle.putParcelable("entryParcelDetail", selectedEntry);
                fragment.setArguments(bundle);
                break;
            case AppConstants.MAIENTANCE:
                com.basic.models.response.maintenanceListResponse.Entry selectedMaintenaceEntry
                        = getIntent().getParcelableExtra("entryMaintenanceDetail");
                fragment = new MaintenanceDetailFragment();
                bundle.putParcelable("entryMaintenanceDetail", selectedMaintenaceEntry);
                fragment.setArguments(bundle);
                break;
            case AppConstants.RESOURCES:
                com.basic.models.response.resourcelistresponse.
                        Entry selectedResourceEntry = getIntent().getParcelableExtra("entryResourceDetail");
                // loading parcel detail Fragment in the activity.

                fragment = new ResourceDetailFragment();
                bundle.putParcelable("entryResourceDetail", selectedResourceEntry);
                fragment.setArguments(bundle);
                break;



            case AppConstants.AVAILABILITY_RESOURCE:

                fragment = new ResourceAvailabilityListing();
                /*Set bundle for get data from pervious screen.*/
                fragment.setArguments(resourceDataBundle);
                break;

            case AppConstants.AVAILABILITY__DETAIL_RESOURCE:

                fragment = new ResourceAvailabilityDetailListing();
                /*Set bundle for get data from pervious screen.*/
                fragment.setArguments(resourceDataBundle);
                break;


        }
        if(null != fragment){
            android.support.v4.app.FragmentManager frgManager = getSupportFragmentManager();
            for (int i = 0; i < frgManager.getBackStackEntryCount(); i++) {
                frgManager.popBackStack();
            }
        fragmentTransaction.add(R.id.content_frame_container, fragment);
        fragmentTransaction.commit();}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setBackground(int i) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.parent_layout);
        relativeLayout.setBackground(getResources().getDrawable(i));
    }

}