package com.mssd.zl;

import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.ChatWithMeAdapter;
import com.mssd.data.ChatBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWithMeActivity extends AutoLayoutActivity {
    @BindView(R.id.chat_back)
    RelativeLayout chatBack;
    @BindView(R.id.chat_title)
    TextView chatTitle;
    @BindView(R.id.chat_recycle)
    RecyclerView chatRecycle;
    @BindView(R.id.chat_head)
    CircleImageView chatHead;
    @BindView(R.id.chat_content)
    EditText chatContent;
    @BindView(R.id.chat_send)
    TextView chatSend;
    private Unbinder unbinder;
    private SharedPreferences sharedPreferences;
    private String userID, contents,userhead;
    private List<ChatBean.DataBean> list;
    private ChatWithMeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_me);
        unbinder = ButterKnife.bind(this);
        init();
        getChat();
        linearLayoutManager = new LinearLayoutManager(ChatWithMeActivity.this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        chatRecycle.setLayoutManager(linearLayoutManager);
    }

    private void init() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        userhead=sharedPreferences.getString("userHead","0");
        ImageLoader.getInstance().displayImage(userhead,chatHead);
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        chatTitle.setTypeface(typeface1);
        chatSend.setTypeface(typeface);
        chatContent.setTypeface(typeface);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.chat_back, R.id.chat_send})
    public void onViewClicked(View view) {
        contents = chatContent.getText().toString().trim();
        switch (view.getId()) {
            case R.id.chat_back:
                finish();
                break;
            case R.id.chat_send:
                if (TextUtils.isEmpty(contents)){
                    ToastUtils.showShort(ChatWithMeActivity.this,"消息不能为空!");
                }else {
                    sendNotice();
                    linearLayoutManager.setStackFromEnd(true);
                }

                break;
        }
    }

    private void getChat() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/chat");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ChatBean bean = gson.fromJson(result, ChatBean.class);
                if (bean.getCode() == 3000) {
                    list = bean.getData();
                    adapter = new ChatWithMeAdapter(list, ChatWithMeActivity.this);
                    chatRecycle.setAdapter(adapter);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(ChatWithMeActivity.this,R.string.erroe);
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

    private void sendNotice() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addchat");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("message", contents);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("mm", "发送成功" + result);
                chatContent.setText(null);
                getChat();
                adapter.notifyItemRangeChanged(0, list.size());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("mm", "发送失败");
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
