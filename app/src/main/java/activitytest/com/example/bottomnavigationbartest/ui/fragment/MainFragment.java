package activitytest.com.example.bottomnavigationbartest.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.LoginCancelEvent;
import activitytest.com.example.bottomnavigationbartest.event.LoginSuccessEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherResEvent;
import activitytest.com.example.bottomnavigationbartest.event.TabSelectedEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.first.FirstTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.FoursTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.second.SecondTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.third.ThirdTabFragment;
import activitytest.com.example.bottomnavigationbartest.ui.view.BottomBar;
import activitytest.com.example.bottomnavigationbartest.ui.view.BottomBarTab;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by pc on 2017/5/17.
 */

public class MainFragment extends BaseFragment {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURS = 3;
    MyApplication mApplication;
    User loginUser;
    boolean stuLogin = true;

    private static final int REQ_MODIFY_FRAGMENT = 100;
    public static final String KEY_RESULT_TYPE = "title";


    private SupportFragment[] mFragments = new SupportFragment[4];
    private SupportFragment[] stuFragments = new SupportFragment[3];
    private SupportFragment[] empFragments = new SupportFragment[3];
    private BottomBarTab[] bottomItems = new BottomBarTab[3];

    BottomBar bottomBar;
    FrameLayout mFrameLayout;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mApplication =(MyApplication)getActivity().getApplication();
        loginUser = mApplication.getLoginUser();
        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        final String strAccount = pref.getString("account","");
        final String strPassword = pref.getString("password","");
        final String strUserType = pref.getString("userType","");

        Log.d("MainFragment",strAccount+"strAccount");
        Log.d("MainFragment",strPassword+"strPassWord");
        Log.d("MainFragment",strUserType+"strUserType");
        if(strAccount.equals("") ) {

        }else if(strUserType.equals("business")){
        //    loginUser.setUserType(User.UserType.business);
            login(strAccount,strPassword,0);

            loginUser.setName(strAccount);
            loginUser.setLogin(true);
            loginUser.setUserPassWord(strPassword);

            mApplication.EmpLogin(loginUser);
            Log.d("工作存储完毕","MAIN"+loginUser.getJobList());
            loginUser = mApplication.getLoginUser();

            stuLogin=false;
        }else{
      //    loginUser.setUserType(User.UserType.student);
            login(strAccount,strPassword,1);
            loginUser.setName(strAccount);
            loginUser.setLogin(true);
            loginUser.setUserPassWord(strPassword);

            mApplication.StuLogin(loginUser);
            loginUser = mApplication.getLoginUser();
            stuLogin=true;
        }
        Log.d("MainActivity","loginUser.getlogin()"+loginUser.getlogin());
        Log.d("工作存储完毕","MAIN"+loginUser.getJobList());
        if(loginUser.getJobList()!=null){
            Log.d("工作存储完毕","MAIN"+loginUser.getJobList().isEmpty());
        }
        if (savedInstanceState == null) {
            stuFragments[FIRST]=mFragments[FIRST] = FirstTabFragment.newInstance();
            empFragments[FIRST]=mFragments[SECOND] = SecondTabFragment.newInstance();
            empFragments[SECOND]=stuFragments[SECOND]=mFragments[THIRD] = ThirdTabFragment.newInstance();
            empFragments[THIRD]=stuFragments[THIRD]=mFragments[FOURS] = FoursTabFragment.newInstance();


            if (loginUser instanceof Employer) {
                loadMultipleRootFragment(R.id.fl_tab_container, SECOND,
                        mFragments[FIRST],
                        mFragments[SECOND],
                        mFragments[THIRD],
                        mFragments[FOURS]);

            }else{
                loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                        mFragments[FIRST],
                        mFragments[SECOND],
                        mFragments[THIRD],
                        mFragments[FOURS]);
            }



        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            stuFragments[FIRST]=mFragments[FIRST] = findChildFragment(FirstTabFragment.class);
            empFragments[FIRST]=mFragments[SECOND] = findChildFragment(SecondTabFragment.class);
            empFragments[SECOND]=stuFragments[SECOND]=mFragments[THIRD] = findChildFragment(ThirdTabFragment.class);
            empFragments[THIRD]=stuFragments[THIRD]=mFragments[FOURS] = findChildFragment(FoursTabFragment.class);
        }



        initView(view);
        return view;

    }
    public void  login(final String strAccount,final  String strPassword,int type){
        String address;
        //和数据库对比是否存在此账户
        if(type==1) {
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

        Log.d("工作请求","MAIN");

        RequestBody requestBody = RequestBody.create(JSON,json.toString());

        HttpUtil.postOkHttpRequest(address,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LoginFragment",e.toString());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
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
                            Toast.makeText(_mActivity,"登录成功！", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                Log.d("工作完成","Main"+responseText);
                final List<Job> jobList = Utility.handlejobResponse(responseText);

                Log.d("工作完成","MAIN"+jobList);
                if(jobList!=null) {

                    loginUser.setJobList(jobList);

                }
                Log.d("工作完成","MAIN"+loginUser.getJobList());



                //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                Headers headers = response.headers();
                Log.d("info_headers", "header " + headers);
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                Log.d("info_cookies", "onResponse-size: " + cookies);
                String sessionStr;
                sessionStr = session.substring(0, session.indexOf(";"));
                Log.i("info_s", "session is  :" + sessionStr);

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
                Log.i("info_logUser", "phoneNum" + phoneNum);
                loginUser.setPhone(phoneNum);
                loginUser.setUserId(userId);

                    loginUser.setUserName(strAccount);
                    loginUser.setUserPassWord(strPassword);
                    loginUser.setSession(sessionStr);
                Log.i("info_s", "loginUser.setStringStr" + sessionStr);


            }
        });



    }
    private void initView(View view) {
        EventBus.getDefault().register(this);
        bottomBar = (BottomBar) view.findViewById(R.id.bottomBar);


        Log.d("MainFragment","loginUser instanceof Employer "+String.valueOf(loginUser instanceof Employer));
        if (loginUser instanceof Employer) {
            bottomItems[0] = new BottomBarTab(_mActivity, R.drawable.ic_add_black_24dp, "发布");
            bottomItems[1] = new BottomBarTab(_mActivity, R.drawable.ic_notifications_none_black_24dp, "消息");
            bottomItems[2] = new BottomBarTab(_mActivity, R.drawable.ic_person_outline_black_24dp, "我");
            bottomBar
                    .addItem(bottomItems[0])
                    .addItem(bottomItems[1])
                    .addItem(bottomItems[2]);

        } else {
            bottomItems[0] = new BottomBarTab(_mActivity, R.drawable.ic_list_black_24dp, "主页");
            bottomItems[1] = new BottomBarTab(_mActivity, R.drawable.ic_notifications_none_black_24dp, "消息");
            bottomItems[2] = new BottomBarTab(_mActivity, R.drawable.ic_person_outline_black_24dp, "我");
            bottomBar
                    .addItem(bottomItems[0])
                    .addItem(bottomItems[1])
                    .addItem(bottomItems[2]);
        }

        bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position,int prePosition) {
                if(loginUser instanceof Employer) {
                    showHideFragment(empFragments[position], empFragments[prePosition]);

                }else {
                    showHideFragment(stuFragments[position], stuFragments[prePosition]);
                }

            }

            @Override
            public void onTabUnselected(int position) {
                Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });

    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == RESULT_OK && data != null) {
            String userType = data.getString(KEY_RESULT_TYPE);
            Log.d("MainActivity",userType);
            if (userType.equals("business")) {
                stuLogin = false;
                bottomBar.replaceTab(0, new BottomBarTab(_mActivity, R.drawable.ic_add_black_24dp, "发布"));
                loginUser = mApplication.getLoginUser();
            }else if(userType.equals("student")){
                bottomBar.replaceTab(0,new BottomBarTab(_mActivity, R.drawable.ic_list_black_24dp, "主页"));
                stuLogin = true;
                loginUser = mApplication.getLoginUser();
            }

          //  Toast.makeText(_mActivity, "登录成功", Toast.LENGTH_SHORT).show();
        }

    }

    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Subscribe
    public void startBrotherRes(StartBrotherResEvent event){
        startForResult(event.targetFragment, REQ_MODIFY_FRAGMENT);
    }

    @Subscribe
    public void loginSuccess(LoginSuccessEvent event){

    }
    @Subscribe
    public void loginCancel(LoginCancelEvent event){
        loginUser = mApplication.getLoginUser();
        if(!stuLogin){
            bottomBar.replaceTab(0,new BottomBarTab(_mActivity, R.drawable.ic_list_black_24dp, "主页"));
            stuLogin = true;
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("MainActivity","MainOnDestroyView");
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("MainActivity","MainOnStart");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("MainActivity","MainResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("MainActivity","MainPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("MainActivity","MainStop");
    }

    @Override
    public void onSupportVisible(){
        super.onSupportVisible();
        Log.d("MainActivity","MainVisible");
    }

    @Override
    public void onSupportInvisible(){
        super.onSupportInvisible();
        Log.d("MainActivity","MainInVisible");
    }
}



