package com.mssd.mfragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.Exploration_Recycle_Food;
import com.mssd.adapter.Exploration_Recycle_House;
import com.mssd.adapter.Exploration_Recycle_Place;
import com.mssd.data.TansuoBean;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.net.BaseFragment;
import com.mssd.net.NetWorkBroadcastReceiver;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration2;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.FoodActivity;
import com.mssd.zl.HistoryActivity;
import com.mssd.zl.R;
import com.mssd.zl.StayActivity;
import com.mssd.zl.TripActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.exploration_viewpager)
    ViewPager explorationViewpager;
    @BindView(R.id.exploration_viewpager_group)
    AutoLinearLayout explorationViewpagerGroup;
    @BindView(R.id.exploration_tx1)
    TextView explorationTx1;
    @BindView(R.id.exploration_tx2)
    TextView explorationTx2;
    @BindView(R.id.exploration_tx3)
    TextView explorationTx3;
    @BindView(R.id.exploration_recy_food)
    RecyclerView explorationRecyFood;
    @BindView(R.id.exploration_tx4)
    TextView explorationTx4;
    @BindView(R.id.exploration_recy_house)
    RecyclerView explorationRecyHouse;
    @BindView(R.id.exploration_tx5)
    TextView explorationTx5;
    @BindView(R.id.exploration_recy_place)
    RecyclerView explorationRecyPlace;
    @BindView(R.id.exploration_scroll)
    MyScrollView explorationScroll;
    @BindView(R.id.exploration_classfiy1_name)
    TextView explorationClassfiy1Name;
    @BindView(R.id.exploration_classfiy1)
    AutoLinearLayout explorationClassfiy1;
    @BindView(R.id.exploration_classfiy2_name)
    TextView explorationClassfiy2Name;
    @BindView(R.id.exploration_classfiy2)
    AutoLinearLayout explorationClassfiy2;
    @BindView(R.id.exploration_classfiy3_name)
    TextView explorationClassfiy3Name;
    @BindView(R.id.exploration_classfiy3)
    AutoLinearLayout explorationClassfiy3;
    @BindView(R.id.exploration_classfiy4_name)
    TextView explorationClassfiy4Name;
    @BindView(R.id.exploration_classfiy4)
    AutoLinearLayout explorationClassfiy4;
    @BindView(R.id.exploration_eat)
    AutoRelativeLayout explorationEat;
    @BindView(R.id.exploration_stay)
    AutoRelativeLayout explorationStay;
    @BindView(R.id.exploration_place)
    AutoRelativeLayout explorationPlace;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, viewpagerImage;
    private Handler handler;
    private ViewPagerThread thread;
    private List<TansuoBean.DataBean.BannerBean> bannerList;
    private List<TansuoBean.DataBean.MealBean> list1;
    private List<TansuoBean.DataBean.StayBean> list2;
    private List<TansuoBean.DataBean.LineBean> list3;
    //NetWorkBroadcastReceiver mNetBroadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exploration, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        getNetBean();
        return view;
    }

/*    @Override
    public void onNetWorkChange(int netMobile) {
        Log.e("tag", "网络状态" + netMobile);
        if (netMobile == 1||netMobile==0) {
            getNetBean();
        }
        super.onNetWorkChange(netMobile);
    }

    //TODO 注册广播
    @Override
    public void onResume() {
        if (mNetBroadcastReceiver == null) {
            mNetBroadcastReceiver = new NetWorkBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetBroadcastReceiver, filter);
        super.onResume();

    }

    //TODO 销毁广播
    @Override
    public void onPause() {
        getActivity().unregisterReceiver(mNetBroadcastReceiver);
        super.onPause();

    }*/

    @Override
    public void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(getActivity(),getActivity().getClass().getCanonicalName());
    }

    @Override
    public void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(getActivity(),getActivity().getClass().getCanonicalName());
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        explorationTx2.setTypeface(typeface);
        explorationTx1.setTypeface(typeface1);
        explorationTx3.setTypeface(typeface1);
        explorationTx4.setTypeface(typeface1);
        explorationTx5.setTypeface(typeface1);
        explorationClassfiy1Name.setTypeface(typeface1);
        explorationClassfiy2Name.setTypeface(typeface1);
        explorationClassfiy3Name.setTypeface(typeface1);
        explorationClassfiy4Name.setTypeface(typeface1);
    }

    private void banner() {
        thread = new ViewPagerThread();
        viewpagerTips = new ImageView[bannerList.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(15, 15);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);

            }
            explorationViewpagerGroup.addView(imageView);
        }
        viewpagerImage = new ImageView[bannerList.size()];
        for (int i = 0; i < viewpagerImage.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewpagerImage[i] = imageView;
            Glide.with(getActivity()).load(bannerList.get(i).getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(imageView);

        }
        explorationViewpager.setOnPageChangeListener(Exploration.this);
        explorationViewpager.setAdapter(new BannerAdapter(viewpagerImage,bannerList));
        handler = new Handler() {
            int bannerNo = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (explorationViewpager.getCurrentItem() == viewpagerImage.length - 1) {
                        bannerNo = 0;
                    } else {
                        bannerNo = explorationViewpager.getCurrentItem() + 1;
                    }
                    explorationViewpager.setCurrentItem(bannerNo, true);
                } catch (Exception e) {

                }

            }
        };
        thread.start();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % viewpagerImage.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {
            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
            }
        }
    }


    @OnClick({R.id.exploration_classfiy1, R.id.exploration_classfiy2, R.id.exploration_classfiy3, R.id.exploration_classfiy4, R.id.exploration_eat, R.id.exploration_place, R.id.exploration_stay})
    public void onViewClicked(View view) {
        Intent intent1, intent2, intent3, intent4, intent5, intent6, intent7;
        switch (view.getId()) {
            case R.id.exploration_classfiy1:
                intent1 = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent1);
                //getActivity().overridePendingTransition(R.anim.in,R.anim.out);
                break;
            case R.id.exploration_classfiy2:
                intent2 = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent2);
                break;
            case R.id.exploration_classfiy3:
                intent3 = new Intent(getActivity(), StayActivity.class);
                startActivity(intent3);
                break;
            case R.id.exploration_classfiy4:
                intent4 = new Intent(getActivity(), TripActivity.class);
                startActivity(intent4);
                break;
            case R.id.exploration_eat:
                intent5 = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent5);
                break;
            case R.id.exploration_place:
                intent6 = new Intent(getActivity(), TripActivity.class);
                startActivity(intent6);
                break;
            case R.id.exploration_stay:
                intent7 = new Intent(getActivity(), StayActivity.class);
                startActivity(intent7);
                break;
        }
    }

    private class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void getFood() {
        explorationRecyFood.addItemDecoration(new SpacesItemDecoration2(40));
        explorationRecyFood.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        explorationRecyFood.setAdapter(new Exploration_Recycle_Food(list1, getActivity()));
    }

    private void getHouse() {
        explorationRecyHouse.addItemDecoration(new SpacesItemDecoration2(40));
        explorationRecyHouse.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        explorationRecyHouse.setAdapter(new Exploration_Recycle_House(list2, getActivity()));
    }

    private void getPlace() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        explorationRecyPlace.addItemDecoration(new ListItemDecoration(100));
        explorationRecyPlace.setLayoutManager(linearLayoutManager);
        explorationRecyPlace.setAdapter(new Exploration_Recycle_Place(list3, getActivity()));
    }

    private void getNetBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(getActivity(), R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        explorationScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/index");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                explorationScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                TansuoBean bean = gson.fromJson(result, TansuoBean.class);
                if (bean.getCode() == 1000) {
                    bannerList = bean.getData().getBanner();
                    list1 = bean.getData().getMeal();
                    list2 = bean.getData().getStay();
                    list3 = bean.getData().getLine();
                    banner();
                    getFood();
                    getHouse();
                    getPlace();
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
}
