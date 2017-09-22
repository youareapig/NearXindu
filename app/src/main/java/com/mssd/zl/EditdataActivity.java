package com.mssd.zl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mssd.data.UserInfoBean;
import com.mssd.utils.AddressPickTask;
import com.mssd.utils.SingleModleUrl;
import com.zhy.autolayout.AutoLayoutActivity;


import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;

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
    private String str_username, str_usersex, str_userbirthday, str_useraddress, str_usersign,userID;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        unbinder = ButterKnife.bind(this);
        sharedPreferences =getSharedPreferences("xindu",MODE_PRIVATE);
        userID=sharedPreferences.getString("userid","0");
        changeFont();
        getInfo();
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

    @OnClick({R.id.bianji_usersex, R.id.bianji_userbirthday, R.id.bianji_usersite, R.id.bianji_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bianji_usersex:
                SinglePicker<String> picker = new SinglePicker<>(this, new String[]{"男", "女"
                });
                picker.setCanLoop(true);//不禁用循环
                picker.setTopBackgroundColor(Color.parseColor("#ffffff"));
                picker.setTopHeight(50);
                picker.setWeightEnable(true);
                picker.setWeightWidth(1);
                picker.setHeight(600);
                picker.setTopLineColor(Color.parseColor("#eeeeee"));
                picker.setTopLineHeight(1);
                picker.setTitleTextColor(Color.BLACK);
                picker.setTitleTextSize(12);
                picker.setCancelTextColor(Color.parseColor("#000000"));
                picker.setCancelTextSize(13);
                picker.setSubmitTextColor(Color.parseColor("#000000"));
                picker.setSubmitTextSize(13);
                picker.setSelectedTextColor(Color.parseColor("#000000"));
                picker.setUnSelectedTextColor(Color.parseColor("#888888"));
                LineConfig config = new LineConfig();
                config.setColor(Color.parseColor("#000000"));//线颜色
                config.setAlpha(140);//线透明度
                config.setRatio((float) (1.0 / 8.0));//线比率
                picker.setLineConfig(config);
                picker.setItemWidth(100);
                picker.setBackgroundColor(Color.parseColor("#ffffff"));
                picker.setSelectedIndex(0);
                picker.setOnItemPickListener(new OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int index, String item) {
                        bianjiUsersex.setText(item);
                    }
                });
                picker.show();
                break;
            case R.id.bianji_userbirthday:
                final DatePicker picker1 = new DatePicker(this);
                picker1.setCanLoop(false);
                picker1.setWheelModeEnable(true);
                picker1.setTopPadding(15);
                picker1.setRangeStart(1900, 8, 29);
                picker1.setRangeEnd(2100, 1, 11);
                picker1.setSelectedItem(2017, 9, 24);
                picker1.setWeightEnable(true);
                LineConfig config1 = new LineConfig();
                config1.setColor(Color.parseColor("#000000"));//线颜色
                config1.setAlpha(140);//线透明度
                picker1.setSelectedTextColor(Color.BLACK);
                picker1.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        bianjiUserbirthday.setText(year + " - " + month + " - " + day);
                    }
                });
                picker1.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker1.setTitleText(year + "-" + picker1.getSelectedMonth() + "-" + picker1.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker1.setTitleText(picker1.getSelectedYear() + "-" + month + "-" + picker1.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker1.setTitleText(picker1.getSelectedYear() + "-" + picker1.getSelectedMonth() + "-" + day);
                    }
                });
                picker1.show();
                break;
            case R.id.bianji_usersite:
                AddressPickTask task = new AddressPickTask(this);
                task.setHideCounty(true);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        //showToast("数据初始化失败");
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        bianjiUsersite.setText(city.getAreaName());
                    }
                });
                task.execute("四川", "成都");
                break;
            case R.id.bianji_save:
                str_username = bianjiUsername.getText().toString().trim();
                str_usersex = bianjiUsersex.getText().toString().trim();
                str_userbirthday = bianjiUserbirthday.getText().toString().trim();
                str_useraddress = bianjiUsersite.getText().toString().trim();
                str_usersign = bianjiUsersign.getText().toString().trim();
                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_usersex) || TextUtils.isEmpty(str_userbirthday) || TextUtils.isEmpty(str_useraddress) || str_usersex.equals("请选择") || str_usersex.equals("请选择") || str_userbirthday.equals("请选择") || str_useraddress.equals("请选择")) {
                    Toast.makeText(EditdataActivity.this, "请完善资料", Toast.LENGTH_SHORT).show();
                } else {
                    submit();
                }
                break;
        }
    }

    private void submit() {
        String sexTag;
        if (str_usersex.equals("男")) {
            sexTag = "0";
        } else {
            sexTag = "1";
        }
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/editInfo");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("gender", sexTag);
        params.addBodyParameter("nickname", str_username);
        params.addBodyParameter("birthday", str_userbirthday);
        params.addBodyParameter("domicile", str_useraddress);
        params.addBodyParameter("sign", str_usersign);
        Log.e("tag", "参数说明" + sexTag + "--" + str_username + "--" + str_userbirthday + "--" + str_useraddress + "--" + str_usersign);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3002")) {
                        Toast.makeText(EditdataActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditdataActivity.this, MainActivity.class);
                        intent.putExtra("indextag", 3);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (json.getString("code").equals("-3002")) {
                        Toast.makeText(EditdataActivity.this, "你未做任何修改", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditdataActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "修改失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getInfo() {

        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/info");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "获取用户信息" + result);
                Gson gson = new Gson();
                UserInfoBean bean = gson.fromJson(result, UserInfoBean.class);
                if (bean.getCode() == 3000) {
                    if (bean.getData().getNickname() != "") {
                        bianjiUsername.setText(bean.getData().getNickname());
                    }
                    if (bean.getData().getGender() == 1) {
                        bianjiUsersex.setText("女");
                    } else if (bean.getData().getGender() == 0) {
                        bianjiUsersex.setText("男");
                    }else {
                        bianjiUsersex.setText("请选择");
                    }
                    if (bean.getData().getBirthday() != "") {
                        bianjiUserbirthday.setText(bean.getData().getBirthday());
                    }else {
                        bianjiUserbirthday.setText("请选择");
                    }
                    if (bean.getData().getDomicile() != "") {
                        bianjiUsersite.setText(bean.getData().getDomicile());
                    }else {
                        bianjiUsersite.setText("请选择");
                    }
                    if (bean.getData().getSign() != null) {
                        bianjiUsersign.setText(bean.getData().getSign());
                    }
                } else {
                    Toast.makeText(EditdataActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "获取用户信息失败");
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
