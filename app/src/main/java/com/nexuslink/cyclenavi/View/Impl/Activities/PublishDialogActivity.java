package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.nexuslink.cyclenavi.Adapters.PhotosPrepareAdapter;
import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.ArticleBean;
import com.nexuslink.cyclenavi.Model.JavaBean.PublishBean;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.FreshEvent;
import com.nexuslink.cyclenavi.Util.GlideImageLoader;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.Util.SpUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PublishDialogActivity extends AppCompatActivity {
    private static final String FORM_DATA = "multipart/form-data";
    private static final int IMAGE_PICKER =  0;
    private PhotosPrepareAdapter adpter;
    private RecyclerView recyclerPhotos;
    private ArrayList<ImageItem> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dialog);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        recyclerPhotos = (RecyclerView) findViewById(R.id.prepare_publish_photos);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerPhotos.setLayoutManager(manager);
        adpter = new PhotosPrepareAdapter(this);
        recyclerPhotos.setAdapter(adpter);

        final EditText text = (EditText) findViewById(R.id.text_publish);
        CardView cardPublish = (CardView) findViewById(R.id.card_publish);

        cardPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadText(text);
            }
        });

        ImageView camera = (ImageView) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PublishDialogActivity.this, ImageGridActivity.class);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
        });
    }

    private void upLoadImages(ArrayList<ImageItem> images, int articleId) {

        if(images != null ) {
            Log.d("TAG_123",images.size() + images.get(0).path);
            List<MultipartBody.Part> list = new ArrayList<>();
           /* for (ImageItem image : images){*/
                File fileold = new File(images.get(0).path);
                File file = new CompressHelper.Builder(this)
                        .setMaxWidth(720)  // 默认最大宽度为720
                        .setMaxHeight(960) // 默认最大高度为960
                        .setQuality(60)
                        .setCompressFormat(Bitmap.CompressFormat.PNG) // 设置压缩为png格式
                        .build()
                        .compressToFile(fileold);



                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/png"), file);
                MultipartBody.Part img =
                        MultipartBody.Part.createFormData("articleImgs",file.getName(),requestFile);
                list.add(img);
          /*  }*/

            RequestBody aId =
                    RequestBody.create(MediaType.parse("multipart/form-data"),articleId+"");

            RequestBody uid =
                    RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(SpUtil.getUserId()));


            RetrofitWrapper.getInstance().create(ICycleNaviService.class).article(aId,uid,img).enqueue(new Callback<ArticleBean>() {
                @Override
                public void onResponse(Call<ArticleBean> call, Response<ArticleBean> response) {
                    Log.d("TAG_123",response.code()+"");
                    EventBus.getDefault().post(new FreshEvent("123"));
                    finish();
                }

                @Override
                public void onFailure(Call<ArticleBean> call, Throwable t) {

                }
            });
        }
    }

    private void upLoadText(TextView text) {
        Log.d("MY_TAG","上传前"+SpUtil.getUserId());
        RetrofitWrapper.getInstance().create(ICycleNaviService.class).publish(SpUtil.getUserId(),text.getText().toString()).enqueue(new Callback<PublishBean>() {
            @Override
            public void onResponse(Call<PublishBean> call, Response<PublishBean> response) {
                upLoadImages(images,response.body().getArticleId());
                finish();
            }

            @Override
            public void onFailure(Call<PublishBean> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                adpter.addPhoto(images);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
