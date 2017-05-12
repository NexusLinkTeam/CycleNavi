package com.nexuslink.cyclenavi.View.Interface;

import java.io.File;

/**
 * Created by Rye on 2017/4/28.
 */

public interface IFragCommunicate {
    void startDrawAndCalculate();
    void sendSpeed(float speed);


    String getHeights();

    String getSpeeds();

    void getShortCut();

    String getTotalTime();

    void shortCutOK(File file);
}
