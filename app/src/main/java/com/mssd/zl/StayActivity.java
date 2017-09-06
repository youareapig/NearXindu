package com.mssd.zl;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.mssd.adapter.Stay_Gallery;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StayActivity extends AutoLayoutActivity {
    @BindView(R.id.stay_gallery)
    Gallery stayGallery;
    private Unbinder unbinder;
    private List<String> list;
    private Stay_Gallery gallery_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);
        unbinder = ButterKnife.bind(this);
        initbean();
        getGallery();
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add("酒店");
        list.add("客栈");
        list.add("民宿");
        list.add("青旅");
        list.add("露营");

    }

    private void getGallery() {
        gallery_adapter = new Stay_Gallery(list, this);
        stayGallery.setAdapter(gallery_adapter);
        stayGallery.setSpacing(160);
        stayGallery.setSelection(5 * 200);
        stayGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gallery_adapter.setSelectItem(position % list.size());
                gallery_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
