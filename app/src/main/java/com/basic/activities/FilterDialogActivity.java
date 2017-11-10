package com.basic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.basic.R;
import com.basic.adapters.FilterAdapter;
import com.basic.models.FilterModel;

import java.util.ArrayList;

/**
 * Dialog activity to show the parcel filter in the applicaiton.
 */
public class FilterDialogActivity extends AppCompatActivity {

    // array list of filter elements.
    ArrayList<FilterModel> filterModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_dialog);

        int deviceWidth = getResources().getDisplayMetrics().widthPixels;

        // setting the position and size of activity on device screen.
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -150;
        params.width = (int)(0.70*deviceWidth);
        params.gravity = Gravity.RIGHT|Gravity.TOP;
        params.y = 45;

        this.getWindow().setAttributes(params);


        filterModelArrayList = getIntent().getParcelableArrayListExtra("filterList");




        ListView filterListView = (ListView)findViewById(R.id.filterListView);
        filterListView.setBackground(getResources().getDrawable(R.drawable.drawable_filter_dialog_bg));
        final FilterAdapter adapter = new FilterAdapter(getApplicationContext(),filterModelArrayList);
        filterListView.setAdapter(adapter);

        // list item click listener.
        filterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        filterModelArrayList.get(i).setChecked(true);
                        adapter.notifyDataSetChanged();
                        Intent intent = new Intent();
                        intent.putExtra("pos",i);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }, 50);

            }
        });
    }
}
