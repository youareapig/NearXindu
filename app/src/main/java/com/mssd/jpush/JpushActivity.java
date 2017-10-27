package com.mssd.jpush;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.NoticeAdapter;
import com.mssd.data.JpushBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.DbManager;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

public class JpushActivity extends AutoLayoutActivity {
    @BindView(R.id.notice_recycle)
    RecyclerView noticeRecycle;
    @BindView(R.id.notice_title)
    TextView noticeTitle;
    @BindView(R.id.notice_scroll)
    MyScrollView noticeScroll;
    @BindView(R.id.shijia_back)
    RelativeLayout shijiaBack;
    private String requestContent;
    private Unbinder unbinder;
    private List<JpushBean> list = new ArrayList<>();
    private JpushBean jpushBean;
    DbManager.DaoConfig config = new DbManager.DaoConfig()
            .setDbName("mydata.db")
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            });
    DbManager db = x.getDb(config);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush);
        unbinder = ButterKnife.bind(this);

        init();
    }

    private void init() {

        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        noticeTitle.setTypeface(typeface);
//        Intent intent = getIntent();
//        if (intent != null) {
//            try {
//                Bundle bundle = getIntent().getExtras();
//                requestContent = bundle.getString(JPushInterface.EXTRA_ALERT);
//                jpushBean = new JpushBean(requestContent, getDate());
//                list.add(jpushBean);
//                db.save(list);
//            } catch (Exception e) {
//                Log.e("tag", "无消息可插入");
//            }
//        }

        try {
            list = db.selector(JpushBean.class).orderBy("id", true).findAll();
            if (list != null) {
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                noticeRecycle.addItemDecoration(new ListItemDecoration(150));
                noticeRecycle.setLayoutManager(gridLayoutManager);
                noticeRecycle.setAdapter(new NoticeAdapter(list, this));
            } else {
                ToastUtils.showShort(this, "暂无消息");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.shijia_back)
    public void onViewClicked() {
        finish();
    }
}
