package com.mssd.zl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mssd.data.LoginBean;
import com.mssd.utils.ClassPathResource;
import com.mssd.utils.CountDownTimerUtils;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.Utils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AutoLayoutActivity {
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.login_userphone)
    EditText loginUserphone;
    @BindView(R.id.login_usercode)
    EditText loginUsercode;
    @BindView(R.id.login_getvercode)
    TextView loginGetvercode;
    @BindView(R.id.login_login)
    TextView loginLogin;
    @BindView(R.id.login_text1)
    TextView loginText1;
    @BindView(R.id.login_text2)
    TextView loginText2;
    @BindView(R.id.login_main)
    LinearLayout loginMain;
    @BindView(R.id.login_main1)
    LinearLayout loginMain1;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;
    private String string_userphone, string_code;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        changeFont();
        keepLoginBtnNotOver(loginMain,loginMain1);
        loginMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.closeKeyboard(LoginActivity.this);
                return false;
            }
        });


        init();
    }

    private void init() {
        loginUserphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                string_userphone = loginUserphone.getText().toString().trim();
                if (!TextUtils.isEmpty(string_userphone)) {
                    loginLogin.setBackgroundResource(R.drawable.loginbutton1);
                    loginLogin.setEnabled(true);
                    loginText2.setTextColor(Color.parseColor("#c69c36"));
                    loginText2.setEnabled(true);
                } else {
                    loginLogin.setBackgroundResource(R.drawable.loginbutton);
                    loginLogin.setEnabled(false);
                    loginText2.setTextColor(Color.parseColor("#a0a0a0"));
                    loginText2.setEnabled(false);
                }
            }
        });
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        loginTitle.setTypeface(typeface1);
        loginUserphone.setTypeface(typeface);
        loginUsercode.setTypeface(typeface);
        loginGetvercode.setTypeface(typeface);
        loginLogin.setTypeface(typeface);
        loginText1.setTypeface(typeface);
        loginText2.setTypeface(typeface);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.login_getvercode, R.id.login_login, R.id.login_text2})
    public void onViewClicked(View view) {
        string_code = loginUsercode.getText().toString().trim();
        string_userphone = loginUserphone.getText().toString().trim();
        ClassPathResource classPathResource = new ClassPathResource();
        boolean isPhone = classPathResource.isMobileNO(string_userphone);
        switch (view.getId()) {
            case R.id.login_getvercode:
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(loginGetvercode, 60000, 1000);
                mCountDownTimerUtils.start();
                break;
            case R.id.login_login:
                if (isPhone == false) {
                    Toast.makeText(this, "您输入的电话号码不正确，请核对和输入", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(string_code)) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        loginNet(string_userphone);
                    }

                }
                break;
            case R.id.login_text2:
                Intent intent = new Intent(LoginActivity.this, AgreementActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loginNet(String tel) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/logo");
        params.addBodyParameter("tel", tel);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "登录成功" + result);
                Gson gson = new Gson();
                LoginBean bean = gson.fromJson(result, LoginBean.class);
                if (bean.getCode() == 3000) {
                    editor.putString("userid", bean.getData().getId() + "");
                    editor.putBoolean("islogin", true);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("indextag", 3);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "访问出错");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void keepLoginBtnNotOver(final View root, final View subView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom-270;
                // 若不可视区域高度大于200，则键盘显示,其实相当于键盘的高度
                if (rootInvisibleHeight > 200) {
                    // 显示键盘时
                    int srollHeight = rootInvisibleHeight - (root.getHeight() - subView.getHeight()) - Utils.getNavigationBarHeight(root.getContext());
                    if (srollHeight > 0) {
                        root.scrollTo(0, srollHeight);
                    }
                } else {
                    // 隐藏键盘时
                    root.scrollTo(0, 0);
                }
            }
        });
    }


}
