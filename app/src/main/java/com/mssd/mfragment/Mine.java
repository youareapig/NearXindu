package com.mssd.mfragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mssd.zl.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 2017/8/30.
 */

public class Mine extends Fragment {
    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_editprofil)
    TextView mineEditprofil;
    @BindView(R.id.mine_text_notice)
    TextView mineTextNotice;
    @BindView(R.id.mine_text_place)
    TextView mineTextPlace;
    @BindView(R.id.mine_text_setting)
    TextView mineTextSetting;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        return view;
    }
    private void changeFont(){
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        mineEditprofil.setTypeface(typeface);
        mineName.setTypeface(typeface);
        mineTextNotice.setTypeface(typeface);
        mineTextPlace.setTypeface(typeface);
        mineTextSetting.setTypeface(typeface);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
