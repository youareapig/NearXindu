package com.mssd.zl;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class KaiJiActivity extends AutoLayoutActivity {
    @BindView(R.id.kaiji_surface)
    SurfaceView kaijiSurface;
    @BindView(R.id.kaiji_back)
    RelativeLayout kaijiBack;
    private Unbinder unbinder;
    private MediaPlayer mediaPlayer;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_ji);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        kaijiSurface.getHolder().setKeepScreenOn(true);
        kaijiSurface.getHolder().addCallback(new SurfaceViewLis());
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
        mediaPlayer.setLooping(true);
        mediaPlayer.setDisplay(kaijiSurface.getHolder());
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @OnClick(R.id.kaiji_back)
    public void onViewClicked() {
        finish();
    }
}
