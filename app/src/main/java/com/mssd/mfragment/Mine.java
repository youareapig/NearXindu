package com.mssd.mfragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.ui.Util;
import com.google.gson.Gson;
import com.mssd.data.UpdataHeadBean;
import com.mssd.data.UserBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.EditdataActivity;
import com.mssd.zl.PlaceActivity;
import com.mssd.zl.R;
import com.mssd.zl.SettingActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 2017/8/30.
 */

public class Mine extends Fragment {
    @BindView(R.id.mine_head)
    CircleImageView mineHead;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_editprofil)
    TextView mineEditprofil;
    @BindView(R.id.mine_text_notice)
    TextView mineTextNotice;
    @BindView(R.id.mine_text_place)
    TextView mineTextPlace;
    @BindView(R.id.mine_text_setting)
    TextView mineTextSetting;
    @BindView(R.id.mine_notice)
    AutoRelativeLayout mineNotice;
    @BindView(R.id.mine_place)
    AutoRelativeLayout minePlace;
    @BindView(R.id.mine_setting)
    AutoRelativeLayout mineSetting;
    private Unbinder unbinder;
    private SharedPreferences sharedPreferences;
    private String userid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        userid = sharedPreferences.getString("userid", "0");
        changeFont();
        requestNet();
        return view;
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        mineEditprofil.setTypeface(typeface);
        mineName.setTypeface(typeface);
        mineTextNotice.setTypeface(typeface);
        mineTextPlace.setTypeface(typeface);
        mineTextSetting.setTypeface(typeface);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_notice, R.id.mine_place, R.id.mine_setting, R.id.mine_editprofil, R.id.mine_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_notice:
                break;
            case R.id.mine_place:
                Intent intent2 = new Intent(getActivity(), PlaceActivity.class);
                startActivity(intent2);
                break;
            case R.id.mine_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_editprofil:
                Intent intent1 = new Intent(getActivity(), EditdataActivity.class);
                startActivity(intent1);
                break;
            case R.id.mine_head:
                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.layout_camera_control,
                        null);
                builder.setView(layout);
                builder.show();
                layout.findViewById(R.id.photograph).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MPermissions.requestPermissions(Mine.this, 20, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        builder.cancel();
                    }
                });
                layout.findViewById(R.id.photo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MPermissions.requestPermissions(Mine.this, 30, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        builder.cancel();
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(20)
    public void requestCameraSuccess() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @PermissionGrant(30)
    public void requestPhotoSuccess() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == 1) {
            final Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(uri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            final String imagePath = c.getString(columnIndex);
            uploadhead(imagePath);
        } else if (requestCode == 2) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            FileOutputStream fileOutputStream = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/" + name;
            try {
                fileOutputStream = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                uploadhead(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                    fileOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void uploadhead(String path) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/setHead");
        params.addBodyParameter("uid",userid );
        params.addBodyParameter("headpic", new File(path));
        params.setMultipart(true);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "上传成功" + result);
                Gson gson = new Gson();
                UpdataHeadBean bean = gson.fromJson(result, UpdataHeadBean.class);
                if (bean.getCode() == 3003) {
                    ImageLoader.getInstance().displayImage(bean.getData().getHeadpic(), mineHead);
                } else {
                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "上传失败");
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

    private void requestNet() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/myInfo");
        params.addBodyParameter("uid", userid);
        Log.e("tag","userID"+userid);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag","获取用户信息"+result);
                Gson gson = new Gson();
                UserBean bean = gson.fromJson(result, UserBean.class);
                if (bean.getCode() == 3000) {
                    if (bean.getData().getHeadpic() != "") {
                        ImageLoader.getInstance().displayImage(bean.getData().getHeadpic(), mineHead);
                    }
                    if (bean.getData().getUname() != "") {
                        mineName.setText(bean.getData().getUname());
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "用户信息错误");
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
