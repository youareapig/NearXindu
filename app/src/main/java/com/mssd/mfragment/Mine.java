package com.mssd.mfragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.mssd.data.UpdataHeadBean;
import com.mssd.data.UserBean;
import com.mssd.jpush.JpushActivity;
import com.mssd.utils.CameraUtil;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.CardActivity;
import com.mssd.zl.EditdataActivity;
import com.mssd.zl.PlaceActivity;
import com.mssd.zl.R;
import com.mssd.zl.SettingActivity;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DELL on 2017/8/30.
 */

public class Mine extends Fragment {
    @BindView(R.id.mine_head)
    ImageView mineHead;
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
    @BindView(R.id.mine_text_card)
    TextView mineTextCard;
    @BindView(R.id.mine_card)
    AutoRelativeLayout mineCard;
    private Unbinder unbinder;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userid;
    private Uri imgUrl;
    private Typeface typeface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString("userid", "0");
        changeFont();
        requestNet();
        return view;
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        mineEditprofil.setTypeface(typeface);
        mineName.setTypeface(typeface);
        mineTextNotice.setTypeface(typeface);
        mineTextPlace.setTypeface(typeface);
        mineTextSetting.setTypeface(typeface);
        mineTextCard.setTypeface(typeface);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_notice, R.id.mine_place, R.id.mine_setting, R.id.mine_editprofil, R.id.mine_head,R.id.mine_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_notice:
                Intent intent3 = new Intent(getActivity(), JpushActivity.class);
                startActivity(intent3);
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
                Window window = builder.getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.width = 800;
                window.setAttributes(layoutParams);
                TextView t = (TextView) layout.findViewById(R.id.photograph);
                TextView t1 = (TextView) layout.findViewById(R.id.photo);
                t.setTypeface(typeface);
                t1.setTypeface(typeface);
                layout.findViewById(R.id.photograph).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MPermissions.requestPermissions(Mine.this, 20, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
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
            case R.id.mine_card:
                Intent intent4=new Intent(getActivity(), CardActivity.class);
                startActivity(intent4);
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
//        imgUrl = CameraUtil.getTempUri();
//        startActivityForResult(CameraUtil.takePicture(imgUrl), 2);
    }

    @PermissionGrant(30)
    public void requestPhotoSuccess() {
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, 1);
        CameraUtil.selectPhoto();
        startActivityForResult(Intent.createChooser(CameraUtil.selectPhoto(), "选择照片"), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO 防止调用相机后不拍照后退程序崩溃
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case 1:
//                final Uri uri = data.getData();
//                String[] filePathColumns = {MediaStore.Images.Media.DATA};
//                Cursor c = getActivity().getContentResolver().query(uri, filePathColumns, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePathColumns[0]);
//                final String imagePath = c.getString(columnIndex);
//                uploadhead(imagePath);
                if (data != null) {
                    imgUrl = CameraUtil.getTempUri();
                    startActivityForResult(CameraUtil.cropPhoto(data.getData(), imgUrl, 200, 200), 3);
                } else {
                    startActivityForResult(CameraUtil.cropPhoto(imgUrl, imgUrl, 200, 200), 3);
                }
                break;
            case 2:
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
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
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
//                if(data != null){
//                    Uri uri = data.getData();
//                    startActivityForResult(CameraUtil.cropPhoto(uri,imgUrl,150,150), 3);
//                }else{
//                    startActivityForResult(CameraUtil.cropPhoto(imgUrl,imgUrl,150,150), 3);
//                }
                break;
            case 3:
                String imgpath = CameraUtil.getPathFromUri(getActivity(), imgUrl);
                uploadhead(imgpath);
                break;
        }

    }

    private void uploadhead(String path) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/setHead");
        params.addBodyParameter("uid", userid);
        params.addBodyParameter("headpic", new File(path));
        params.setMultipart(true);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                UpdataHeadBean bean = gson.fromJson(result, UpdataHeadBean.class);
                if (bean.getCode() == 3003) {
                    Glide.with(getActivity())
                            .load(bean.getData().getHeadpic())
                            .placeholder(R.mipmap.yuan)
                            .error(R.mipmap.yuan)
                            .bitmapTransform(new CenterCrop(getActivity()), new CropCircleTransformation(getActivity()))
                            .into(mineHead);
                } else {
                    ToastUtils.showShort(getActivity(), "上传失败!");
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(), R.string.erroe);
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
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                UserBean bean = gson.fromJson(result, UserBean.class);
                if (bean.getCode() == 3000) {
                    if (bean.getData().getHeadpic() != "") {
                        Glide.with(getActivity())
                                .load(bean.getData().getHeadpic())
                                .placeholder(R.mipmap.yuan)
                                .error(R.mipmap.yuan)
                                .bitmapTransform(new CenterCrop(getActivity()), new CropCircleTransformation(getActivity()))
                                .into(mineHead);
                        editor.putString("userHead", bean.getData().getHeadpic());
                        editor.commit();
                    }
                    if (bean.getData().getUname() != "") {
                        mineName.setText(bean.getData().getUname());
                        editor.putString("userName", bean.getData().getUname());
                        editor.commit();
                    }
                } else {
                    ToastUtils.showShort(getActivity(), R.string.erroe);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(), R.string.erroe);
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
