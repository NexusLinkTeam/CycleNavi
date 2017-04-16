package com.nexuslink.cyclenavi.Model.Impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.nanchen.compresshelper.CompressHelper;
import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.Interface.IPersonalModel;
import com.nexuslink.cyclenavi.Model.JavaBean.UpLoadBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.FileManager;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.View.Impl.Activities.PersonalActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Rye on 2017/1/25.
 */

public class PersonalModel implements IPersonalModel {

    @Override
    public void cancelLoginStatus(PersonalActivity persoanalActivity) {
        persoanalActivity.getSharedPreferences("CycleNaviData",MODE_PRIVATE).edit().putBoolean("isUserLogin",false)
                .putString("name", persoanalActivity.getString(R.string.without_login)).apply();
    }

    @Override
    public void upLoad(Uri uri, PersonalActivity context) {
        File fileold = new File(FileManager.getRealPathFromURI(uri,context));
        File file = new CompressHelper.Builder(context)
                .setMaxWidth(720)  // 默认最大宽度为720
                .setMaxHeight(960) // 默认最大高度为960
                .setQuality(40)
                .setCompressFormat(Bitmap.CompressFormat.PNG) // 设置压缩为png格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(fileold);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/png"), file);

        MultipartBody.Part userImg =
                MultipartBody.Part.createFormData("userImg",file.getName(),requestFile);

        RequestBody uid =
                RequestBody.create(MediaType.parse("multipart/form-data"),context.getSharedPreferences("CycleNaviData",MODE_PRIVATE).getString("uid","0"));

        RetrofitWrapper.getInstance().create(ICycleNaviService.class).upLoad(uid,userImg).enqueue(new Callback<UpLoadBean>() {
            @Override
            public void onResponse(Call<UpLoadBean> call, Response<UpLoadBean> response) {
                Log.d("Tag",response.raw()+""+response.code()+"");
                Log.d("Tag",response.body().getCode()+""+response.body().getUserImg()+response.isSuccessful()+"response body："+response.body().toString()+response.toString());
                if(response.body().getCode() == 200){
                    Log.d("Tag","userImg:"+response.body().getUserImg()+"code:"+response.body().getCode());
                }
            }

            @Override
            public void onFailure(Call<UpLoadBean> call, Throwable t) {
                Log.d("Tag",t.toString());
            }
        });
    }
}
