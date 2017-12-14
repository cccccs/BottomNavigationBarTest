package activitytest.com.example.bottomnavigationbartest.ui.fragment.third;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.InformAdapter;
import activitytest.com.example.bottomnavigationbartest.adpater.InformLAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;
import activitytest.com.example.bottomnavigationbartest.db.Employer;
import activitytest.com.example.bottomnavigationbartest.db.Inform;
import activitytest.com.example.bottomnavigationbartest.db.InformL;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.MsgL;
import activitytest.com.example.bottomnavigationbartest.db.Student;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.LoginCancelEvent;
import activitytest.com.example.bottomnavigationbartest.event.LoginSuccessEvent;
import activitytest.com.example.bottomnavigationbartest.event.SignSuccessEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherResEvent;
import activitytest.com.example.bottomnavigationbartest.event.TabSelectedEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.MainFragment;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.LoginFragment;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/4/27.
 */

public class InformMesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecy;
    User loginUser;
    MyApplication myApplication;

    private boolean mInAtTop = true;
    private int mScrollTotal;

    private InformLAdapter iAdapter;
    private InformAdapter mAdapter;

    public static InformMesFragment instance(){
        Bundle args = new Bundle();

        InformMesFragment fragment = new InformMesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.message_inform, container, false);

        myApplication = (MyApplication)getActivity().getApplication();
        loginUser = myApplication.getLoginUser();
        initView(view);
        return view;
    }

    public void initView(View view){
        EventBus.getDefault().register(this);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        if(loginUser.getlogin()) {
            if (loginUser instanceof Employer) {
                getUser();
            } else {
                getJob();
            }
        }

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecy.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });
        mAdapter = new InformAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0) {
                    mInAtTop = true;
                } else {
                    mInAtTop = false;
                }
            }
        });




    }

    public List<Inform> initDatas(List<Job> jobList) {
        List<Inform> informList = new ArrayList<>();


        try {
            for (int i = 0; i < jobList.size(); i++) {

                Inform inform = new Inform();
                inform.setPhone(jobList.get(i).getPhoneNum());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");//设置日期格式
                inform.setTime(df.format(System.currentTimeMillis()));
                inform.setJob(jobList.get(i));
                informList.add(inform);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return informList;
    }

    public List<InformL> initMsgDatas(List<MsgL> msgLList) {
        List<InformL> informList = new ArrayList<>();


        try {
            for (int i = 0; i < msgLList.size(); i++) {

                InformL inform = new InformL();
                inform.setJob(msgLList.get(i).getInfo());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd ");//设置日期格式
                inform.setTime(df.format(System.currentTimeMillis()));
                informList.add(inform);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return informList;
    }

    public void getJob(){

        final String address;
            address  = "http://119.29.3.128:8080/JobHunter/JobSeeker/login";

        JSONObject json = new JSONObject();
        try {
            json.put("username", loginUser.getUserName());
            json.put("password",loginUser.getUserPassWord());
        }catch (JSONException e){
            Toast.makeText(_mActivity, e+ "", Toast.LENGTH_SHORT).show();
        }

        RequestBody requestBody = RequestBody.create(JSON,json.toString());

        HttpUtil.postOkHttpRequest(address,requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("LoginFragment", e.toString());
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity, "请求失败", Toast.LENGTH_SHORT).show();


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();
                if (!Utility.handleStatusResponse(responseText)) {
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "请求失败!", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {


                    final List<Job> jobList = Utility.handlejobStatusResponse(responseText);
                    loginUser.setMyJobList(jobList);
                    Log.d("InformMesFragment","通知Job"+jobList);




                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Inform> informList = initDatas(loginUser.getMyJobList());
                            mAdapter = new InformAdapter(_mActivity);
                            mRecy.setAdapter(mAdapter);
                            mAdapter.setDatas(informList);
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(_mActivity, "请求成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        Log.d("InformMesFragment","通知"+loginUser.getMyJobList());
        try {

        }catch (Exception e){
            Log.d("InformMesFragment","通知无");
            e.printStackTrace();
        }
    }


    @Override
    public void onRefresh() {
        if (!loginUser.getlogin()) {
            showDialog();
            mRefreshLayout.setRefreshing(false);
        }else {
            mRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(false);
                }
            }, 2500);
            loginUser = myApplication.getLoginUser();
            Log.d("InformMesFragment", "状态" + (loginUser instanceof Student));


            if (loginUser instanceof Student) {
                getJob();
            } else {
                getUser();
            }
        }
    }
    public void showDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("未登录");
        dialog.setMessage("请先登录！");
        dialog.setCancelable(false);
        dialog.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new StartBrotherResEvent(LoginFragment.newInstance()));
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
    @Subscribe
    public void SignSuccess(SignSuccessEvent event){
        getJob();
    }

    public void getUser(){
        final String url  ="http://119.29.3.128:8080/JobHunter/Employer/getMessageRecived";

        JSONObject object = new JSONObject();

        RequestBody requestBody = RequestBody.create(JSON,object.toString());

        HttpUtil.postOkHttpRequestWithSession(url,loginUser.getSession(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity, "请求失败", Toast.LENGTH_SHORT).show();


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText =response.body().string();
                Log.d("InformMesFragment",responseText);
                if(Utility.handleStatusResponse(responseText)){
                    final List<MsgL> msgList = Utility.handleMsgResponse(responseText);
                    loginUser.setMsgLList(msgList);





                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<InformL> informList = initMsgDatas(loginUser.getMsgLList());
                            iAdapter = new InformLAdapter(_mActivity);
                            mRecy.setAdapter(iAdapter);
                            iAdapter.setDatas(informList);
                            iAdapter.notifyDataSetChanged();
                            Toast.makeText(_mActivity, "请求成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity, "请求失败!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });

    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.SECOND) return;

        if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }
    }

    @Subscribe
    public void loginSuccess(LoginSuccessEvent event){
        onRefresh();
    }

    @Subscribe
    public void loginCancel(LoginCancelEvent event){
        mRecy.setAdapter(null);
    }

    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

}
