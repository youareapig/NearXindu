package com.mssd.jpush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mssd.zl.R;

import cn.jpush.android.api.JPushInterface;

public class JpushActivity extends AppCompatActivity {
    private TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush);
        t1= (TextView) findViewById(R.id.t1);
        Intent intent=getIntent();
        if (intent!=null){
            Bundle bundle=getIntent().getExtras();
            String content=bundle.getString(JPushInterface.EXTRA_ALERT);
            t1.setText(content);
        }
    }
}
