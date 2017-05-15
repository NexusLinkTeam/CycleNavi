package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.method.NumberKeyListener;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.UpdateEPhone;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.Util.SpUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ng.max.slideview.SlideView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MsgActivity extends AppCompatActivity {

    private static final int MY_SENDMSG_PERMISSIONS = 1;
    private SlideView slideView;
    private EditText msgContent;
    private TextView changeMsg;
    private TextView saveMsgInfo;
    char[] numberChars ={'1','2','3','4','5','6','7','8','9','0'};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        if (ContextCompat.checkSelfPermission(MsgActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MsgActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED  ){
            ActivityCompat.requestPermissions(MsgActivity.this
                    ,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_SENDMSG_PERMISSIONS);
        }
        changeMsg = (TextView)findViewById(R.id.changeMsg);
        saveMsgInfo = (TextView)findViewById(R.id.saveMsgInfo);
        msgContent = (EditText)findViewById(R.id.msgContent);
        msgContent.setText(SpUtils.getString(MsgActivity.this,"EmergencyContent"));
        slideView = (SlideView)findViewById(R.id.slideViewMsg);
        changeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.change_msg_dialog,(ViewGroup)findViewById(R.id.changeMsgdialog));
                final EditText editText = (EditText)view.findViewById(R.id.Editchange);
                editText.setText(SpUtils.getString(MsgActivity.this,"EmergencyCall"));
/*                editText.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return numberChars;
                    }
                    @Override
                    public int getInputType() {
                        return 0;
                    }
                });*/
                AlertDialog.Builder builder = new AlertDialog.Builder(MsgActivity.this);
                builder.setTitle("修改预存紧急联系电话号码");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = editText.getText().toString();
                        if (phoneNumber.length() != 11)
                        {
                            Toast.makeText(MsgActivity.this,"请输入11位有效电话号码",Toast.LENGTH_LONG).show();
                        }else{
                            Log.e("MsgA","是11位");
                            changeEmergencyPhoneNumber(phoneNumber);
                        }
                    }
                });
                builder.setView(view);
                builder.show();
            }
        });
        saveMsgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.change_msg_dialog,(ViewGroup)findViewById(R.id.changeMsgdialog));
                final EditText editText = (EditText)view.findViewById(R.id.Editchange);
                editText.setText(SpUtils.getString(MsgActivity.this,"EmergencyContent"));
                AlertDialog.Builder builder = new AlertDialog.Builder(MsgActivity.this);
                builder.setTitle("紧急短信内容");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = editText.getText().toString();
                        SpUtils.putString(MsgActivity.this,"EmergencyContent",content);
                        msgContent.setText(content);
                        Toast.makeText(MsgActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setView(view);
                builder.show();
            }
        });
        slideView.setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
/*                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);*/
                sendMsg();
            }
        });
    }
    public void sendMsg()
    {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> list = smsManager.divideMessage(msgContent.getText().toString());
        for (String text: list)
        {/*SpUtils.getString(MsgActivity.this,"EmergencyCall"*/
            smsManager.sendTextMessage(SpUtils.getString(MsgActivity.this,"EmergencyCall"),null,text,null,null);
        }
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        Toast.makeText(MsgActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
    }
    private void changeEmergencyPhoneNumber(final String newNumber)
    {
        RetrofitWrapper.getInstance().create(ICycleNaviService.class).changeEPhone(54,newNumber)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UpdateEPhone>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(MsgActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(UpdateEPhone updateEPhone) {
                        if (updateEPhone.getCode() == 200)
                        {
                            Toast.makeText(MsgActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            SpUtils.putString(MsgActivity.this,"EmergencyCall",newNumber);
                        }
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_SENDMSG_PERMISSIONS)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MsgActivity.this,"开启授权",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(MsgActivity.this,"短信权限未授权",Toast.LENGTH_LONG).show();
                finish();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
