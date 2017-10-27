package com.mssd.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.mssd.data.JpushBean;
import com.mssd.html.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DELL on 2017/8/28.
 */

public class MyReceiver extends BroadcastReceiver {
    DbManager.DaoConfig config = new DbManager.DaoConfig()
            .setDbName("mydata.db")
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            });
    DbManager db = x.getDb(config);

    private List<JpushBean> list = new ArrayList<>();
    private JpushBean jpushBean;
    private String content,islink,link;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extra=bundle.getString(JPushInterface.EXTRA_EXTRA);
        String id=bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        Log.e("tag","推送参数---->"+"title:"+title+"  alert:"+alert+"  massage:"+message+"  extra:"+extra+"  id"+id);
        Log.e("tag","-----------------------------------------");
        try {
            JSONObject jsonObject=new JSONObject(extra);
             content=jsonObject.getString("content");
             islink=jsonObject.getString("islink");
             link=jsonObject.getString("link");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Intent intent1 = new Intent(context, JpushActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }else {
            try {
                jpushBean = new JpushBean(content, getDate(),islink,link);
                if (content!=null){
                    list.add(jpushBean);
                    db.save(list);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }


}

