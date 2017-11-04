package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 * Created by YoKeyword on 16/2/14.
 */
public class RegisterFragment extends BaseBackFragment {
    private EditText mEtAccount, mEtPassword, mEtPasswordConfirm;
    private Button mBtnRegister;
    private LoginFragment.OnLoginSuccessListener mOnLoginSuccessListener;
    private CheckBox stuCheckBox,empCheckBox;

    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (getPreFragment().getPreFragment().getTopChildFragment() instanceof LoginFragment.OnLoginSuccessListener) {
//            mOnLoginSuccessListener = (LoginFragment.OnLoginSuccessListener) getPreFragment().getPreFragment().getTopChildFragment();
//        } else {
//            throw new RuntimeException( getPreFragment().getPreFragment().getTopChildFragment().toString()
//                    + " must implement OnLoginSuccessListener");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mEtAccount = (EditText) view.findViewById(R.id.et_account);
        mEtPassword = (EditText) view.findViewById(R.id.et_password);
        mEtPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);
        stuCheckBox = (CheckBox) view.findViewById(R.id.cb1);
        empCheckBox = (CheckBox)view.findViewById(R.id.cb2);

        stuCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    empCheckBox.setChecked(false);
                }else{
                    empCheckBox.setChecked(true);
                }
            }
        });

        empCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stuCheckBox.setChecked(false);
                }else{
                    stuCheckBox.setChecked(true);
                }
            }
        });

        showSoftInput(mEtAccount);

        toolbar.setTitle(R.string.register);
        initToolbarNav(toolbar);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = mEtAccount.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strPasswordConfirm = mEtPasswordConfirm.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim()) || TextUtils.isEmpty(strPasswordConfirm.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!strPassword.equals(strPasswordConfirm)) {
                    Toast.makeText(_mActivity, "两次密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String address ;
                if(!empCheckBox.isChecked()) {
                    address= "http://119.29.3.128:8080/JobHunter/JobSeeker/signin";
                }else{
                    address = "http://119.29.3.128:8080/JobHunter/Employer/signin";
                }



                JSONObject json = new JSONObject();
                try {
                    json.put("username", strAccount);
                    json.put("password",strPassword);
                }catch (JSONException e){
                    Toast.makeText(_mActivity, e+ "", Toast.LENGTH_SHORT).show();
                }
                RequestBody requestBody = RequestBody.create(JSON,json.toString());

                HttpUtil.postOkHttpRequest(address, requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("RegisterFragment","RegisterFragment"+e.toString());
                        _mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(_mActivity, "注册失败！", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("LoginFragment","MMMMMMMMM"+response.body().string());

//                        if(!Utility.handleStatusResponse(response.body().string())){
//                            _mActivity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(_mActivity, "注册成功，请登录！", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                        }else{
//                            _mActivity.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(_mActivity, "注册失败！", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }


                    }
                });


                // 注册成功
                //mOnLoginSuccessListener.onLoginSuccess(strAccount);
                //popTo(LoginFragment.class, true);
                pop();

            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideSoftInput();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
    }
}
