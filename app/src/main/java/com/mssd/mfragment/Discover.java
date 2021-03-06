package com.mssd.mfragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Discover_Recycle1;
import com.mssd.adapter.Discover_Recycle2;
import com.mssd.adapter.Discover_Recycle3;
import com.mssd.adapter.Discover_Recycle4;
import com.mssd.data.DiscoverBean;
import com.mssd.html.WebActivity;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration2;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.GalleryActivity;
import com.mssd.zl.GoodsActivity;
import com.mssd.zl.PedestrianActivity;
import com.mssd.zl.R;
import com.mssd.zl.TalkHistoryActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover extends Fragment {
    @BindView(R.id.discover_tx1)
    TextView discoverTx1;
    @BindView(R.id.discover_tx2)
    TextView discoverTx2;
    @BindView(R.id.discover_recycle1)
    RecyclerView discoverRecycle1;
    @BindView(R.id.discover_recycle2)
    RecyclerView discoverRecycle2;
    @BindView(R.id.discover_recycle3)
    RecyclerView discoverRecycle3;
    @BindView(R.id.discover_recycle4)
    RecyclerView discoverRecycle4;
    @BindView(R.id.discover_Topimg)
    ImageView discoverTopimg;
    @BindView(R.id.discover_scroll)
    MyScrollView discoverScroll;
    @BindView(R.id.discover_text)
    TextView discoverText;
    @BindView(R.id.discover_img1)
    ImageView discoverImg1;
    @BindView(R.id.discover_text1)
    TextView discoverText1;
    @BindView(R.id.discover_img2)
    ImageView discoverImg2;
    @BindView(R.id.discover_text2)
    TextView discoverText2;
    @BindView(R.id.discover_img3)
    ImageView discoverImg3;
    @BindView(R.id.discover_text3)
    TextView discoverText3;
    @BindView(R.id.discover_img4)
    ImageView discoverImg4;
    @BindView(R.id.discover_text4)
    TextView discoverText4;
    @BindView(R.id.discover_pull)
    PullToRefreshLayout discoverPull;
    private Unbinder unbinder;
    private List<DiscoverBean.DataBeanXXXX.T1Bean.DataBean> list1;
    private List<DiscoverBean.DataBeanXXXX.T2Bean.DataBeanX> list2;
    private List<DiscoverBean.DataBeanXXXX.T3Bean.DataBeanXX> list3;
    private List<DiscoverBean.DataBeanXXXX.T4Bean.DataBeanXXX> list4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        getNetBean();
        discoverPull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getNetBean();
                        discoverPull.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {

            }
        });
        discoverPull.setCanLoadMore(false);
        return view;
    }


    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        discoverTx1.setTypeface(typeface1);
        discoverTx2.setTypeface(typeface);
        discoverText.setTypeface(typeface1);
        discoverText1.setTypeface(typeface1);
        discoverText2.setTypeface(typeface1);
        discoverText3.setTypeface(typeface1);
        discoverText4.setTypeface(typeface1);
    }

    private void getRecycler1() {
        discoverRecycle1.addItemDecoration(new SpacesItemDecoration2(40));
        discoverRecycle1.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle1.setAdapter(new Discover_Recycle1(list1, getActivity()));
    }

    private void getRecycler2() {
        discoverRecycle2.addItemDecoration(new SpacesItemDecoration2(40));
        discoverRecycle2.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle2.setAdapter(new Discover_Recycle2(list2, getActivity()));
    }

    private void getRecycler3() {
        discoverRecycle3.addItemDecoration(new SpacesItemDecoration2(40));
        discoverRecycle3.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle3.setAdapter(new Discover_Recycle3(list3, getActivity()));
    }

    private void getRecycler4() {
        discoverRecycle4.addItemDecoration(new SpacesItemDecoration2(40));
        discoverRecycle4.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle4.setAdapter(new Discover_Recycle4(list4, getActivity()));
    }

    private void getNetBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(getActivity(), R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        discoverScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/probe");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                discoverScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                DiscoverBean bean = gson.fromJson(result, DiscoverBean.class);
                if (bean.getCode() == 1000) {
                    discoverText.setText(bean.getData().getTop().getTitle());
                    Glide.with(getActivity()).load(bean.getData().getTop().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(discoverTopimg);
                    discoverText1.setText(bean.getData().getT1().getPic().getContent());
                    Glide.with(getActivity()).load(bean.getData().getT1().getPic().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(discoverImg1);
                    discoverText2.setText(bean.getData().getT2().getPic().getContent());
                    Glide.with(getActivity()).load(bean.getData().getT2().getPic().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(discoverImg2);
                    discoverText3.setText(bean.getData().getT3().getPic().getContent());
                    Glide.with(getActivity()).load(bean.getData().getT3().getPic().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(discoverImg3);
                    discoverText4.setText(bean.getData().getT4().getPic().getContent());
                    Glide.with(getActivity()).load(bean.getData().getT4().getPic().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(discoverImg4);


                    list1 = bean.getData().getT1().getData();
                    list2 = bean.getData().getT2().getData();
                    list3 = bean.getData().getT3().getData();
                    list4 = bean.getData().getT4().getData();
                    getRecycler1();
                    getRecycler2();
                    getRecycler3();
                    getRecycler4();
                } else {
                    ToastUtils.showShort(getActivity(), R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(), R.string.erroe);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                customProgressDialog.cancel();
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.discover_Topimg, R.id.discover_img1, R.id.discover_img2, R.id.discover_img3, R.id.discover_img4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.discover_Topimg:
                Intent intent4 = new Intent(getActivity(), WebActivity.class);
                intent4.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "show/playvideo");
                startActivity(intent4);
                break;
            case R.id.discover_img1:
                Intent intent = new Intent(getActivity(), TalkHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.discover_img2:
                Intent intent1 = new Intent(getActivity(), PedestrianActivity.class);
                startActivity(intent1);
                break;
            case R.id.discover_img3:
                Intent intent2 = new Intent(getActivity(), GoodsActivity.class);
                startActivity(intent2);
                break;
            case R.id.discover_img4:
                Intent intent3 = new Intent(getActivity(), GalleryActivity.class);
                startActivity(intent3);
                break;
        }
    }


}
