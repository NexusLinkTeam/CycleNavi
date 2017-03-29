package com.nexuslink.cyclenavi.View.Impl.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nexuslink.cyclenavi.Api.ICycleNaviService;
import com.nexuslink.cyclenavi.Model.JavaBean.SaveNameBean;
import com.nexuslink.cyclenavi.Presenter.Impl.RegisterPresenterImpl;
import com.nexuslink.cyclenavi.R;
import com.nexuslink.cyclenavi.Util.RetrofitWrapper;
import com.nexuslink.cyclenavi.Util.ShowProgressTool;
import com.nexuslink.cyclenavi.View.Interface.IRegisterView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity implements IRegisterView{
    private RegisterPresenterImpl registerPresenter;
    private View progressView;
    private View resisterForm;
    private EditText userName;
    private EditText userPassword;
    private EditText emergencyPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // actionbar处理
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.register_user));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
        //初始化
        registerPresenter = new RegisterPresenterImpl(this);
        CardView registerCard = (CardView) findViewById(R.id.register_card);
        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        final EditText rePassword = (EditText) findViewById(R.id.re_password);
        emergencyPhone= (EditText) findViewById(R.id.emergency_phone);
        resisterForm = findViewById(R.id.register_form);
        progressView = findViewById(R.id.register_progress);
        registerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresenter.registerNewUser(userName,userPassword,rePassword,emergencyPhone);
            }
        });
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                RetrofitWrapper.getInstance().create(ICycleNaviService.class).saveName(userName.getText().toString())
                        .enqueue(new Callback<SaveNameBean>() {
                            @Override
                            public void onResponse(Call<SaveNameBean> call, Response<SaveNameBean> response) {
                                if(response.body().getCode()==200){
                                    if(!response.body().isHasSame()){
                                    }else {
                                        userName.setError("用户名重复");
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SaveNameBean> call, Throwable t) {

                            }
                        });
            }



            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRegisterProgress() {
        ShowProgressTool.showProgress(true,progressView,resisterForm);
    }

    @Override
    public void onCancelRegisterProgress() {
        ShowProgressTool.showProgress(false,progressView,resisterForm);
    }

    @Override
    public void showNetWorkWrong() {
        Snackbar.make(resisterForm,R.string.network_wrong,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showErrorReason(EditText editText, String reason) {
        editText.setError(reason);
    }


    @Override
    public void onFinishRegister() {
        Intent data = new Intent();
        data.putExtra("NAME",userName.getText().toString());
        data.putExtra("PASS",userPassword.getText().toString());
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public void showPhoneNumWrong() {
        //前提是名字要ok
        emergencyPhone.requestFocus();
        emergencyPhone.setError(getString(R.string.phonenum_wrong));
    }
}
