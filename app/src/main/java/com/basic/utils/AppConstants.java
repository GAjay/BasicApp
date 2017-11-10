package com.basic.utils;

import com.basic.R;

/**
 * class to hold the various constants used in application.
 */
public class AppConstants {
    // side bar items constants.
    public static final int DASHBOARD = R.string.dashboard;
    public static final int MAIENTANCE = R.string.maintenance;
    public static final int PARCEL = R.string.parcel;
    public static final int RESOURCES = R.string.resources;
    public static final int EVENTS = R.string.events;
    public static final int INFORMATION = R.string.information;
    public static final int HELP = R.string.help;
    public static final int LOGOUT = R.string.logout;
    public static final int BOOK_RESOURCE = R.string.book_resource;

    public static final int MAINTENANCE_NEW = R.string.new_job;


    // constants of parcel status viz : returned, issued, received or forward.
    public static final int PARCEL_STATUS_RETURNED = 3;
    public static final int PARCEL_STATUS_ISSUED = 1;
    public static final int PARCEL_STATUS_FORWARD = 2;
    public static final int PARCEL_STAUS_RECEIVED = 0;
    public static final int PARCEL_STAUS_ALL = -1;

    // constants of parcel status viz : assigned, out, history , pending return or cancelled.
    public static final int RESOURCE_STATUS_ASSIGNED =0;
    public static final int RESOURCE_STATUS_OUT =1;
    public static final int RESOURCE_STATUS_HISTORY =2;
    public static final int RESOURCE_STATUS_PENDING_RETURN =3;
    public static final int RESOURCE_STATUS_CANCELLED =4;

    /*Status messages for success and faliure */
    public static final int SUCCESS_STATUS = 200;
    public static final int FALIURE_STATUS = 401;

    public static final int RESOURCE_TYPE = R.string.resource_type;
    public static final int RESOURCE_TYPE_NEW = R.string.resource_type_new;
    public static final int LOCATION_TYPE = R.string.location_type;

    public static final int AVAILABLE_RESOURCE  = R.string.available_screen;

    public static final int AVAILABILITY_RESOURCE  = R.string.availabilitylisting;

    public static final int  AVAILABILITY__DETAIL_RESOURCE  = R.string.availability_detail_screen;
}
