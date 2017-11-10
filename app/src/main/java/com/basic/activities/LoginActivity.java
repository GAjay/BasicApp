package com.basic.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.basic.R;
import com.basic.models.request.NotificationRequest;
import com.basic.models.response.dotNetServiceResponse;
import com.basic.models.response.loginresponse.Entry;
import com.basic.network.ApiClient;
import com.basic.utils.AppConstants;
import com.basic.utils.CustomProgressDialog;
import com.basic.utils.DialogUtils;
import com.basic.utils.SharedPreference;
import com.basic.utils.Utils;

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
 * An activity for login functionality
 *
 * @author Ranosys
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_login);

        ImageView splashlogo = (ImageView)findViewById(R.id.splashlogo);
        int deviceWidth = getResources().getDisplayMetrics().widthPixels;

        splashlogo.getLayoutParams().width = (int)(0.60*deviceWidth);
        splashlogo.getLayoutParams().height = (int)(0.48*deviceWidth);

        //Setting up click listener
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //Login Button
            case R.id.button_login:
                loginService(view);
                break;
        }
    }

    /**
     * Call Login Service with send device token in notification servcie.
     *
     * @param view:login_activity
     */
    private void loginService(final View view) {
        //hiding keyboard before calling webservice.
        Utils.hideKeyBoard(view, LoginActivity.this);
        //Network available checking.
        if (Utils.isNetworkAvailable(LoginActivity.this)) {
            //blank field validation
            if (!isAnyFieldEmpty()) {
                if (checkFieldValidation()) {
                    //Getting emailid and pin number.
                    final String emailId = ((EditText) findViewById(R.id.text_emailid)).getText().toString();
                    String pin = ((EditText) findViewById(R.id.text_password)).getText().toString();
                    //Initialization progressbar
                    final ProgressDialog progressDialog = CustomProgressDialog.ctor(LoginActivity.this,
                            ContextCompat.getColor(LoginActivity.this, R.color.sidebar_logout_title_color));
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.
                            graphics.Color.TRANSPARENT));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                /*Calling login service*/
                    ApiClient.ApiInterface apiService = ApiClient.getClient();
                    //Creating request for login service.
                    //Add New field as per to resolve issue of 3762 entryid
                    String strRequestBody = "SELECT en.[entryid], en.[PinNumber], ed.[Email], en.[NameLast]," +
                            " en.[NameFirst], en.[NameTitle], bk.[RoomLocationID],bk.[RoomSpaceID] FROM [Entry] AS en LEFT JOIN" +
                            " [EntryAddress] AS ed ON ed.[entryid] = en.[entryid] LEFT JOIN [Booking] as bk " +
                            "ON bk.[entryid] = en.[entryid] WHERE ed.[Email] = '"+emailId+"' " +
                            "AND en.[PinNumber] = '" + pin + "' and bk.EntryStatusEnum = '5' AND ed.[AddressTypeID] = 0";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),
                            strRequestBody);
                    Call<ResponseBody> call = apiService.getloginresponse(requestBody);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
                                JSONObject jsonObj = null;
                                try {/*xml to json conversion*/
                                    jsonObj = XML.toJSONObject(s);
                                    Log.d("JSON", jsonObj.toString());
                                    JSONObject feedObject = jsonObj.getJSONObject("feed");
                                    JSONObject entryObject = feedObject.getJSONObject("entry");
                                    Gson gson = new Gson();
                                    Entry entry = gson.fromJson(entryObject.toString(), Entry.class);
                                    //Checking is username and password exist in system or not.
                                    if (null != entry) {
                                        SharedPreference sp = new SharedPreference(getApplicationContext());
                                        sp.storeEntryId(entry.getContent().getRecord().getEntryID());
                                        sp.storeFirstName(entry.getContent().getRecord().getNameFirst());
                                        sp.storeLastName(entry.getContent().getRecord().getNameLast());
                                        sp.storeNameTitle(entry.getContent().getRecord().getNameTitle());
                                        sp.storeRoomSpaceId(entry.getContent().getRecord().getRoomSpaceId());
                                        sp.storeRoomLocationId(entry.getContent().getRecord().getRoomLocationId());

                                        callFCMWebService(entry.getContent().getRecord().getEntryID(),
                                                progressDialog,emailId);
                                    }
                                    Log.d("entry final value:", entry.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                    //Showing Dialog in case user not exist.
                                    DialogUtils.getConfirmationDialogAlert(LoginActivity.this,
                                            getResources().getString(R.string.invalid_user_credentails),
                                            getResources().getString(R.string.alert_title), getResources().getString(R.string.ok));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // Log error here since request failed
                            Log.d("Error", t.getMessage());
                            progressDialog.dismiss();
                            //Retry your conection dailog.
                            if (null != t && t instanceof IOException) {
                                DialogUtils.getInternetRetryDialog(LoginActivity.this, new DialogUtils.internetRetryCallback() {
                                    @Override
                                    public void onClick() {
                                        loginService(view);
                                    }
                                });
                            } else {
                                DialogUtils.getConfirmationDialogAlert(LoginActivity.this,
                                        getResources().getString(R.string.some_error),
                                        getResources().getString(R.string.alert), getResources().getString(R.string.ok));
                            }
                        }
                    });
                }

                //Data Validation.0

            } else {
                DialogUtils.getConfirmationDialogAlert(LoginActivity.this, getString(R.string
                                .all_fields_required), getString(R.string.alert),
                        getString(R.string.ok));
            }

        } else {
            DialogUtils.getInternetRetryDialog(LoginActivity.this, new DialogUtils.internetRetryCallback() {
                @Override
                public void onClick() {
                    loginService(view);
                }
            });
        }
    }

    /**
     * Calling notification for FCM service.
     *  @param entryID :        For save in send notification in database.
     * @param progressDialog :
     * @param emailId
     */
    private void callFCMWebService(String entryID, final ProgressDialog progressDialog, String emailId) {
        ApiClient.ApiInterface apiService = ApiClient.getnotification();
        //getting package version.
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = null;
        if (pInfo != null) {
            version = pInfo.versionName;
        }
        //Creating Notification request.
        NotificationRequest notificationRequest = new NotificationRequest(entryID,
                new SharedPreference(LoginActivity.this).getDeviceToken(), version,emailId,new SharedPreference(LoginActivity.this).getFirstName()+" "+
        new SharedPreference(getApplicationContext()).getLastName());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                notificationRequest.toString());

        Call<ResponseBody> call = apiService.getNotificationResponse(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = XML.toJSONObject(s);
                        Log.d("JSON", jsonObj.toString());
                        JSONObject responseObject = jsonObj.getJSONObject("Response");
                        JSONObject statusObject = responseObject.getJSONObject("Status");
                        Log.d("JsonObjec1", statusObject.toString());
                        Gson gson = new Gson();
                        dotNetServiceResponse sendDeviceToken = gson.fromJson(statusObject.toString(), dotNetServiceResponse.class);
                        if (AppConstants.SUCCESS_STATUS == sendDeviceToken.getContent()) {
                            progressDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                            i.putExtra("notification",-1);
                            startActivity(i);
                            finish();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        DialogUtils.getConfirmationDialogAlert(LoginActivity.this, getResources().getString(R.string.invalid_user_credentails),
                                getResources().getString(R.string.alert_title), getResources().getString(R.string.ok));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                Log.d("Error", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    /**
     * check all fields if value is filled or not.
     *
     * @return : true or false on the basis of result.
     */
    private boolean isAnyFieldEmpty() {
        boolean isEmpty = false;
        String emailId = ((EditText) findViewById(R.id.text_emailid)).getText().toString();
        String pin = ((EditText) findViewById(R.id.text_password)).getText().toString();

        if (TextUtils.isEmpty(emailId)) {
            isEmpty = true;
        }
        if (TextUtils.isEmpty(pin)) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * checking fields for valid values.
     *
     * @return : true or false on the basis of result.
     */
    private boolean checkFieldValidation() {
        boolean isValid = true;
        String emailId = ((EditText) findViewById(R.id.text_emailid)).getText().toString();
        String pin = ((EditText) findViewById(R.id.text_password)).getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId.toString().trim()).matches()) {
            DialogUtils.getConfirmationDialogAlert(LoginActivity.this, getString(R.string
                            .valid_email_required), getString(R.string.alert),
                    getString(R.string.ok));
            return false;
        }
        if (pin.length() < 6) {
            DialogUtils.getConfirmationDialogAlert(LoginActivity.this, getString(R.string
                            .password_error), getString(R.string.alert),
                    getString(R.string.ok));
            return false;
        }
        return isValid;
    }
}

