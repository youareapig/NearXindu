package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mssd.cardslid.CardAdapter;
import com.mssd.cardslid.CardSlidePanel;
import com.mssd.data.FoodBean;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3, foodBean4;
    private Typeface typeface,typeface1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hof);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
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
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "第一张");
        foodBean2 = new FoodBean(R.mipmap.test, "第二张");
        foodBean3 = new FoodBean(R.mipmap.test, "第三张");
        foodBean4 = new FoodBean(R.mipmap.test, "第四张");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list.add(foodBean4);
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
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }

                viewHolder.bindData(list.get(index % list.size()));

            }

            @Override
            public Object getItem(int index) {
                return list.get(index % list.size());
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
        hofText1.setText(list.get(index % list.size()).getName());
    }

    class ViewHolder {

        ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            AutoUtils.autoSize(view);
        }

        public void bindData(FoodBean itemData) {
            imageView.setImageResource(itemData.getImg());


        }
    }
}
