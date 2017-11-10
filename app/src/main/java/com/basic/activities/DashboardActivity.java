package com.basic.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.basic.R;
import com.basic.adapters.NavigationAdapter;
import com.basic.fragment.CommonWebViewFragment;
import com.basic.fragment.DashboardFragment;
import com.basic.fragment.MaientanceListFragment;
import com.basic.fragment.ParcelListFragment;
import com.basic.models.NavigationItemModel;
import com.basic.utils.AppConstants;
import com.basic.utils.DialogUtils;
import com.basic.utils.SharedPreference;
import com.basic.utils.Utils;

import java.util.ArrayList;

/**
 * Dashboard activity is main activity which will appear after login.
 */
public class DashboardActivity extends AppCompatActivity {


    public DrawerLayout drawer;
    public static ActionBarDrawerToggle drawerToggle;
    private ListView _list_side_bar_listView;
    private ArrayList<NavigationItemModel> dataList = new ArrayList<>();
    private LinearLayout linearLayout;
    NavigationAdapter adapter;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_dashboard);
        PackageManager pm = getPackageManager();
        for (ApplicationInfo app : pm.getInstalledApplications(0)) {
            Log.d("PackageList", "package: " + app.packageName + ", sourceDir: " + app.sourceDir);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        linearLayout = (LinearLayout) findViewById(R.id.dashboard_parent_layout);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setActionbar(false);
        drawer.addDrawerListener(drawerToggle);

        _list_side_bar_listView = (ListView) findViewById(R.id.list_left_drawer);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.item_navigation_header,
                _list_side_bar_listView, false);
        _list_side_bar_listView.addHeaderView(header, null, false);

        dataList = getNavigationItemList();
        adapter = new NavigationAdapter(getApplicationContext(), dataList);
        _list_side_bar_listView.setAdapter(adapter);

        Bundle fcmBundle = getIntent().getExtras();
//        int pos = fcmBundle.getInt("notification", 0);
        int pos = -1;
        Log.d("pos", String.valueOf(pos));
      /*  for(int i=0;i<dataList.size();i++){
            dataList.get(i).setChecked(false);
        }*/
        if (pos >= 0) {
            /* Set integer for parcel list screen.*/
            int screen = 3;
            NavigationItemModel model = dataList.get(screen - 1);
            selectItem(model, screen);

            dataList.get(screen).setChecked(true);

        } else {
            dataList.get(0).setChecked(true);
            final android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            DashboardFragment fragment = new DashboardFragment();
            fragmentTransaction.add(R.id.content_frame, fragment);
            toolbar.setTitle("");
            fragmentTransaction.commit();
        }
        _list_side_bar_listView.setOnItemClickListener(new DrawerItemClickListener());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment f = getSupportFragmentManager().
                findFragmentById(R.id.content_frame);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            drawerToggle.setDrawerIndicatorEnabled(false);
            drawerToggle.syncState();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } else if (f instanceof CommonWebViewFragment) {
            ((CommonWebViewFragment) f).onBackPressedFragment();
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if ((f instanceof DashboardFragment)) {
            super.onBackPressed();
        } else {
            selectSideBarItem(AppConstants.DASHBOARD);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {
            super.onBackPressed();
            return true;
        }
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle(Toolbar toolbar) {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Utils.hideKeyBoard(drawer, getApplicationContext());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Utils.hideKeyBoard(drawer, getApplicationContext());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
    }

    private ArrayList<NavigationItemModel> getNavigationItemList() {
        ArrayList<NavigationItemModel> itemList = new ArrayList<>();
        itemList.add(new NavigationItemModel(R.drawable.ic_dashboard_navigation,
                AppConstants.DASHBOARD, R.color.sidebar_dashboard_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_maintance_navigation,
                AppConstants.MAIENTANCE, R.color.sidebar_maintienance_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_parcel_navigation,
                AppConstants.PARCEL, R.color.sidebar_parcel_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_resources_navigation,
                AppConstants.RESOURCES, R.color.sidebar_resources_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_events_navigation,
                AppConstants.EVENTS, R.color.sidebar_events_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_information_navigation,
                AppConstants.INFORMATION, R.color.sidebar_information_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_help_navigation,
                AppConstants.HELP, R.color.sidebar_help_title_color));
        itemList.add(new NavigationItemModel(R.drawable.ic_logout_navigation,
                AppConstants.LOGOUT, R.color.sidebar_logout_title_color));

        return itemList;
    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // condition to check the clicked item is not logout.
            if (position != dataList.size()) {
//                for (NavigationItemModel model : dataList) {
//                    model.setChecked(false);
//                }
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setChecked(false);
                }
                dataList.get(position - 1).setChecked(true);
                adapter.notifyDataSetChanged();
            }
            NavigationItemModel model = dataList.get(position - 1);
            selectItem(model, position);
        }
    }


    /**
     * Method will be call when user click on any item in side bar.
     *
     * @param model    : model is the sidebar naviagtion model.
     * @param position : position where user clicked.
     */
    public void selectItem(NavigationItemModel model, int position) {
        Bundle fragmentBundle = new Bundle();
        Fragment fragment = null;
        switch (model.getItemName()) {
            case AppConstants.DASHBOARD:

                fragment = new DashboardFragment();

                break;
            case AppConstants.MAIENTANCE:
                fragment = new MaientanceListFragment();
                break;
            case AppConstants.PARCEL:
                fragment = new ParcelListFragment();
                break;
            case AppConstants.EVENTS:
                fragment = new CommonWebViewFragment();
                fragmentBundle.putString("url", "http://www.dwellstudent.co.uk/en/event/");
                fragmentBundle.putInt("title", R.string.events);
                fragment.setArguments(fragmentBundle);

                break;
            case AppConstants.INFORMATION:
                fragment = new CommonWebViewFragment();
                fragmentBundle.putString("url", "http://www.dwellstudent.co.uk/en/information/");
                fragmentBundle.putInt("title", R.string.information);
                fragment.setArguments(fragmentBundle);
                break;
            case AppConstants.HELP:
                fragment = new CommonWebViewFragment();
                fragmentBundle.putString("url", "http://www.dwellstudent.co.uk/en/help/");
                fragmentBundle.putInt("title", R.string.help);
                fragment.setArguments(fragmentBundle);
                break;
            case AppConstants.LOGOUT:
                getLogout();
                break;
            default:
                break;
        }

        if (null != fragment) {
            android.support.v4.app.FragmentManager frgManager = getSupportFragmentManager();
            for (int i = 0; i < frgManager.getBackStackEntryCount(); i++) {
                frgManager.popBackStack();
            }
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            if (model.getItemName() != AppConstants.LOGOUT) {
                _list_side_bar_listView.setItemChecked(position, true);
            }
            setTitle(model.getItemName());
            drawer.closeDrawer(_list_side_bar_listView);
        }
    }

    /**
     * Logout method will be called when user get logout.
     */
    public void getLogout() {
        drawer.closeDrawer(_list_side_bar_listView);
        DialogUtils.getConfirmationDialogWithCallback(DashboardActivity.this,
                getResources().getString(R.string.sure_want_logout),
                getResources().getString(R.string.alert),
                getResources().getString(R.string.alert_yes_button_text),
                getResources().getString(R.string.alert_no_button_text),
                new DialogUtils.successCallback() {
                    @Override
                    public void onClick() {
                        SharedPreference sp = new SharedPreference(getApplicationContext());
                        sp.getLogout(DashboardActivity.this);
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            NavigationItemModel model = dataList.get(data.getIntExtra("position", 0) - 1);
            selectItem(model, data.getIntExtra("position", 0));
        }


    }


    public void superBackPressed() {
        selectSideBarItem(AppConstants.DASHBOARD);
    }

    public void selectSideBarItem(int pos) {
        NavigationItemModel navigationItemModel = null;
        int position = 0;
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setChecked(false);
        }
        for (NavigationItemModel model : dataList) {
            model.setChecked(false);
            if (model.getItemName() == pos) {
                navigationItemModel = model;
                position = dataList.indexOf(model);
                break;
            }
        }
        dataList.get(position).setChecked(true);
        adapter.notifyDataSetChanged();
        selectItem(navigationItemModel, position);
    }

    public void setActionbar(final boolean isBackNavigationRequired) {
        if (isBackNavigationRequired) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerToggle = setupDrawerToggle(toolbar);
            drawer.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        }
    }


    public void setBackground(int i) {
        linearLayout.setBackground(getResources().getDrawable(i));
    }

}
