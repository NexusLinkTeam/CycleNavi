package com.nexuslink.cyclenavi.Model.Impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.amap.api.maps.MapView;
import com.nexuslink.cyclenavi.Model.Interface.IInfoModel;
import com.nexuslink.cyclenavi.Presenter.Interface.IInfoPresenter;
import com.nexuslink.cyclenavi.Util.MediaScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rye on 2017/4/19.
 */

public class InfoModel implements IInfoModel {
    private File path;
    private File newFile;
    private Uri uri;
    private IInfoPresenter infoPresenter;

    public InfoModel(IInfoPresenter presenter) {
        this.infoPresenter = presenter;
    }

    //存储照片
    @Override
    public void storePic(Context context) {
        path = new File(Environment.getExternalStorageDirectory()+"/骑车帮");
        if(!path.exists()){
            path.mkdir();
        }

        newFile = new File(path,"Img_"+System.currentTimeMillis()+".png");
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(Build.VERSION.SDK_INT >= 24){
            uri = FileProvider.getUriForFile(context,"com.nexuslink.cyclenavi.fileprovider",newFile);
        }else {
            uri = Uri.fromFile(newFile);
        }
    }

    //？？？？
    @Override
    public Uri getUri() {
        return uri;
    }

    //添加到手机相册
    @Override
    public void addToGallery(Context context) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), newFile.getAbsolutePath(),"hello", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MediaScanner mediaScanner = new MediaScanner(context);
        String[] filePaths = new String[]{newFile.getAbsolutePath()};
        Log.d("TAG",newFile.getAbsolutePath());
        String[] mimeTypes = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("png")};
        mediaScanner.scanFiles(filePaths, mimeTypes);
    }

    // TODO: 2017/5/9  获得截图算法
    
    @Override
    public void getScreenShot(final Bitmap bitmap, final ViewGroup viewContainer, final MapView mapView, final View... views) {
        new Thread(){
            public void run(){

                Bitmap screenShotBitmap = getMapAndViewScreenShot(bitmap,viewContainer,mapView,views);
                if(Environment.getExternalStorageState().
                        equals(Environment.MEDIA_MOUNTED)) {

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"test1.png");

                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        screenShotBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                        //根据自己需求，如果外边对bitmp还有别的需求就不要recycle的
                        screenShotBitmap.recycle();
                        bitmap.recycle();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    infoPresenter.requestShortCutSuccess(file);
                }
            }

        }.start();
    }

    public static Bitmap getMapAndViewScreenShot(Bitmap bitmap, ViewGroup viewContainer, MapView mapView, View...views){
        int width = viewContainer.getWidth();
        int height = viewContainer.getHeight();
        final Bitmap screenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenBitmap);
        canvas.drawBitmap(bitmap, mapView.getLeft(), mapView.getTop(), null);
        for (View view:views){
            view.setDrawingCacheEnabled(true);
            canvas.drawBitmap(view.getDrawingCache(), view.getLeft(), view.getTop(), null);
        }

        return screenBitmap;
    }



}
