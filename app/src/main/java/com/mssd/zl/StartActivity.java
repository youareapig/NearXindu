package com.mssd.zl;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StartActivity extends AutoLayoutActivity {
    @BindView(R.id.start_surface)
    SurfaceView startSurface;
    @BindView(R.id.start_go)
    TextView startGo;
    private Unbinder unbinder;
    private MediaPlayer mediaPlayer;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        startSurface.getHolder().setKeepScreenOn(true);
        startSurface.getHolder().addCallback(new SurfaceViewLis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @OnClick(R.id.start_go)
    public void onViewClicked() {
        Intent intent=new Intent(StartActivity.this,MainActivity.class);
        intent.putExtra("showtag",1);
        startActivity(intent);
        finish();
    }

    private class SurfaceViewLis implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (position == 0) {
                play();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private void play() {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor fd = null;
        try {
            fd = this.getAssets().openFd("kaiji.mp4");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                    fd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //循环播放
       // mediaPlayer.setLooping(true);
        mediaPlayer.setDisplay(startSurface.getHolder());
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        //TODO 播放结束监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("showtag",1);
                startActivity(intent);
                finish();
            }
        });
    }
}
