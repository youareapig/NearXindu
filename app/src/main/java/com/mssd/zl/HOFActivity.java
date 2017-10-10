package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mssd.cardslid.CardAdapter;
import com.mssd.cardslid.CardSlidePanel;
import com.mssd.data.TalkHistoryBean;
import com.mssd.utils.SingleModleUrl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HOFActivity extends AutoLayoutActivity implements CardSlidePanel.CardSwitchListener {

    @BindView(R.id.hof_card)
    CardSlidePanel hofCard;
    @BindView(R.id.hof_text1)
    TextView hofText1;
    @BindView(R.id.hof_text2)
    TextView hofText2;
    @BindView(R.id.hofTitle)
    TextView hofTitle;
    @BindView(R.id.hof_back)
    RelativeLayout hofBack;
    private Unbinder unbinder;
    private List<TalkHistoryBean.DataBean> list;
    private Typeface typeface, typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hof);
        unbinder = ButterKnife.bind(this);
        changeFont();
        init();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        hofTitle.setTypeface(typeface1);
        hofText1.setTypeface(typeface1);
        hofText2.setTypeface(typeface);
    }

    private void initbean() {
        hofText1.setText(list.get(0).getHname());
        hofText2.setText(list.get(0).getHtag());
        hofCard.setCardSwitchListener(this);
        hofCard.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.hofitem;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public void bindView(View view, final int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(list.get(index % list.size()));
                viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("tag", "点击了" + list.get(index % list.size()).getHname());
                    }
                });

            }

            @Override
            public Object getItem(int index) {
                if (list != null) {
                    return list.get(index % list.size());
                }
                return 0;
            }


        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onShow(int index) {
    }

    @Override
    public void onCardVanish(int index, int type) {
        Toast.makeText(this, "-----" + index + "------" + type, Toast.LENGTH_LONG).show();
        hofText1.setText(list.get((index + 1) % list.size()).getHname());
        hofText2.setText(list.get((index + 1) % list.size()).getHtag());
    }

    @OnClick(R.id.hof_back)
    public void onViewClicked() {
        finish();
    }

    class ViewHolder {

        ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            AutoUtils.autoSize(view);
        }

        public void bindData(TalkHistoryBean.DataBean itemData) {
            ImageLoader.getInstance().displayImage(itemData.getUrl(), imageView);


        }
    }

    private void init() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "History/culture");
        params.addBodyParameter("cid", "6");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "名人堂" + result);
                Gson gson = new Gson();
                TalkHistoryBean bean = gson.fromJson(result, TalkHistoryBean.class);
                list = bean.getData();
                if (bean.getCode() == 1000) {
                    initbean();
                } else {
                    Toast.makeText(HOFActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "名人堂访问错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }
}
