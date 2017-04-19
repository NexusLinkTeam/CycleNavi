package com.nexuslink.cyclenavi.Model.Impl;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.nexuslink.cyclenavi.Model.Interface.IInfoModel;
import com.nexuslink.cyclenavi.Presenter.Impl.InfoPresenterImpl;
import com.nexuslink.cyclenavi.Presenter.Interface.IInfoPresenter;
import com.nexuslink.cyclenavi.Util.MediaScanner;

import java.io.File;
import java.io.FileNotFoundException;
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

    @Override
    public Uri getUri() {
        return uri;
    }

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
}
