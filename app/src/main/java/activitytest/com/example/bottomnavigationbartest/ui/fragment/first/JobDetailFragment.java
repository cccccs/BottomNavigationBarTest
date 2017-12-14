package activitytest.com.example.bottomnavigationbartest.ui.fragment.first;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;

import activitytest.com.example.bottomnavigationbartest.MyApplication;
import activitytest.com.example.bottomnavigationbartest.R;
import activitytest.com.example.bottomnavigationbartest.base.BaseBackFragment;
import activitytest.com.example.bottomnavigationbartest.db.Job;
import activitytest.com.example.bottomnavigationbartest.db.User;
import activitytest.com.example.bottomnavigationbartest.event.SignSuccessEvent;
import activitytest.com.example.bottomnavigationbartest.event.StartBrotherEvent;
import activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.AllMyselfFragment;
import activitytest.com.example.bottomnavigationbartest.util.HttpUtil;
import activitytest.com.example.bottomnavigationbartest.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static activitytest.com.example.bottomnavigationbartest.ui.fragment.fours.RegisterFragment.JSON;

/**
 * Created by pc on 2017/5/8.
 */

public class JobDetailFragment extends BaseBackFragment {
    private static final String ARG_JOB = "arg_job";

    private Job mJob;

    private User loginUser;
    private MyApplication myApplication;

    TextView mSignText;
    TextView mJobDetailName;
    TextView mJobDetailSalre;
    TextView mJobDetailLoc;
    TextView mJobDetailDate;
    TextView mJobStartTime;
    TextView mJobEndTime;
    TextView mJobWorkTime;
    TextView mWorkHour;
    TextView mWorkTime;
    TextView mWorkContent;
    TextView mJobPhone;
    TextView mJobWorkHour;
    LinearLayout mCollectLinear;
    LinearLayout mSignUpLinear;

    public static JobDetailFragment newInstance() {
        Bundle args = new Bundle();
        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobDetailFragment newInstance(Job job){
        Bundle args = new Bundle();
        args.putParcelable(ARG_JOB, job);
        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJob = getArguments().getParcelable(ARG_JOB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.first_job_detail,container, false);

        myApplication = (MyApplication)_mActivity.getApplication();
        loginUser = myApplication.getLoginUser();
        initView(view);
        return view;
    }

    public void initView(View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mJobDetailName = (TextView)view.findViewById(R.id.job_detail_name);
        mJobDetailSalre = (TextView)view.findViewById(R.id.job_detail_salary);
        mJobDetailLoc = (TextView) view.findViewById(R.id.job_detail_place);
        mJobDetailDate = (TextView) view.findViewById(R.id.job_detail_publish_time);
//        mJobType = (TextView) view.findViewById(R.id.job_detail_job_type);
//        mJobPeopleNum = (TextView) view.findViewById(R.id.job_detail_person_num);
//        mJobSex = (TextView) view.findViewById(R.id.job_detail_sex);

        mWorkTime =(TextView)view.findViewById(R.id.job_detail_work_time);
        mWorkContent = (TextView) view.findViewById(R.id.job_detail_job_content);

//        mJobHadPeopleNum = (TextView)view.findViewById(R.id.job_detail_had_people_num);
        mCollectLinear = (LinearLayout)view.findViewById(R.id.job_detail_collect);
        mSignUpLinear = (LinearLayout) view.findViewById(R.id.signUp);

        mSignText = (TextView) view.findViewById(R.id.sign_tx);
        mJobDetailName.setText(mJob.getWorkName());
        mJobDetailSalre.setText(mJob.getWorkPay() + "");
        mJobDetailLoc.setText(mJob.getWorkPlace());
        mJobDetailDate.setText(mJob.getPublishTime());
  //      mJobType.setText(mJob.getWorkType());
    //    mJobPeopleNum.setText(mJob.getPeopleNum());
      //  mJobSex.setText(mJob.getJobSex());

        mWorkTime.setText(mJob.getWorkTime());
        mWorkContent.setText(mJob.getWorkContent());
        //mJobHadPeopleNum.setText(mJob.getHavePeopleNum());



        toolbar.setTitle("兼职详情");
       toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               _mActivity.onBackPressed();
            }
        });

        if(mJob.getStatus()==1){
            mSignText.setText("已报名");
        }else{
            mSignUpLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loginUser = myApplication.getLoginUser();
                    Log.d("JobDetailFragment","电话"+loginUser.getPhone());
                    try {
                        if (loginUser.getPhone().equals("")) {

                            showNormalDialog();
                        } else {
                            handleSignUp();
                        }
                    }catch (Exception e){
                        showNormalDialog();
                    }
                }
            });
        }


    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(_mActivity);

        normalDialog.setTitle("提示");
        normalDialog.setMessage("请完善个人电话信息以便联系");

        normalDialog.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new StartBrotherEvent(AllMyselfFragment.newInstance()));
                    }
                });
        // 显示
        normalDialog.show();
    }

    public void handleSignUp(){
        String address = "http://119.29.3.128:8080/JobHunter/JobSeeker/takeJob";

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("jobId",mJob.getJobId());
            jsonObject.put("employerId",mJob.getEmployerId());
            Log.d("JobDetailFragment","工作ID"+mJob.getJobId()+mJob.getEmployerId());
        }catch (Exception e){
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON,jsonObject.toString());
        HttpUtil.postOkHttpRequestWithSession(address,loginUser.getSession(), requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(_mActivity,"报名失败 failure",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(Utility.handleStatusResponse(response.body().string())) {
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity,"报名成功",Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new SignSuccessEvent(mJob.getJobId()));
                            _mActivity.onBackPressed();
                        }
                    });
                }else{
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(_mActivity,"报名失败 false",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        hideSoftInput();
    }
    @Override
    public void onDetach(){
        super.onDetach();
    }


}
