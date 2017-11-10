package com.basic.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.basic.R;
import com.basic.interfaces.InternetDialogCallback;

/**
 * A class for internet retry dialog
 *
 * @author Ranosys Technologies
 */
public class InternetRetry {
    final private InternetDialogCallback internetDialogCallback;

    public InternetRetry(InternetDialogCallback internetDialogCallback, Context context) {
        this.internetDialogCallback = internetDialogCallback;
        getInternetDialog(context);
    }

    /**
     * A method for showing internet dialog
     *
     * @param context: context
     */
    private void getInternetDialog(Context context) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context, R.style.InternetDialog);
        builder.setMessage(context.getResources().getString(R.string.please_check_internet));
        // A button to cancel internet dialog
        builder.setNegativeButton(context.getResources().getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                internetDialogCallback.getInternetDialogResponse(false);
            }
        });
        // A button to retry internet
        builder.setPositiveButton(context.getResources().getString(R.string.retry_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                internetDialogCallback.getInternetDialogResponse(true);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
