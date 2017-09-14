package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mssd.utils.AddressPickTask;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;

public class EditdataActivity extends AutoLayoutActivity {
    @BindView(R.id.bianji_title)
    TextView bianjiTitle;
    @BindView(R.id.bianji_nicheng)
    TextView bianjiNicheng;
    @BindView(R.id.bianji_username)
    EditText bianjiUsername;
    @BindView(R.id.bianji_sex)
    TextView bianjiSex;
    @BindView(R.id.bianji_usersex)
    TextView bianjiUsersex;
    @BindView(R.id.bianji_birthday)
    TextView bianjiBirthday;
    @BindView(R.id.bianji_userbirthday)
    TextView bianjiUserbirthday;
    @BindView(R.id.bianji_site)
    TextView bianjiSite;
    @BindView(R.id.bianji_usersite)
    TextView bianjiUsersite;
    @BindView(R.id.bianji_sign)
    TextView bianjiSign;
    @BindView(R.id.bianji_usersign)
    EditText bianjiUsersign;
    @BindView(R.id.bianji_save)
    TextView bianjiSave;
    private Unbinder unbinder;
    private Typeface typeface1, typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        unbinder = ButterKnife.bind(this);
        changeFont();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        bianjiTitle.setTypeface(typeface1);
        bianjiNicheng.setTypeface(typeface);
        bianjiSex.setTypeface(typeface);
        bianjiSave.setTypeface(typeface);
        bianjiSite.setTypeface(typeface);
        bianjiSign.setTypeface(typeface);
        bianjiBirthday.setTypeface(typeface);

        bianjiUserbirthday.setTypeface(typeface);
        bianjiUsername.setTypeface(typeface);
        bianjiUsersex.setTypeface(typeface);
        bianjiUsersign.setTypeface(typeface);
        bianjiUsersite.setTypeface(typeface);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.bianji_usersex, R.id.bianji_userbirthday, R.id.bianji_usersite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bianji_usersex:
                List<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                SinglePicker<String> picker1 = new SinglePicker<>(this, sexList);
                picker1.setCanceledOnTouchOutside(false);
                picker1.setSelectedIndex(1);
                picker1.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int index, String item) {
                        bianjiUsersex.setText(item);
                    }
                });
                picker1.show();
                break;
            case R.id.bianji_userbirthday:
                final DatePicker picker = new DatePicker(this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(2017, 1, 11);
                picker.setRangeStart(1900, 8, 29);
                picker.setSelectedItem(1990, 10, 14);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        bianjiUserbirthday.setText(year + "-" + month + "-" + day);
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
                break;
            case R.id.bianji_usersite:
                AddressPickTask task = new AddressPickTask(this);
                task.setHideCounty(true);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        Toast.makeText(EditdataActivity.this, "数据初始化失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        bianjiUsersite.setText(city.getAreaName());
                    }
                });
                task.execute("四川", "成都");
                break;
        }
    }
}
