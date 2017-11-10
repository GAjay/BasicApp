package com.basic.utils;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.basic.R;
import com.basic.adapters.FilterAdapter;
import com.basic.fragment.ParcelListFragment;
import com.basic.models.FilterModel;
import java.util.ArrayList;

/**
 * Class hold various kind of dialogs definatiosn required in the application.
 */
public class DialogUtils extends AlertDialog.Builder {
    private Context mContext;
    public interface successCallback {
        void onClick();
    }
    public interface ResendCallBack {
        void onClick();
        void onResend();
    }
    public interface internetRetryCallback {
        void onClick();
    }
    public interface choiceSelectedCallback {
        void onChoiceSelected(int pos);
    }
    public DialogUtils(Context context) {
        super(context);
        mContext = context;
    }
    public static void getConfirmationDialog(Context context, String text, String title, String positiveButtonText,
                                             String negativeButtonText) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.negative_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.positive_button);
        alertDialogTitleTextView.setText(title);
        alertDialogContentTextView.setText(text);
        positiveButton.setText(positiveButtonText);
        negativeButton.setText(negativeButtonText);
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int) (deviceWidth * 0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
    }
    public static void getConfirmationDialogAlert(Context context, String text, String title, String positiveButtonText
    ) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.negative_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.positive_button);
        negativeButton.setVisibility(View.GONE);
        alertDialogTitleTextView.setText(title);
        alertDialogContentTextView.setText(text);
        positiveButton.setText(positiveButtonText);
        // negativeButton.setText(negativeButtonText);
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int)(deviceWidth*0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
    }
    public static void getConfirmationDialogAlert(Context context, String text, String title,
                                                  String positiveButtonText,
                                                  final successCallback successCallback)
    {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.negative_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.positive_button);
        negativeButton.setVisibility(View.GONE);
        alertDialogTitleTextView.setText(title);
        alertDialogContentTextView.setText(text);
        positiveButton.setText(positiveButtonText);
        // negativeButton.setText(negativeButtonText);
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int)(deviceWidth*0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successCallback.onClick();
                ad.dismiss();
            }
        });
    }
    public static void getConfirmationDialogWithCallback(Context context, String text,
                                                         String title, String positiveButtonText,
                                                         String negativeButtonText, final ResendCallBack successCallback) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.positive_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.negative_button);
        alertDialogTitleTextView.setText(title);
        alertDialogContentTextView.setText(text);
        positiveButton.setText(positiveButtonText);
        negativeButton.setText(negativeButtonText);
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int)(deviceWidth*0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successCallback.onClick();
                ad.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successCallback.onResend();
                ad.dismiss();
            }
        });
    }

    public static void getConfirmationDialogWithCallback(Context context, String text,
                                                         String title, String positiveButtonText,
                                                         String negativeButtonText, final successCallback successCallback) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.positive_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.negative_button);
        alertDialogTitleTextView.setText(title);
        alertDialogContentTextView.setText(text);
        positiveButton.setText(positiveButtonText);
        negativeButton.setText(negativeButtonText);
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int)(deviceWidth*0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successCallback.onClick();
                ad.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
    }
    // TODO : this method was created for shwoing the filters on parcel list screen
    // TODO : may be use in creating filters on upcoming screens. Remove if not use there.
    public static void getFilterDialog(final Context context, ArrayList<FilterModel> filterModelArrayList,
                                       final ParcelListFragment.filterItemClickedInterface filterItemClickedInterface) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_parcel_filter_layout, null);
        ListView filterListView = (ListView)dialogView.findViewById(R.id.filter_list_view);
        filterListView.setClickable(true);
        final FilterAdapter adapter = new FilterAdapter(context,filterModelArrayList);
        filterListView.setAdapter(adapter);
        builder.setView(dialogView);
        int deviceHeight = context.getResources().getDisplayMetrics().heightPixels;
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        final AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();
                filterItemClickedInterface.filterSelected(i);
                alertDialog.dismiss();
                alertDialog.cancel();
            }
        });
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wlp.horizontalMargin=0.0f;
        wlp.x = -150;   //x position
        wlp.y = 100;   //y position
        wlp.width = (int)(deviceWidth*0.60);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        alertDialog.setCancelable(true);
    }
    public static void getInternetRetryDialog(Context context, final internetRetryCallback internetRetryCallback) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert_dialog, null);
        builder.setView(dialogView);
        TextView alertDialogTitleTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_title_text_view);
        TextView alertDialogContentTextView = (TextView) dialogView.findViewById(R.id.alert_dialog_content_text_view);
        TextView positiveButton = (TextView) dialogView.findViewById(R.id.negative_button);
        TextView negativeButton = (TextView) dialogView.findViewById(R.id.positive_button);
        alertDialogTitleTextView.setText(context.getResources().getString(R.string.internet_connection_title));
        alertDialogContentTextView.setText(context.getResources().getString(R.string.please_check_internet));
        positiveButton.setText(context.getResources().getString(R.string.cancel_text));
        negativeButton.setText(context.getResources().getString(R.string.retry_text));
        final AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int deviceWidth = context.getResources().getDisplayMetrics().widthPixels;
        Window window = ad.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = (int)(deviceWidth*0.70);
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                internetRetryCallback.onClick();
                ad.dismiss();
            }
        });
    }
    public static void getDropDown(Context context, ArrayList<String> itemList, int selectedId,String title,
                                   final choiceSelectedCallback choiceSelectedCallback) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.single_choice_dialog, null);
        TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        dialog_title.setText(title);
        final RadioGroup choice_radio_group = (RadioGroup) dialogView.findViewById(R.id.choice_radio_group);
        // preparing category drop down and select the pre-select value
        for (int i = 0; i < itemList.size(); i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.partial_radio_button, null);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            radioButton.setId(i);
            radioButton.setText(itemList.get(i));
            radioButton.setTextSize(16);
            if (selectedId == i) {
                radioButton.setChecked(true);
            }
            choice_radio_group.addView(radioButton);
        }
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, final int checkedId) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        dialog.cancel();
                        choiceSelectedCallback.onChoiceSelected(checkedId);
                    }
                }, 250);
            }
        });
    }
}