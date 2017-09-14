package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mssd.adapter.Search_Recycle;
import com.mssd.data.HistoryBean;
import com.mssd.utils.SpacesItemDecoration3;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchActivity extends AutoLayoutActivity {
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.search_go)
    ImageView searchGo;
    @BindView(R.id.search_text_hot)
    TextView searchTextHot;
    @BindView(R.id.search_recycle_hot)
    RecyclerView searchRecycleHot;
    @BindView(R.id.search_text_history)
    TextView searchTextHistory;
    @BindView(R.id.search_text_clear)
    TextView searchTextClear;
    @BindView(R.id.search_recycle_history)
    RecyclerView searchRecycleHistory;
    private Unbinder unbinder;
    private List<String> list;
    private String searchContent;
    private List<String> list1=new ArrayList<>();
    private List<HistoryBean> data;
    private Search_Recycle search_recycle;
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
        setContentView(R.layout.activity_search);
        unbinder = ButterKnife.bind(this);
        changeFont();
        initbean();
        getHotBean();
        getHistoryBean();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        searchText.setTypeface(typeface);
        searchTextClear.setTypeface(typeface);
        searchTextHistory.setTypeface(typeface);
        searchTextHot.setTypeface(typeface);
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add("民宿");
        list.add("客栈");
        list.add("酒店");
        list.add("秘籍");
        list.add("火锅");
        list.add("自然");

        try {
            data = db.selector(HistoryBean.class).orderBy("id",true).limit(8).findAll();
            for (int i = 0; i < data.size(); i++) {
                list1.add(data.get(i).getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getHotBean() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        searchRecycleHot.addItemDecoration(new SpacesItemDecoration3(40));
        searchRecycleHot.setLayoutManager(gridLayoutManager);
        searchRecycleHot.setAdapter(new Search_Recycle(list, this));
    }
    private void getHistoryBean(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        search_recycle=new Search_Recycle(list1, this);
        searchRecycleHistory.addItemDecoration(new SpacesItemDecoration3(40));
        searchRecycleHistory.setLayoutManager(gridLayoutManager);
        searchRecycleHistory.setAdapter(search_recycle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.search_go,R.id.search_text_clear})
    public void onViewClicked(View view)  {
        switch (view.getId()){
            case R.id.search_go:
                searchContent = searchText.getText().toString().trim();
                if (!TextUtils.isEmpty(searchContent)) {
                    List<HistoryBean> list = new ArrayList<>();
                    HistoryBean bean = new HistoryBean(searchContent);
                    list.add(bean);
                    try {
                        data = db.selector(HistoryBean.class).orderBy("id",true).limit(8).findAll();
                        for (int i = 0; i < data.size(); i++) {
                            list1.add(data.get(i).getText());
                        }
                        if (list1.contains(searchContent)){

                        }else {
                            db.save(list);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            db.save(list);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                }else {
                    Toast.makeText(SearchActivity.this,"请输入搜索内容",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_text_clear:
                list1.clear();
                search_recycle.notifyDataSetChanged();
                try {
                    db.delete(HistoryBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }


    }
}
