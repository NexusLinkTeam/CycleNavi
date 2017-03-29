package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexuslink.cyclenavi.Presenter.Impl.PersonalPresenterImpl;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.View.Interface.IPersonalView;


public class PersonalActivity extends AppCompatActivity implements View.OnClickListener,IPersonalView {
    private PersonalPresenterImpl personalPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalPresenterImpl = new PersonalPresenterImpl(this);
        setContentView(R.layout.activity_personal);
        TextView name = (TextView) findViewById(R.id.name_text);
        name.setText(getSharedPreferences("CycleNaviData",MODE_PRIVATE).getString("name","name"));
        ImageView back = (ImageView) findViewById(R.id.back);
        TextView logout = (TextView) findViewById(R.id.logout);
        ImageView photo = (ImageView) findViewById(R.id.photo);
        photo.setOnClickListener(this);
        back.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.logout:
                new AlertDialog.Builder(this).setTitle(getString(R.string.logout))
                        .setMessage(R.string.logout_message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        personalPresenterImpl.logout();
                        setResult(RESULT_OK);
                        finish();
                    }
                }).setNegativeButton(R.string.no,null).show();
                break;
            case R.id.photo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choosePath)
                        .setItems(new String[]{"相机", "图库"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        //相机拍摄
                                        break;
                                    case 1:
                                        Intent intent = new Intent(
                                                Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, 1);
                                        break;
                                }
                            }
                        }).show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            personalPresenterImpl.upLoadPhoto(uri,this);
        }

    }
    @Override
    public PersonalActivity getCurrentActivity() {
        return this;
    }
}
