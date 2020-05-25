package com.example.andy.myutils.Utils;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Register {

    private static Register mRegister;
    private Activity mActivity;
    private Context mContext;
    private EditText mUserName;
    private EditText mPassWord;
    private boolean userExists;
    private boolean  passwordCorrect;

    private Register() {

    }


    public static Register getInstance() {
        if (mRegister == null) {
            mRegister = new Register();
        }
        return mRegister;
    }

    public void login(Activity activity, Context context, EditText userName, EditText passWord){
        this.mActivity = activity;
        this.mContext = context;
        this.mUserName = userName;
        this.mPassWord = passWord;
        check();
    }

    private void check(){
        if (mUserName.getText().toString().replace(" ", "").equals("")){
            Toast.makeText(mContext, "用户名还未填写!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mPassWord.getText().toString().replace(" ", "").equals("")){
            Toast.makeText(mContext, "密码还未填写!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getUserExists()==false){
            Toast.makeText(mContext, "没有此用户!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (getPasswordCorrect()==false){
            Toast.makeText(mContext, "密码错误!", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private boolean getUserExists(){
        return userExists;
    }

    private boolean getPasswordCorrect(){
        return passwordCorrect;
    }




}
