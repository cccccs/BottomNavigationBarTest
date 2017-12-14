package activitytest.com.example.bottomnavigationbartest.ui.fragment.fours;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.adpater.WantedAdapter;
import activitytest.com.example.bottomnavigationbartest.base.BaseFragment;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.listener.OnItemClickListener;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/8/27.
 */

public class PublishWantedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    SwipeRefreshLayout mRefreshLayout;
    RecyclerView mRecy;

    User loginUser;
    MyApplication myApplication;

    WantedAdapter mAdapter;
    public static PublishWantedFragment newInstance() {
        PublishWantedFragment fragment = new PublishWantedFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.publish_wanted, container, false);

        myApplication = (MyApplication)getActivity().getApplication();
        loginUser = myApplication.getLoginUser();
        initView(view);
        return view;
    }

    public void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));

        mRecy.setLayoutManager(new LinearLayoutManager(_mActivity));
//        mRecy.setHasFixedSize(true);
//        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
//        mRecy.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(0, 0, 0, space);
//            }
//        });
        mAdapter = new WantedAdapter(_mActivity);
        mRecy.setAdapter(mAdapter);



        mAdapter.setOnMsgClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                Job job = mAdapter.getMsg(position);
                int jobId = job.getJobId();
                getUserList(jobId);

            }
        });

        List<Job> chatList = loginUser.getJobList();

        if(chatList!=null) {
            Log.d("PublishWantedFragment","工作列表"+ loginUser.getJobList().toString());
            mAdapter.setDatas(chatList);
        }
    }


    public void getUserList(int jobId){
        final String url  ="http://119.29.3.128:8080/JobHunter/Employer/JobSeekerInfo";
        Log.d("PubishWantedFragment","Session值"+loginUser.getSession());
        JSONObject object = new JSONObject();
        try {

            object.put("jobId",jobId);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON,object.toString());
        final String[] str= new String[2];
         String str1,str2;
        HttpUtil.postOkHttpRequestWithSession(url, loginUser.getSession(), requestBody,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"请求失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                if(Utility.handleStatusResponse(responseText)) {
                    final User user = Utility.handleUserResponse(responseText);
                    Log.d("工作请求", "主页FIRST");
                    if (user != null) {

                        _mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                str[0]=user.getUserName();
                                str[1]=user.getPhone();
                                showDialog(str);
                                Toast.makeText(_mActivity, "刷新成功", Toast.LENGTH_LONG).show();
                            }

                        });
                    }
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            str[0]="暂无人报名";
                            str[1]=" ";
                            showDialog(str);
                            Toast.makeText(_mActivity, "刷新成功", Toast.LENGTH_LONG).show();
                        }

                    });
            }
            }
        });

        Log.d("PubishWantedFragment","Session值"+loginUser.getSession());


    }

    public void showDialog(final String[] items){
        AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity);
        builder.setTitle("报名列表");
        // builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);

        // 设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(_mActivity, items[which],
                        Toast.LENGTH_SHORT).show();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();



    }
    @Override
    public void onRefresh() {


        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2500);
        getJobList();
    }

    public void getJobList(){

        final String address;
        address = "http://119.29.3.128:8080/JobHunter/Employer/login";

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


                    final List<Job> jobList = Utility.handlejobResponse(responseText);

                    Log.d("工作完成","MAIN"+jobList);
                    if(jobList!=null) {

                        loginUser.setJobList(jobList);

                    }




                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                            mAdapter.setDatas(loginUser.getJobList());
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



    private List<Job> initJobs(){


        List<Job> jobList = new ArrayList<>();
        String workName[] = new String[]{"托管班兼职","招聘App推广"};
        int workPay[] = new int[]{40,60};
        String workPlace[] = new String[]{"金明>30KM","崂山区内"};
        String publishTime[] = new String[]{"2017-08-17","2017-08-19 "};
        String workType[] = new String[]{"家教 ", "其他"};
        String PeopleNum[] = new String[]{"1人","2人"};
        String jobSex[] = new String[]{"男","不限"};
        String workData[] = new String[]{"8月-9月", "长期接受报名"};
        String workTime[] = new String[]{" 下午3点到5点" ,"上午9点到下午5点"};
        String workContent[] = new String[]{"要求：有初中语文教学经验，善于沟通表达，课堂活跃的大学生老师。\n 要求汉语言专业大学生。 \n男大学生优先，女大学生也可以考虑。\n有意者请联系选师无忧伍老师13610045402 ","一、直招APP下载兼职：\n1、负责去建材市场、五金机电市场、婚庆花店、广告打印、水果蔬菜批发市场。带着优惠券和小礼品给商户讲解货拉拉这款拉货平台，对于有意向商户教给他们下载使用货拉拉软件。\n2、要求踏实认真、飞机王勿扰。二、兼职绩效标准：1、底薪100+提成+奖金。2、工资第二天结，打到银行卡。\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\n"};
        String havePeopleNum[] = new String[]{"10人","5人"};
        int imageId[] = new int[]{R.drawable.work_image,R.drawable.work_image};



        for (int i = 0; i < 15; i++) {
            int index = (int) (Math.random() * 2);
            Job job = new Job();
            job.setWorkName(workName[index]);
            job.setWorkPay(workPay[index]);
            job.setWorkPlace(workPlace[index]);
            job.setPublishTime(publishTime[index]);
            job.setWorkType(workType[index]);
            job.setPeopleNum(PeopleNum[index]);
            job.setJobSex(jobSex[index]);
            job.setWorkData(workData[index]);
            job.setWorkTime(workTime[index]);
            job.setWorkContent(workContent[index]);
            job.setHavePeopleNum(havePeopleNum[index]);
            job.setImageId(imageId[index]);
            jobList.add(job);
        }
        return jobList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecy.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

}