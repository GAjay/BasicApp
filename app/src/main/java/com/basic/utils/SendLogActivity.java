package com.basic.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.basic.R;
import com.basic.activities.DashboardActivity;
import com.basic.models.request.SendErrorRequest;
import com.basic.network.ApiClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *  activity to send error logs on server.
 */
public class SendLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_log);


        String errorDetail = extractLogToFile();
        if(!TextUtils.isEmpty(errorDetail)){
            SendErrorRequest sendErrorRequest = new SendErrorRequest(errorDetail,"ajay.maheshwari@ranosys.com","Dwell Error");
            Log.d("error details:",sendErrorRequest.toString());
            sendDataToServer(sendErrorRequest);
        }

    }

    private void sendDataToServer(SendErrorRequest sendErrorRequest){
        ApiClient.ApiInterface service = ApiClient.getErrorPostClient();
        Call<ResponseBody> call = service.sendError(sendErrorRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                deleteFile("stack.trace");
                DialogUtils.getConfirmationDialogAlert(com.basic.utils.SendLogActivity.this, getResources()
                        .getString(R.string.some_error), getResources().getString(R.string.alert), getResources().getString(R.string.ok), new DialogUtils.successCallback() {
                    @Override
                    public void onClick() {
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        i.putExtra("notification",-1);
                        startActivity(i);
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteFile("stack.trace");
               DialogUtils.getConfirmationDialogAlert(com.basic.utils.SendLogActivity.this,getResources()
                       .getString(R.string.some_error),getResources().getString(R.string.alert),getResources().getString(R.string.ok),new DialogUtils.successCallback() {
                    @Override
                    public void onClick() {
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        i.putExtra("notification",-1);
                        startActivity(i);
                        finish();
                    }
                });;
            }
        });
    }


    private String extractLogToFile() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        StringBuilder builder = new StringBuilder();

        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER))
            model = Build.MANUFACTURER + " " + model;


        String line = "";
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(this
                            .openFileInput("stack.trace")));
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }

        builder.append("Android Version :" + Build.VERSION.SDK_INT).append("\n")
                .append("Device :" + model).append("\n")
                .append("App Version :" + (info == null ? "(null)" : info.versionCode)).append("\n")
                .append("Is internet connected :" + Utils.isConnected(getApplicationContext())).append("\n")
                .append("Is Mobile network connected :" + Utils.isConnectedMobile(getApplicationContext())).append("\n")
                .append("Is GPS Enabled :" + Utils.isGpsEnabled(getApplicationContext())).append("\n")
                .append("Is Wifi connected :" + Utils.isConnectedWifi(getApplicationContext())).append("\n")
                .append("Is Location services Enabled :" + Utils.isLocationServiceEnabled(getApplicationContext())).append("\n");

        return builder.toString();
    }

}
