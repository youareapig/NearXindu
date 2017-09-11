package com.mssd.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration3 extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration3(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;
        outRect.bottom=space;
    }
}