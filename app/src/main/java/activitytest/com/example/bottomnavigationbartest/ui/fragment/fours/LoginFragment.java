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

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.LoginCancelEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.MainFragment;

import static activitytest.com.example.bottomnavigationbartest.db.User.UserType.student;

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
                String strAccount = mEtAccount.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //和数据库对比是否存在此账户

                if(signCorrect){
                    SharedPreferences.Editor editor  =  getActivity().getSharedPreferences("data",Context.MODE_PRIVATE).edit();
                    editor.putString("account",strAccount);
                    editor.putString("password",strPassword);
                    editor.putString("userType",(empCheckBox.isChecked()?User.UserType.business: student).toString());
                    editor.apply();
                }


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


                Log.d("MainActivity","loginUser.getlogin()"+loginUser.getlogin());
                Log.d("MainFragment","loginUser instanceof Employer "+String.valueOf(loginUser instanceof Employer));

                // 登录成功 eventbus上传发布事件
              //  EventBus.getDefault().post(new LoginSuccessEvent(strAccount));
                hideSoftInput();
              mOnLoginSuccessListener.onLoginSuccess(strAccount);
                pop();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RegisterFragment.newInstance());
            }
        });
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
