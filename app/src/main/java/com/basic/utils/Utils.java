package com.basic.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.basic.R;
import com.basic.interfaces.RoomsSpaceCallback;
import com.basic.models.response.roomspaceresponse.Entry;
import com.basic.network.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Utils class have various common utility methods required in application.
 */
public class Utils {


    private static RoomsSpaceCallback roomspacecallback;

    public static void hideKeyBoard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = com.basic.utils.Utils.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = com.basic.utils.Utils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = com.basic.utils.Utils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /** Method to check gps enabled or not.
     * @param context : context of current class.
     * @return : true or false.
     */
    public static boolean isGpsEnabled(Context context){
        final LocationManager manager = (LocationManager)context.getSystemService( Context.LOCATION_SERVICE );

        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            // Call your Alert message
            return true;
        }
        return  false;
    }

    /** Method to check location services are enabled or not.
     * @param context : context of calling class
     * @return : true or false.
     */
    public static boolean isLocationServiceEnabled(Context context){
        String locationProviders = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return !(locationProviders == null || locationProviders.equals(""));
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    // A method for showing common snackBar in app.
    public static void showSnackBar(View view, String snackbarText) {
        Snackbar snackbar = Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#ff5455"));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    // A method for knowing currently visible fragment.
    public static Fragment getCurrentFragment(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return activity.getSupportFragmentManager()
                .findFragmentByTag(fragmentTag);
    }



    public static void getRoomSpaceId(final String roomSpace, final ProgressDialog progressDialog, final Context context, final RoomsSpaceCallback roomspace){
        roomspacecallback =roomspace;
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

        // will call web service here.
        ApiClient.ApiInterface apiService = ApiClient.getClient();
        String strRequestBody = "SELECT [roomspaceid] from [RoomSpaceMaintenance] where [roomspaceid] = "+roomSpace ;

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        Call<ResponseBody> call = apiService.getParcelList(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) try {
                    String s = response.body().string();
                    //System.out.println("response isabc:" + s);
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = XML.toJSONObject(s);
                        Log.d("JSON", jsonObj.toString());
                        JSONObject jsonObject = jsonObj.getJSONObject("feed");
                        JSONArray jsonArray = null;
                        try {
                            //System.out.println("sdfasdhkfhasdfhsjkahf+is");
                            jsonArray = jsonObject.getJSONArray("entry");
                            Gson gson = new Gson();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                                Log.d("response ::::", feed.toString());
                                roomspace.onsuccess(feed);
                                break;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            JSONObject jsonObject1 = jsonObject.getJSONObject("entry");
                            Gson gson = new Gson();
                            Entry feed = gson.fromJson(jsonObject1.toString(), Entry.class);
                            roomspace.onsuccess(feed);
                        }
                    } catch (JSONException e) {
                        Log.e("JSON exception", e.getMessage());
                        e.printStackTrace();
                        roomspace.onfailure("error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                else {
                    handleWebserviceFailureResponse(context,null,roomSpace,progressDialog);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed

                handleWebserviceFailureResponse(context,t, roomSpace, progressDialog);
            }
        });

    }
    /**
     * Method to handle the all situation when web service calling get failed.
     *  @param t        : Error thrown by network libray in web service calling.
     * @param roomSpace
     * @param progressDialog

     */
    private static void handleWebserviceFailureResponse(final Context context, Throwable t, final String roomSpace, final ProgressDialog progressDialog) {

        // checking if error is instance of IO class, then its internet error. other wise
        // some error from server side.

        if (null != progressDialog) {
            Log.d("Progress","utlisdismis");
            progressDialog.dismiss();
        }

        if (null != t && t instanceof IOException) {
            DialogUtils.getInternetRetryDialog(context, new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    getRoomSpaceId(roomSpace, progressDialog, context,roomspacecallback);
                }
            });
        } else {
            DialogUtils.getConfirmationDialogAlert(context, context.getString(R.string.some_error),
                    context.getString(R.string.alert), context.getString(R.string.ok));
        }
    }

}


