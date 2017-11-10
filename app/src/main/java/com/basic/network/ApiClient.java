package com.basic.network;

import com.basic.models.request.SendErrorRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * API client class will communicate to netwok via web services.
 */
public class ApiClient {

    public static final String BASE_URL = "http://local.com";
    public static String errorURL = "http://local.com";
    //Local Url
   // public static final String NOTIFICATION_URL ="http://ranosys.info/StarrezNotification/";
    public static final String NOTIFICATION_URL = "http://local.com";

    private static ApiInterface gitApiInterface;

    public static ApiInterface getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.connectTimeout(150, TimeUnit.SECONDS);
        okClient.readTimeout(150,TimeUnit.SECONDS);
        okClient.addInterceptor(logging);
      
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        gitApiInterface = client.create(ApiInterface.class);
        return gitApiInterface;
    }

    public static ApiInterface getErrorPostClient() {
        if (gitApiInterface == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okClient = new OkHttpClient.Builder();
            okClient.addInterceptor(logging);
            okClient.connectTimeout(150, TimeUnit.SECONDS);
            okClient.readTimeout(150,TimeUnit.SECONDS);
            okClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(errorURL)
                    .client(okClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gitApiInterface = client.create(ApiInterface.class);
            return gitApiInterface;
        }
        return gitApiInterface;
    }


    public static ApiInterface getnotification() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.connectTimeout(150, TimeUnit.SECONDS);
        okClient.readTimeout(150,TimeUnit.SECONDS);
        okClient.addInterceptor(logging);

        Retrofit client = new Retrofit.Builder()
                .baseUrl(NOTIFICATION_URL)
                .client(okClient.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        gitApiInterface = client.create(ApiInterface.class);
        return gitApiInterface;
    }


    /**
     * Interface hold various method for web service calling.
     */
    public interface ApiInterface {


        /** Method to send the error logs via web service on server.
         * @param sendErrorRequest : error object.
         * @return : resposne body return raw response from server.
         */
        @POST("crash.php")
        Call <ResponseBody> sendError(@Body SendErrorRequest sendErrorRequest);

        /** Method to execute login request.
         * @param loginQuery :  hold the login details entered by user.
         * @return : return raw responsne returned from server.
         */
        @POST("services/query")
        Call<ResponseBody> getloginresponse(@Body RequestBody loginQuery);

        /** Method to fetch the parcel list from server.
         * @param parcelQuery : hold the details requried to fetch the parcel list.
         * @return : return the raw response.
         */
        @POST("services/query")
        Call<ResponseBody> getParcelList(@Body RequestBody parcelQuery);

        @POST("api/SaveUser")
        Call<ResponseBody>getNotificationResponse(@Body RequestBody notificationData);

        /**Method For resource List
         * @param resourceListQuery : hold text plain query for resource list.
         * @return : return the raw response.
         */
        @POST("services/query")
        Call<ResponseBody> getResourceList(@Body RequestBody resourceListQuery);
        /** Method to fetch the parcel list from server.
         * @param requestBody : hold the details requried to fetch the data.
         * @return : return the raw response.
         */
        @POST("services/query")
        Call<ResponseBody> callAPI(@Body RequestBody requestBody);

        @POST("services/update/RoomSpaceMaintenance/{id}")
        Call<ResponseBody> closeMaintenanceRequest(@Path("id") String maintenanceId, @Body RequestBody requestBody);

        @POST("services/create/ResourceBooking")
        Call<ResponseBody> resourceBooking(@Body RequestBody resourceListQuery);

        @POST("services/create/roomspacemaintenance")
        Call<ResponseBody> addNewMaintenanceRequest(@Body RequestBody requestBody);

        @GET("services/photo/RecordAttachment/{id}")
        Call<ResponseBody> getMaintenanceImage(@Path("id") String maintenanceId);

        @POST ("api/logout")
        Call<ResponseBody> logout(@Body RequestBody requestBody);

        /** Method to execute login request.
         * @param priorityIDQueries :  hold the priorityIDs.
         * @return : return raw responsne returned from server.
         */
        @POST("services/query")
        Call<ResponseBody> getPriorityIDS(@Body RequestBody priorityIDQueries);
    }

}


