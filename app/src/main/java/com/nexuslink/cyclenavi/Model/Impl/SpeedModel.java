package com.nexuslink.cyclenavi.Model.Impl;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.nexuslink.cyclenavi.Model.Interface.ISpeedModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by Rye on 2017/3/28.
 */

public class SpeedModel implements ISpeedModel {
    private File path;
    private File newFile;
    private Uri uri;
    @Override
    public double currentSpeed() {
        return 0;
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
}
