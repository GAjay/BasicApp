package com.basic.interfaces;

/**
 * Call back, will be triggerd when there is no internet connection.
 */
public interface InternetDialogCallback {
    void getInternetDialogResponse(Boolean isRetryButtonClicked);
}
