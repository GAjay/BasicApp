package com.basic.fragment;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.R;
import com.basic.activities.ContainerActivity;
import com.basic.models.response.parcellistresponse.Entry;
import com.basic.utils.AppConstants;
import com.basic.utils.DateUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParcelDetailFragment extends Fragment {


    public ParcelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.parcel_detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_parcel_detail, container, false);
        ((ContainerActivity)getActivity()).setBackground(R.drawable.ic_parcel_bg);
        Bundle bundle = getArguments();
        if(null!=bundle){
            Entry parcelDetail = (Entry)bundle.getParcelable("entryParcelDetail");
            setData(rootView,parcelDetail);
        }
        return rootView;
    }

    /** Method to show the data on screen.
     * @param rootView : is the root view of this fragment, holds all the child views.
     * @param parcelDetail : Object to hold the details of this particular parcel.
     */
    private void setData(View rootView, Entry parcelDetail) {
        TextView parcelTitleTextView = (TextView)rootView.findViewById(R.id.parcel_title_text_view);

        TextView parcel_type_value_text_view = (TextView)rootView.
                findViewById(R.id.parcel_type_value_text_view);
        TextView receipt_date_value_text_view = (TextView)rootView.
                findViewById(R.id.receipt_date_value_text_view);
        TextView shipping_type_value_text_view = (TextView)rootView.
                findViewById(R.id.shipping_type_value_text_view);
        TextView issue_date_value_text_view = (TextView)rootView.
                findViewById(R.id.issue_date_value_text_view);
        TextView forwading_address_value_text_view = (TextView)rootView.
                findViewById(R.id.forwading_address_value_text_view);
        TextView tracking_no_value_text_view = (TextView)rootView.
                findViewById(R.id.tracking_no_value_text_view);
        TextView admin_comments_value_text_view = (TextView)rootView.
                findViewById(R.id.admin_comments_value_text_view);
        TextView parcel_status_text_view = (TextView)rootView.
                findViewById(R.id.parcel_status_text_view);
        View view_dotted_parcel_detail_divider_title = (View) rootView.findViewById
                (R.id.view_dotted_parcel_detail_divider_title);

        LinearLayout parcel_bg = (LinearLayout) rootView.findViewById
                (R.id.parcel_status_background);

        view_dotted_parcel_detail_divider_title.getBackground().setColorFilter(getResources().getColor
                (R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

       if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getDescription())){
           parcelTitleTextView.setText("No title available");

       } else{
           parcelTitleTextView.setText(
                   parcelDetail.getContent().getRecord().getDescription());
       }

        if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getParcelTypeValue())){
            parcel_type_value_text_view.setText("NA");
        }
        else{
            parcel_type_value_text_view.setText(parcelDetail.getContent().getRecord().getParcelTypeValue());
        }

        if(null!=parcelDetail.getContent().getRecord().getReceiptDate() &&
                !TextUtils.isEmpty(parcelDetail.getContent().getRecord().getReceiptDate())){
            receipt_date_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime(
                    parcelDetail.getContent().getRecord().getReceiptDate().replace("T"," "))));
        }else{
            receipt_date_value_text_view.setText("NA");
        }

        shipping_type_value_text_view.setText(parcelDetail.getContent().getRecord().getShippingTypeValue());

        if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getIssueDate())){
            issue_date_value_text_view.setText("NA");
        }else{
            issue_date_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime
                    (parcelDetail.getContent().getRecord().getIssueDate().replace("T"," "))));
        }

        if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getAddressValue())){
            forwading_address_value_text_view.setText("NA");
        }else {
            forwading_address_value_text_view.setText(parcelDetail.getContent().getRecord().getAddressValue());
        }
        if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getTrackingNumber())){
            tracking_no_value_text_view.setText("NA");
        }else {
            tracking_no_value_text_view.setText(parcelDetail.getContent().getRecord().getTrackingNumber());
        }
        if(TextUtils.isEmpty(parcelDetail.getContent().getRecord().getComments())){
            admin_comments_value_text_view.setText("NA");
        }else{
            admin_comments_value_text_view.setText(parcelDetail.getContent().getRecord().getComments());
        }


        // setting the status of parcel.
        int status = parcelDetail.getContent().getRecord().getParcelStatusEnum();
        switch (status) {
            case AppConstants.PARCEL_STAUS_RECEIVED:
                parcel_status_text_view.setText("New Parcel");
                parcel_bg.setBackground(ContextCompat.getDrawable(getActivity(),
                        R.drawable.drawable_bottom_corner_round_bg_green));
                break;
            case AppConstants.PARCEL_STATUS_RETURNED:
                parcel_status_text_view.setText(parcelDetail.getContent().getRecord().getStatusDescription());
                parcel_bg.setBackground(ContextCompat.getDrawable(getActivity(),
                        R.drawable.drawable_bottom_corner_round_bg_red));
                break;
            case AppConstants.PARCEL_STATUS_FORWARD:
                parcel_status_text_view.setText(parcelDetail.getContent().getRecord().getStatusDescription());
                parcel_bg.setBackground(ContextCompat.getDrawable(getActivity(),
                        R.drawable.drawable_bottom_corner_round_bg_yellow));
                break;
            case AppConstants.PARCEL_STATUS_ISSUED:
                parcel_status_text_view.setText("Collected");
                parcel_bg.setBackground(ContextCompat.getDrawable(getActivity(),
                        R.drawable.drawable_bottom_corner_round_bg_oragne));
                break;
            default:
                parcel_status_text_view.setText(parcelDetail.getContent().getRecord().getStatusDescription());
                parcel_bg.setBackground(ContextCompat.getDrawable(getActivity(),
                        R.drawable.drawable_bottom_corner_round_bg_oragne));
                break;
        }
    }
}
