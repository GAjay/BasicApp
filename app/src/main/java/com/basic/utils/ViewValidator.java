package com.basic.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.basic.R;

/**
 * A class for checking validations of EditTexts
 *
 * @author Ranosys Technologies
 */
public class ViewValidator {

    private final Context mContext;
    private final View mView;
    private boolean isValid = true;
    private boolean isBlankFieldChecked = false;

    public ViewValidator(View view,Context context) {
        mContext = context;
        mView = view;
    }

    public boolean checkValidation(ViewGroup rootLayout) {
        // isValid = true;
        int childCount = rootLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (rootLayout.getChildAt(i) instanceof ViewGroup) {
                isValid = checkValidation((ViewGroup) rootLayout.getChildAt(i));
            } else if (rootLayout.getChildAt(i) instanceof View) {
                View view = rootLayout.getChildAt(i);
                if (view instanceof EditText) {
                    EditText inputFieldLayout = (EditText) view;
                    switch (inputFieldLayout.getId()) {
                        case R.id.text_password:
                            validationForPassword(inputFieldLayout);
                            break;
                        case R.id.text_emailid:
                            emailValidator(inputFieldLayout);
                            break;

                    }
                }
            }
        }
        return isValid;
    }


    // A method for checking whether field is empty.
    private boolean checkBlankFieldValidation(EditText textInputLayout) {
        boolean errorOccured = false;
        if (textInputLayout.getText().toString().trim().length() == 0) {
            com.basic.utils.DialogUtils.getConfirmationDialogAlert(mContext, mContext.getString(R.string
                            .all_fields_required), mContext.getString(R.string.alert),
                    mContext.getString(R.string.ok));
            isValid = false;
            errorOccured = true;
        }
        return errorOccured;
    }

    // A method for checking whether password is present.
    private void validationForPassword(EditText textInputLayout) {

        if (textInputLayout.getText().toString().length() < 6) {

            com.basic.utils.DialogUtils.getConfirmationDialogAlert(mContext, mContext.getString(R.string
                            .password_error), mContext.getString(R.string.alert),
                    mContext.getString(R.string.ok));
            isValid = false;
        }

    }


    // A method for checking whether email is valid.
    private void emailValidator(EditText textInputLayout) {
        String value = textInputLayout.getText().toString();
      /*  if (TextUtils.isEmpty(value)) {
            textInputLayout.setError(vector_drawable_email);
            isValid = false;
        } else {*/
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {

           // Utils.showSnackBar(mView, mContext.getResources().getString(R.string.valid_email_required));
            DialogUtils.getConfirmationDialogAlert(mContext, mContext.getString(R.string
                            .valid_email_required), mContext.getString(R.string.alert),
                    mContext.getString(R.string.ok));
            isValid = false;

          /*  }*/

        }
    }
}