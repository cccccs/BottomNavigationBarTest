package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.content.Context;
import android.content.SharedPreferences;
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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.LoginCancelEvent;
import activitytest.com.example.bottomnavigationbartest.event.LoginSuccessEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.MainFragment;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.db.User.UserType.student;
import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by YoKeyword on 16/2/14.
 */
public class LoginFragment extends BaseBackFragment {
    private EditText mEtAccount, mEtPassword;
    private Button mBtnLogin, mBtnRegister;
    private MyApplication mApplication;
    private CheckBox stuCheckBox,empCheckBox;
    private boolean signCorrect=true;
    private OnLoginSuccessListener mOnLoginSuccessListener;
    private User loginUser;


    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttachFragment(Fragment childFragment){
//       super.onAttachFragment(childFragment);
//        if (childFragment instanceof OnLoginSuccessListener) {
//            mOnLoginSuccessListener = (OnLoginSuccessListener) childFragment;
//        } else {
//            throw new RuntimeException(childFragment.toString()
//                    + " must implement OnLoginSuccessListener");
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getPreFragment().getTopChildFragment() instanceof OnLoginSuccessListener) {
            mOnLoginSuccessListener = (OnLoginSuccessListener) getPreFragment().getTopChildFragment();
        } else {
            throw new RuntimeException(getPreFragment().getTopChildFragment().toString() + " must implement OnLoginSuccessListener");
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d("MainActivity","LoginOnStart");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d("MainActivity","LoginResume");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d("MainActivity","LoginPause");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d("MainActivity","LoginStop");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mApplication = (MyApplication)getActivity().getApplication();
        loginUser = mApplication.getLoginUser();

        initView(view);
        return view;
    }

    private void initView(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mEtAccount = (EditText) view.findViewById(R.id.et_account);
        mEtPassword = (EditText) view.findViewById(R.id.et_password);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
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
        toolbar.setTitle(R.string.login);
        initToolbarNav(toolbar);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strAccount = mEtAccount.getText().toString();
                final String strPassword = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
               login(strAccount,strPassword);

            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RegisterFragment.newInstance());
            }
        });
    }
    public void  login(final String strAccount,final  String strPassword){
        String address;
        //和数据库对比是否存在此账户
        if(!empCheckBox.isChecked()) {
            address  = "http://119.29.3.128:8080/JobHunter/JobSeeker/login";
        }else{
            address = "http://119.29.3.128:8080/JobHunter/Employer/login";
        }

        JSONObject json = new JSONObject();
        try {
            json.put("username", strAccount);
            json.put("password",strPassword);
        }catch (JSONException e){
            Toast.makeText(_mActivity, e+ "", Toast.LENGTH_SHORT).show();
        }


        RequestBody requestBody = RequestBody.create(JSON,json.toString());

        HttpUtil.postOkHttpRequest(address,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LoginFragment",e.toString());
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"登录失败", Toast.LENGTH_SHORT).show();


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                Headers headers = response.headers();
                Log.d("info_headers", "header " + headers);
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                Log.d("info_cookies", "onResponse-size: " + cookies);
                String sessionStr;
                sessionStr = session.substring(0, session.indexOf(";"));
                Log.i("info_s", "session is  :" + sessionStr);

                String responseText = response.body().string();
                Log.d("工作完成","Main"+responseText);
                final List<Job> jobList = Utility.handlejobResponse(responseText);

                Log.d("工作完成","MAIN"+jobList);
                if(jobList!=null) {

                    loginUser.setJobList(jobList);

                }

                    int userId;
                    String phoneNum;
                    int genderId;

                        userId = Utility.handleIntResponse(responseText,"userId") ;

                    phoneNum = Utility.handleStringResponse(responseText,"phoneNumber");

                        genderId = Utility.handleIntResponse(responseText,"gender") ;



                    if(genderId!=0) {
                        loginUser.setSex(User.Sex.man);
                    }else{
                        loginUser.setSex(User.Sex.woman);
                    }
                Log.i("info_logUser", "" + phoneNum);
                    loginUser.setPhone(phoneNum);
                    loginUser.setUserId(userId);

                loginUser.setSession(sessionStr);
                Log.i("info_s", "Session test" + sessionStr);
                loginUser.setUserName(strAccount);
                Log.i("info_logUser", "" + loginUser.getPhone());
                if(!Utility.handleStatusResponse(responseText)){
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "用户名或密码名错误!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            correctPassword(strAccount,strPassword);
                        }
                    });

                }



            }
        });



    }

    private void correctPassword(String strAccount,String strPassword) {
        SharedPreferences.Editor editor  =  _mActivity.getSharedPreferences("data",Context.MODE_PRIVATE).edit();
        editor.putString("account",strAccount);
        editor.putString("password",strPassword);
        editor.putString("userType",(empCheckBox.isChecked()?User.UserType.business: student).toString());
        editor.apply();



        if(empCheckBox.isChecked()) {
            loginUser.setName(strAccount);
            loginUser.setLogin(true);
            loginUser.setUserPassWord(strPassword);
            mApplication.EmpLogin(loginUser);

            Bundle bundle = new Bundle();
            bundle.putString(MainFragment.KEY_RESULT_TYPE,"business");
            setFragmentResult(RESULT_OK, bundle);
        }else {
            loginUser.setName(strAccount);
            loginUser.setLogin(true);
            loginUser.setUserPassWord(strPassword);
            mApplication.StuLogin(loginUser);


            Bundle bundle = new Bundle();
            bundle.putString(MainFragment.KEY_RESULT_TYPE,"student");
            setFragmentResult(RESULT_OK, bundle);
        }

        Log.i("info_s", "SessionTest" + loginUser.getSession());
        Log.d("MainActivity","loginUser.getlogin()"+loginUser.getlogin());
        Log.d("MainFragment","loginUser instanceof Employer "+String.valueOf(loginUser instanceof Employer));

        // 登录成功 eventbus上传发布事件
         EventBus.getDefault().post(new LoginSuccessEvent(strAccount));
        hideSoftInput();



        mOnLoginSuccessListener.onLoginSuccess(strAccount);
        pop();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
    }

    public interface OnLoginSuccessListener {
        void onLoginSuccess(String account);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            hideSoftInput();
        }
    }

    @Override
    protected void initToolbarNav(Toolbar toolbar) {
        super.initToolbarNav(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();

                EventBus.getDefault().post(new LoginCancelEvent());
                hideSoftInput();
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        EventBus.getDefault().post(new LoginCancelEvent());
        hideSoftInput();
        return false;
    }

}
