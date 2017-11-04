package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/8/20.
 */

public class AllMyselfFragment extends BaseBackFragment {
    Toolbar toolbar;
    EditText userName,userAge,userSex,userPhone,userGrade;
    ImageView correctImg;
    LinearLayout gradeLinear;
    CheckBox checkBox1,checkBox2;
    Button bt1;

    MyApplication myApplication;
    User loginUser;

    public static AllMyselfFragment newInstance(){
        Bundle args = new Bundle();
        AllMyselfFragment fragment = new AllMyselfFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_all_myself,container,false);
        myApplication = (MyApplication)getActivity().getApplication();
        loginUser =myApplication.getLoginUser();
        _mActivity.setSupportActionBar(toolbar);
        initView(view);
        return view;
    }

    private void initView(View view){
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        gradeLinear = (LinearLayout) view.findViewById(R.id.linear_grade);
        checkBox1 = (CheckBox) view.findViewById(R.id.cb1);
        checkBox2 = (CheckBox) view.findViewById(R.id.cb2);
        userName = (EditText) view.findViewById(R.id.name);

        correctImg = (ImageView) view.findViewById(R.id.correct);
        userPhone = (EditText) view.findViewById(R.id.phone);


        initToolbarNav(toolbar);

        if(loginUser instanceof Employer)
            gradeLinear.setVisibility(View.INVISIBLE);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox2.setChecked(false);
                }else {
                    checkBox2.setChecked(true);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkBox1.setChecked(false);
                }
                else {
                    checkBox1.setChecked(true);
                }
            }
        });

        correctImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserInfo();

            }
        });



    }

    public void sendUserInfo(){
        String url  ="http://119.29.3.128:8080/JobHunter/JobSeeker/update";

        JSONObject json = new JSONObject();
        try {
            json.put("gender",checkBox1.isChecked()?1:0);
            json.put("publicname",userName.getText());
            json.put("phoneNumber",userPhone.getText());
        }catch (JSONException e){
            Toast.makeText(_mActivity, e+ "", Toast.LENGTH_SHORT).show();
        }
        RequestBody requestBody = RequestBody.create(JSON,json.toString());
        HttpUtil.postOkHttpRequestWithSession(url, loginUser.getSession(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"发送失败 onFailure", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(Utility.handleStatusResponse(response.body().string())) {
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "发送成功！", Toast.LENGTH_SHORT).show();
                            _mActivity.onBackPressed();

                        }
                    });
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "发送失败 STATUS", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
