package com.basic.fragment;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basic.R;
import com.basic.activities.ContainerActivity;
import com.basic.models.response.resourcelistresponse.Entry;
import com.basic.utils.AppConstants;
import com.basic.utils.DateUtils;

/**
 * A Fragment for resource detail view.
 */
public class ResourceDetailFragment extends Fragment {

    public ResourceDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.resource_detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_resource_detail, container, false);
        ((ContainerActivity)getActivity()).setBackground(R.drawable.ic_resource_bg);
        Bundle bundle = getArguments();
        if (null != bundle) {
            Entry resourceDetail = bundle.getParcelable("entryResourceDetail");
            setData(rootView, resourceDetail);
        }
        return rootView;
    }

    /**
     * Method to show the data on screen.
     *
     * @param rootView       : is the root view of this fragment, holds all the child views.
     * @param resourceDetail : Object to hold the details of this particular resource.
     */
    private void setData(View rootView, Entry resourceDetail) {

        TextView resource_title_text_view = (TextView) rootView.findViewById(R.id.resource_title_text_view);
        TextView resource_type_value_text_view = (TextView) rootView.findViewById(R.id.resource_type_value_text_view);
        TextView date_to_value_text_view = (TextView) rootView.findViewById(R.id.date_to_value_text_view);
        TextView date_from_value_text_view = (TextView) rootView.findViewById(R.id.date_from_value_text_view);
        TextView description_value_text_view = (TextView) rootView.findViewById(R.id.description_value_text_view);
        TextView admin_comments_value_text_view = (TextView) rootView.findViewById(R.id.admin_comments_value_text_view);
        TextView resource_status_text_view = (TextView) rootView.findViewById(R.id.resource_status_text_view);
        LinearLayout resource_bg =(LinearLayout) rootView.findViewById(R.id.resource_status_detail_background);
        View view_dotted_resource_detail_divider_title = (View) rootView.findViewById
                (R.id.view_dotted_resource_detail_divider_title);

        view_dotted_resource_detail_divider_title.getBackground().setColorFilter(getResources().getColor
                (R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        resource_type_value_text_view.setText(resourceDetail.getContent().getRecord().
                getmResourceTypeEnum());

        if (TextUtils.isEmpty(resourceDetail.getContent().getRecord().getmResource())) {
            resource_title_text_view.setText("No title available");

        } else {
            resource_title_text_view.setText(resourceDetail.
                    getContent().getRecord().getmResource());
        }

        if (TextUtils.isEmpty(resourceDetail.getContent().getRecord().getmDateStart())) {
            date_to_value_text_view.setText("NA");
        } else {
            date_to_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime(
                    resourceDetail.getContent().getRecord().getmDateEnd().replace("T", " "))));
        }
        if (TextUtils.isEmpty(resourceDetail.getContent().getRecord().getmDateEnd())) {
            date_from_value_text_view.setText("NA");
        } else {
            date_from_value_text_view.setText(DateUtils.getDateTime(DateUtils.convertToDeviceTime(
                    resourceDetail.getContent().getRecord().getmDateStart().replace("T", " "))));
        }

        if (TextUtils.isEmpty(DateUtils.getDate(resourceDetail.getContent().getRecord().getmDescription()))) {
            description_value_text_view.setText("NA");
        } else {
            description_value_text_view.setText(resourceDetail.getContent().getRecord().getmDescription());
        }


        if (TextUtils.isEmpty(DateUtils.getDate(resourceDetail.getContent().getRecord().getmComments()))) {
            admin_comments_value_text_view.setText("NA");
        } else {
            admin_comments_value_text_view.setText(resourceDetail.getContent().getRecord().getmComments());
            ;
        }


        // setting the status of resource.
        int status = resourceDetail.getContent().getRecord().getmResourceBookingStatusEnum();
        switch (status) {
            case AppConstants.RESOURCE_STATUS_ASSIGNED:
                resource_status_text_view.setText(resourceDetail.getContent().getRecord().getmStatus());
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable( R.drawable.drawable_bottom_corner_round_bg_purple));
                break;
            case AppConstants.RESOURCE_STATUS_OUT:
                resource_status_text_view.setText("Collected");
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.drawable_bottom_corner_round_bg_oragne));
                break;
            case AppConstants.RESOURCE_STATUS_CANCELLED:
                resource_status_text_view.setText(resourceDetail.getContent().getRecord().getmStatus());
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.drawable_bottom_corner_round_bg_red));
                break;
            case AppConstants.RESOURCE_STATUS_HISTORY:
                resource_status_text_view.setText(resourceDetail.getContent().getRecord().getmStatus());
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.drawable_bottom_corner_round_bg_blue));
                break;
            case AppConstants.RESOURCE_STATUS_PENDING_RETURN:
                resource_status_text_view.setText(resourceDetail.getContent().getRecord().getmStatus());
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.drawable_bottom_corner_round_bg_yellow));
                break;

            default:
                resource_status_text_view.setText(resourceDetail.getContent().getRecord().getmStatus());
                resource_bg.setBackground(getActivity().getResources().
                        getDrawable(R.drawable.drawable_bottom_corner_round_bg_gray_status));
                break;
        }
    }
}
