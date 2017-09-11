package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.Gallery_Recycle;
import com.mssd.utils.SpacesItemDecoration3;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GalleryActivity extends AutoLayoutActivity {
    @BindView(R.id.gallery_title)
    TextView galleryTitle;
    @BindView(R.id.gallery_recycle)
    RecyclerView galleryRecycle;
    @BindView(R.id.gallery_text1)
    TextView galleryText1;
    @BindView(R.id.gallery_text2)
    TextView galleryText2;
    private Unbinder unbinder;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);

    }
    private void changeFont(){
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        galleryTitle.setTypeface(typeface1);
        galleryText1.setTypeface(typeface1);
        galleryText2.setTypeface(typeface1);
    }

    private void getBean() {
        galleryRecycle.setAdapter(new Gallery_Recycle(list, this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        galleryRecycle.setLayoutManager(gridLayoutManager);
        galleryRecycle.addItemDecoration(new SpacesItemDecoration3(30));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
